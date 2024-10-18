package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.ui.base.BasePresenter

interface ThemeEditorContract {

    interface View {
        fun openColorPicker()
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun openColorPicker()
        abstract fun onColorChanged(color: Int)
    }
}