package com.therxmv.telegramthemer.ui.editor.simple

import javax.inject.Inject

class SimpleThemeEditPresenter @Inject constructor(
//    private val parentPresenter: ThemeEditorContract.Presenter,
): SimpleThemeEditContract.Presenter() {

    override fun attachView(view: SimpleThemeEditContract.View) {
        super.attachView(view)

        view.setUpColorPickerButton {
//            parentPresenter.openColorPicker()
        }
    }
}