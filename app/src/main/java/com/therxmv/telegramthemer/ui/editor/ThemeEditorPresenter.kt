package com.therxmv.telegramthemer.ui.editor

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

    private val listeners: MutableList<(Int) -> Unit> = mutableListOf()

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
                listeners.add(event.onColorChange)
                view.openColorPicker()
            }
        }
        themeEditorEventProvider.eventFlow.update { null } // clear event
    }

    override fun openColorPicker() {
        view.openColorPicker()
    }

    override fun onColorChanged(color: Int) {
        // TODO probably save color
        listeners.forEach {
            it(color)
        }
    }
}