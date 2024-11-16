package com.therxmv.telegramthemer.domain.adapter

import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.domain.model.ThemeState

interface PreviewColorsAdapter {
    fun getThemePreviewColors(themeState: ThemeState): PreviewColorsModel
}