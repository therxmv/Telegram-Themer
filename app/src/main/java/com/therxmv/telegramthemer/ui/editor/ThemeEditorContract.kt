package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.domain.model.TemplateCapabilities
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.base.BasePresenter
import java.io.File

interface ThemeEditorContract {

    interface View {
        fun openColorPicker(currentColor: Int)
        fun openMoreOptions(
            themeState: ThemeState,
            styles: List<TemplateStyle>,
            capabilitiesByStyle: Map<String, TemplateCapabilities>,
        )
        fun shareThemeFile(file: File)
    }

    abstract class Presenter: BasePresenter<View>() {
        abstract val areTemplatesReady: Boolean
        abstract fun onColorChanged(color: Int)
        abstract fun onPropertyChange(themeState: ThemeState)
        abstract fun getShareDescription(): String
    }
}