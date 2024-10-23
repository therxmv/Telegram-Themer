package com.therxmv.telegramthemer.ui.editor.data.preview

import com.therxmv.preview.PreviewColorsModel
import com.therxmv.telegramthemer.ui.editor.data.ThemeState

interface PreviewColorsAdapter {
    fun getDefaultThemeColors(themeState: ThemeState): PreviewColorsModel
}