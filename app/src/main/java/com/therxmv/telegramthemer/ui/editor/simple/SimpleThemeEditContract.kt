package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.preview.PreviewColorsModel
import com.therxmv.telegramthemer.ui.base.BasePresenter

interface SimpleThemeEditContract {

    interface View {
        fun setUpColorPickerButton(onClick: () -> Unit)
        fun setUpMoreOptionsButton(onClick: () -> Unit)
        fun setUpExportButton(onClick: () -> Unit)
        fun setColorPickerColors(accent: Int, background: Int)
        fun setPreviewColors(colors: PreviewColorsModel)
    }

    abstract class Presenter : BasePresenter<View>() {

    }
}