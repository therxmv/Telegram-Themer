package com.therxmv.telegramthemer.ui.editor

import android.graphics.Color
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

    private var currentColor = Color.parseColor("#299FE9") // TODO default color
    private val listeners: MutableList<ColorChangeListener> = mutableListOf()

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
                view.openColorPicker(currentColor)
            }

            is ThemeEditorEvent.SubscribeOnColorChanges -> {
                listeners.add(event.listener)
                event.listener.onColorChange(currentColor)
            }

            is ThemeEditorEvent.UnsubscribeFromColorChanges -> {
                listeners.remove(event.listener)
            }
        }
        themeEditorEventProvider.eventFlow.update { null } // clear event
    }

    override fun onColorChanged(color: Int) {
        currentColor = color
        listeners.forEach {
            it.onColorChange(color)
        }
    }
}