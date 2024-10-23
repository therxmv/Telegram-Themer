package com.therxmv.telegramthemer.ui.editor

import android.graphics.Color
import com.therxmv.telegramthemer.ui.editor.data.Styles
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
) : ThemeEditorContract.Presenter() {

    private var themeState = ThemeState(
        style = Styles.DEFAULT,
        accent = Color.parseColor("#299FE9"), // TODO constant
        isDark = false,
        isAmoled = false,
        isMonet = false,
        isGradient = false,
    )
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
        themeState = themeState.copy(accent = color, isMonet = false)
        listeners.forEach {
            it.onStateChange(themeState)
        }
    }

    override fun onPropertyChange(themeState: ThemeState) {
        this.themeState = themeState
        listeners.forEach {
            it.onStateChange(themeState)
        }
    }
}