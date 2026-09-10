package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateCapabilities
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.base.BasePresenter

interface SimpleThemeEditContract {

    interface View {
        fun setUpColorPicker(onOpenColorPicker: () -> Unit)
        fun setUpAccentSwatches(onSwatchClicked: (index: Int) -> Unit)
        fun setUpStyleSelector(onStyleSelected: (index: Int) -> Unit)
        fun setUpToggles(
            onDarkToggled: (Boolean) -> Unit,
            onAmoledToggled: (Boolean) -> Unit,
            onMonetToggled: (Boolean) -> Unit,
            onGradientToggled: (Boolean) -> Unit,
        )
        fun setUpExportButton(onClick: () -> Unit)
        fun setUpPlatformButtons(onAndroidClick: () -> Unit, onIosClick: () -> Unit)
        fun renderOptionsCard(
            themeState: ThemeState,
            styles: List<TemplateStyle>,
            capabilities: TemplateCapabilities,
            recentAccentColors: List<Int>,
        )
        fun setPreviewColors(colors: PreviewColorsModel)
        fun setPlatformSelection(platform: Platform)
        fun startPreviewAnimation(newGradient: IntArray, oldGradient: IntArray = newGradient.reversedArray())
    }

    abstract class Presenter : BasePresenter<View>() {

    }
}
