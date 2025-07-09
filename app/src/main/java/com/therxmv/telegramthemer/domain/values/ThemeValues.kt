package com.therxmv.telegramthemer.domain.values

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.model.TintedThemeColors

interface ThemeValues {
    fun getTintedColorSchema(state: ThemeState): TintedThemeColors
    fun getAtthemeMap(state: ThemeState): Map<String, String>
}