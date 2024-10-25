package com.therxmv.telegramthemer.domain.adapter

import com.therxmv.preview.PreviewColorsModel
import com.therxmv.telegramthemer.domain.model.ThemeState

interface PreviewColorsAdapter {
    fun getThemePreviewColors(themeState: ThemeState): PreviewColorsModel
}