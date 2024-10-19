package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.ui.base.BasePresenter

interface ThemeEditorContract {

    interface View {
        fun openColorPicker(currentColor: Int)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun onColorChanged(color: Int)
    }
}