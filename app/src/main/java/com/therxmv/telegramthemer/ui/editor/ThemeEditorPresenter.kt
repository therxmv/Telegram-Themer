package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.usecase.GetAtthemeFileUseCase
import com.therxmv.telegramthemer.domain.usecase.GetCachedThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.SaveThemeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ThemeEditorPresenter @Inject constructor(
    @Named("Default") private val defaultDispatcher: CoroutineDispatcher,
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    getCachedTheme: GetCachedThemeUseCase,
    private val saveTheme: SaveThemeUseCase,
    private val getAtthemeFile: GetAtthemeFileUseCase,
) : ThemeEditorContract.Presenter() {

    companion object {
        private const val SHARE_TEXT = "Theme made via play.google.com/store/apps/details?id=com.therxmv.telegramthemer"
        private const val THERXMV_MENTION = "@therxmv_channel"
        private const val BLANDO_MENTION = "@BlandoThemes"
    }

    private var themeState = getCachedTheme()
    private val listeners: MutableList<ThemeStateListener> = mutableListOf()

    override fun attachView(view: ThemeEditorContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        coroutineScope.launch(defaultDispatcher) {
            themeEditorEventProvider.eventFlow.collect(::collectThemeEvent)
        }
    }

    override fun detachView() {
        listeners.clear()
        super.detachView()
    }

    private fun collectThemeEvent(event: ThemeEditorEvent?) {
        if (event == null) return

        when (event) {
            is ThemeEditorEvent.OpenColorPicker -> {
                view.openColorPicker(themeState.accent)
            }

            is ThemeEditorEvent.OpenMoreOptions -> {
                view.openMoreOptions(themeState)
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
        updateThemeSate(themeState.copy(accent = color, isMonet = false))
    }

    override fun onPropertyChange(themeState: ThemeState) {
        updateThemeSate(themeState)
    }

    override fun getShareDescription(): String =
        "$SHARE_TEXT\n\n$THERXMV_MENTION\n$BLANDO_MENTION"

    private fun updateThemeSate(newState: ThemeState) {
        themeState = newState
        saveTheme(themeState)

        listeners.forEach {
            it.onStateChange(themeState)
        }
    }
}