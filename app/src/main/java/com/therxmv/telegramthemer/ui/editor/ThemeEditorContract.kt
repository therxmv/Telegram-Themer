package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.ui.base.BasePresenter
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import java.io.File

interface ThemeEditorContract {

    interface View {
        fun openColorPicker(currentColor: Int)
        fun openMoreOptions(themeState: ThemeState)
        fun shareThemeFile(file: File)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract fun onColorChanged(color: Int)
        abstract fun onPropertyChange(themeState: ThemeState)
        abstract fun getShareDescription(): String
    }
}