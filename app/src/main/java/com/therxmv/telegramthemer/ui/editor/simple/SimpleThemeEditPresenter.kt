package com.therxmv.telegramthemer.ui.editor.simple

import androidx.lifecycle.lifecycleScope
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.usecase.GetAvailableStylesUseCase
import com.therxmv.telegramthemer.domain.usecase.GetMonetAccentColorUseCase
import com.therxmv.telegramthemer.domain.usecase.GetPreviewColorsModelUseCase
import com.therxmv.telegramthemer.domain.usecase.GetRecentAccentColorsUseCase
import com.therxmv.telegramthemer.domain.usecase.GetTemplateCapabilitiesUseCase
import com.therxmv.telegramthemer.domain.usecase.SaveRecentAccentColorsUseCase
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import com.therxmv.telegramthemer.ui.editor.ThemeStateListener
import com.therxmv.telegramthemer.ui.editor.simple.SimpleThemeEditPresenter.Companion.MAX_RECENT_ACCENT_COLORS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    private val getPreviewColorsModel: GetPreviewColorsModelUseCase,
    private val getAvailableStyles: GetAvailableStylesUseCase,
    private val getTemplateCapabilities: GetTemplateCapabilitiesUseCase,
    private val getRecentAccentColors: GetRecentAccentColorsUseCase,
    private val saveRecentAccentColors: SaveRecentAccentColorsUseCase,
    private val getMonetAccentColor: GetMonetAccentColorUseCase,
    @Named("Main") private val mainDispatcher: CoroutineDispatcher,
) : SimpleThemeEditContract.Presenter(), ThemeStateListener {

    companion object {
        private const val MAX_RECENT_ACCENT_COLORS = 5
    }

    private var currentState: ThemeState? = null
    private var availableStyles: List<TemplateStyle> = emptyList()

    private var recentAccentColors: List<Int> = getRecentAccentColors()

    private var previewAnimationJob: Job? = null

    override fun attachView(view: SimpleThemeEditContract.View) {
        super.attachView(view)

        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.SubscribeOnColorChanges(this@SimpleThemeEditPresenter)
        }

        with(view) {
            setUpColorPicker {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker() }
            }
            setUpAccentSwatches { index ->
                val state = currentState ?: return@setUpAccentSwatches
                val color = recentAccentColors.getOrNull(index) ?: return@setUpAccentSwatches
                updateThemeState(state.copy(accent = color, isMonet = false))
            }
            setUpStyleSelector { index ->
                val state = currentState ?: return@setUpStyleSelector
                val style = availableStyles.getOrNull(index) ?: return@setUpStyleSelector
                updateThemeState(state.copy(style = style.id))
            }
            setUpToggles(
                onDarkToggled = { isDark ->
                    val state = currentState ?: return@setUpToggles
                    val isAmoled = state.isAmoled.takeIf { isDark } ?: false
                    applyToggle(state.copy(isDark = isDark, isAmoled = isAmoled))
                },
                onAmoledToggled = { isAmoled ->
                    val state = currentState ?: return@setUpToggles
                    val isDark = state.isDark.takeIf { !isAmoled } ?: true
                    applyToggle(state.copy(isDark = isDark, isAmoled = isAmoled))
                },
                onMonetToggled = { isMonet ->
                    val state = currentState ?: return@setUpToggles
                    applyToggle(state.copy(isMonet = isMonet))
                },
                onGradientToggled = { isGradient ->
                    val state = currentState ?: return@setUpToggles
                    updateThemeState(state.copy(isGradient = isGradient))
                },
            )
            setUpExportButton {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.ExportTheme }
            }
            setUpPlatformButtons(
                onAndroidClick = {
                    themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.ChangePlatform(Platform.ANDROID) }
                },
                onIosClick = {
                    themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.ChangePlatform(Platform.IOS) }
                },
            )
        }
    }

    override fun detachView() {
        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.UnsubscribeFromColorChanges(this@SimpleThemeEditPresenter)
        }
        currentState = null
        previewAnimationJob?.cancel()
        previewAnimationJob = null
        super.detachView()
    }

    override fun onStateChange(themeState: ThemeState) {
        if (themeState == currentState) return

        val model = getPreviewColorsModel(themeState)

        if (currentState == null // Animate on start
            || themeState.isMonet // On monet checkbox
            || currentState?.accent == themeState.accent // On other checkboxes
        ) {
            animatePreviewBackground(model.previewGradient)
        }

        view.setPreviewColors(model)
        view.setPlatformSelection(themeState.platform)

        if (currentState?.platform != themeState.platform) {
            availableStyles = getAvailableStyles(themeState.platform)
        }
        currentState = themeState

        // Covers Monet already being on when this screen (re)attaches - e.g.
        // app restart - since then nothing went through onMonetToggled/
        // applyToggle below.
        if (themeState.isMonet && recentAccentColors.firstOrNull() != themeState.accent) {
            addRecentAccentColor(themeState.accent)
        } else {
            renderOptionsCard()
        }
    }

    override fun onColorPickerClosed() {
        currentState?.accent?.let(::addRecentAccentColor)
    }

    /**
     * Commits [edited], first re-resolving the Monet accent if it leaves
     * Monet on - a Dark/Amoled toggle changes which system color (light/dark
     * variant) that resolves to - and joining the recents row the same way a
     * custom pick does.
     */
    private fun applyToggle(edited: ThemeState) {
        val newState = if (edited.isMonet) edited.copy(accent = getMonetAccentColor(edited.isDark)) else edited
        updateThemeState(newState)
        if (edited.isMonet) addRecentAccentColor(newState.accent)
    }

    private fun updateThemeState(newState: ThemeState) {
        themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.UpdateThemeProperties(newState) }
    }

    /**
     * Moves [color] to the front of the recent-colors row (deduping it if
     * already there), dropping the oldest entry past [MAX_RECENT_ACCENT_COLORS],
     * and persists the result.
     */
    private fun addRecentAccentColor(color: Int) {
        recentAccentColors = (listOf(color) + recentAccentColors.filterNot { it == color })
            .take(MAX_RECENT_ACCENT_COLORS)
        saveRecentAccentColors(recentAccentColors)
        renderOptionsCard()
    }

    private fun renderOptionsCard() {
        val themeState = currentState ?: return
        val capabilities = getTemplateCapabilities(themeState)
        view.renderOptionsCard(themeState, availableStyles, capabilities, recentAccentColors)
    }

    private fun animatePreviewBackground(gradient: List<Int>) {
        previewAnimationJob?.cancel()
        previewAnimationJob = lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
            view.startPreviewAnimation(gradient.toIntArray())
        }
    }
}