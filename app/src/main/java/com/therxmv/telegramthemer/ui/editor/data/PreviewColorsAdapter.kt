package com.therxmv.telegramthemer.ui.editor.data

import com.therxmv.preview.PreviewColorsModel

interface PreviewColorsAdapter {
    fun getDefaultThemeColors(themeState: ThemeState): PreviewColorsModel
}