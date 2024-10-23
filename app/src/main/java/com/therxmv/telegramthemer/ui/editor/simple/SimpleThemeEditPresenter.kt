package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import com.therxmv.telegramthemer.ui.editor.ThemeStateListener
import com.therxmv.telegramthemer.ui.editor.data.PreviewColorsAdapter
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    private val previewColorsAdapter: PreviewColorsAdapter,
): SimpleThemeEditContract.Presenter(), ThemeStateListener {

    override fun attachView(view: SimpleThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.SubscribeOnColorChanges(this@SimpleThemeEditPresenter)
        }

        view.setUpColorPickerButton {
            themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker }
        }
        view.setUpMoreOptionsButton {
            themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenMoreOptions }
        }
    }

    override fun detachView() {
        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.UnsubscribeFromColorChanges(this@SimpleThemeEditPresenter)
        }
        super.detachView()
    }

    override fun onStateChange(themeState: ThemeState) { // TODO change button border and theme background
        val model = previewColorsAdapter.getDefaultThemeColors(themeState)
        view.setColorPickerColors(model.accent, model.background)
        view.setPreviewColors(model)
    }
}