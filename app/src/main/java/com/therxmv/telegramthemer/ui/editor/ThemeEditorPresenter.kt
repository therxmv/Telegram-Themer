package com.therxmv.telegramthemer.ui.editor

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.usecase.GetAtthemeFileUseCase
import com.therxmv.telegramthemer.domain.usecase.GetAvailableStylesUseCase
import com.therxmv.telegramthemer.domain.usecase.GetCachedThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.GetTemplateCapabilitiesUseCase
import com.therxmv.telegramthemer.domain.usecase.SaveThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.SyncTemplatesUseCase
import com.therxmv.telegramthemer.ui.extensions.isMonetAvailable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Named

class ThemeEditorPresenter @Inject constructor(
    @Named("Default") private val defaultDispatcher: CoroutineDispatcher,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    getCachedTheme: GetCachedThemeUseCase,
    private val saveTheme: SaveThemeUseCase,
    private val getAtthemeFile: GetAtthemeFileUseCase,
    private val syncTemplates: SyncTemplatesUseCase,
    private val getAvailableStyles: GetAvailableStylesUseCase,
    private val getTemplateCapabilities: GetTemplateCapabilitiesUseCase,
) : ThemeEditorContract.Presenter() {

    companion object {
        private const val SHARE_TEXT = "Theme made via play.google.com/store/apps/details?id=com.therxmv.telegramthemer"
        private const val THERXMV_MENTION = "@therxmv_channel"
        private const val BLANDO_MENTION = "@BlandoThemes"

        // Splash screen waits at most this long for the template sync before
        // dismissing anyway - the sync itself keeps running in the background
        // either way (see attachView).
        private const val SPLASH_TIMEOUT_MS = 5000L
    }

    private var themeState = clampToCapabilities(getCachedTheme())
    private var currentOverwrittenKey: AtthemePreviewKeys? = null
    private val listeners: MutableList<ThemeStateListener> = mutableListOf()
    private var collectJob: Job? = null

    @Volatile
    override var areTemplatesReady = false
        private set

    override fun attachView(view: ThemeEditorContract.View) {
        super.attachView(view)

        val syncJob = lifecycleOwner.lifecycleScope.launch(ioDispatcher) { syncTemplates() }
        lifecycleOwner.lifecycleScope.launch(ioDispatcher) {
            // Only the wait is bounded - a slow syncJob is never cancelled here,
            // it just keeps caching whatever it can after the splash dismisses.
            withTimeoutOrNull(SPLASH_TIMEOUT_MS) { syncJob.join() }
            areTemplatesReady = true
        }

        collectJob = lifecycleOwner.lifecycleScope.launch(defaultDispatcher) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                themeEditorEventProvider.eventFlow.collect(::collectThemeEvent)
            }
        }
    }

    override fun detachView() {
        listeners.clear()
        collectJob?.cancel()
        collectJob = null
        super.detachView()
    }

    private fun collectThemeEvent(event: ThemeEditorEvent?) {
        if (event == null) return

        when (event) {
            is ThemeEditorEvent.OpenColorPicker -> {
                currentOverwrittenKey = event.overwrittenKey

                val color = event.currentColor ?: themeState.accent
                view.openColorPicker(color)
            }

            is ThemeEditorEvent.OpenMoreOptions -> {
                val styles = getAvailableStyles(themeState.platform)
                val capabilitiesByStyle = styles.associate {
                    it.id to getTemplateCapabilities(themeState.copy(style = it.id))
                }
                view.openMoreOptions(themeState, styles, capabilitiesByStyle)
            }

            is ThemeEditorEvent.ResetOverwrittenColors -> {
                currentOverwrittenKey = null
                updateThemeSate(themeState.copy(overwrittenColors = emptyMap()))
            }

            is ThemeEditorEvent.ChangePlatform -> {
                updateThemeSate(themeState.copy(platform = event.platform))
            }

            is ThemeEditorEvent.ExportTheme -> {
                val file = getAtthemeFile(themeState)
                view.shareThemeFile(file)
            }

            is ThemeEditorEvent.SubscribeOnColorChanges -> {
                listeners.add(event.listener)
                event.listener.onStateChange(themeState)
            }

            is ThemeEditorEvent.UnsubscribeFromColorChanges -> {
                listeners.remove(event.listener)
            }
        }
        themeEditorEventProvider.eventFlow.update { null } // clear event
    }

    override fun onColorChanged(color: Int) {
        val newState = if (currentOverwrittenKey == null) {
            themeState.copy(accent = color, isMonet = false)
        } else {
            val overwrittenMap = themeState.overwrittenColors.toMutableMap()
            currentOverwrittenKey?.let {
                it.similarKeys.forEach { name ->
                    overwrittenMap[name] = color
                }
            }

            themeState.copy(overwrittenColors = overwrittenMap)
        }

        updateThemeSate(newState)
    }

    override fun onPropertyChange(themeState: ThemeState) {
        updateThemeSate(themeState)
    }

    override fun getShareDescription(): String =
        "$SHARE_TEXT\n\n$THERXMV_MENTION\n$BLANDO_MENTION"

    private fun updateThemeSate(newState: ThemeState) {
        themeState = clampToCapabilities(newState)
        saveTheme(themeState)

        listeners.forEach {
            it.onStateChange(themeState)
        }
    }

    /**
     * Forces the toggles that depend on template availability/platform back
     * to a valid combination, since [state] may have come from before a
     * style/platform switch that no longer supports them (or from a cached
     * [ThemeState] predating a remote change) - the source of truth for what
     * gets persisted/exported, independent of whether the UI that produced
     * [state] already hid the corresponding toggle.
     */
    private fun clampToCapabilities(state: ThemeState): ThemeState {
        val capabilities = getTemplateCapabilities(state)
        // Either variant could be the one missing - a dark-only style must
        // resolve to dark, not just fall back to light like a light-only one.
        val isDark = when {
            state.isDark && capabilities.hasDark -> true
            !state.isDark && capabilities.hasLight -> false
            else -> capabilities.hasDark
        }

        return state.copy(
            // A ThemeState cached before this app version had `style` as an
            // uppercase enum name (e.g. "SOZA") - lowercasing here, the one
            // place ThemeState gets sanitized before anything downstream sees
            // it, keeps it matching TemplateStyle.id (always lowercase) for
            // the rest of the app, e.g. the style picker's label lookup.
            style = state.style.lowercase(),
            isDark = isDark,
            isAmoled = state.isAmoled && isDark,
            isGradient = state.isGradient && capabilities.hasGradient,
            isMonet = state.isMonet && isMonetAvailable(),
        )
    }
}