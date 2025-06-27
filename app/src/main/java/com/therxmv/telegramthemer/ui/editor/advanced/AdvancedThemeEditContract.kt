package com.therxmv.telegramthemer.ui.editor.advanced

import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.telegramthemer.ui.base.BasePresenter

interface AdvancedThemeEditContract {

    interface View {
        fun setPreviewColors(colors: PreviewColorsModel)
        fun setUpOnPreviewClick(action: (AtthemePreviewKeys, Int) -> Unit)
        fun setUpResetButton(onClick: () -> Unit)
    }

    abstract class Presenter : BasePresenter<View>()
}