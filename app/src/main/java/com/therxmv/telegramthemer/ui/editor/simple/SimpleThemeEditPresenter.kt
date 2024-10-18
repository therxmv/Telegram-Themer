package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
): SimpleThemeEditContract.Presenter() {

    override fun attachView(view: SimpleThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        view.setUpColorPickerButton {
            themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker }
        }
    }
}