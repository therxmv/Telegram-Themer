package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.ui.base.BasePresenter

interface SimpleThemeEditContract {

    interface View {
        fun setUpColorPickerButton(onClick: () -> Unit)
        fun setUpMoreOptionsButton(onClick: () -> Unit)
        fun setUpExportButton(onClick: () -> Unit)
        fun setColorPickerColors(accent: Int, background: Int)
        fun setPreviewColors(colors: PreviewColorsModel)
        fun startPreviewAnimation(newGradient: IntArray, oldGradient: IntArray = newGradient.reversedArray())
    }

    abstract class Presenter : BasePresenter<View>() {

    }
}