package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.preview.PreviewColorsModel
import com.therxmv.telegramthemer.ui.base.BasePresenter

interface SimpleThemeEditContract {

    interface View {
        fun setUpColorPickerButton(onClick: () -> Unit)
        fun setColorPickerBackground(color: Int)
        fun setThemeColors(colors: PreviewColorsModel)
    }

    abstract class Presenter : BasePresenter<View>() {

    }
}