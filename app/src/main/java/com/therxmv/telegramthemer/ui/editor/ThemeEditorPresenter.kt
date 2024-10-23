package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.domain.usecase.storage.GetCachedThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.storage.SaveThemeUseCase
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
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
) : ThemeEditorContract.Presenter() {

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

    private fun updateThemeSate(newState: ThemeState) {
        themeState = newState
        saveTheme(themeState)

        listeners.forEach {
            it.onStateChange(themeState)
        }
    }
}