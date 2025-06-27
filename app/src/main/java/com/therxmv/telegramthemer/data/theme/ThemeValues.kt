package com.therxmv.telegramthemer.data.theme

import com.therxmv.telegramthemer.data.theme.utils.TintedThemeColors
import com.therxmv.telegramthemer.domain.model.ThemeState

interface ThemeValues {
    fun getTintedColorSchema(state: ThemeState): TintedThemeColors
    fun getAtthemeMap(state: ThemeState): Map<String, String>
}