package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.ui.base.BasePresenter
import java.io.File

interface ThemeEditorContract {

    interface View {
        fun openColorPicker(currentColor: Int)
        fun shareThemeFile(file: File)
        fun requestInAppReview()
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract val areTemplatesReady: Boolean
        abstract fun onColorChanged(color: Int)
        abstract fun onColorPickerClosed()
        abstract fun getShareDescription(): String
    }
}