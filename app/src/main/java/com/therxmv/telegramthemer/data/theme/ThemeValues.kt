package com.therxmv.telegramthemer.data.theme

import com.therxmv.telegramthemer.domain.model.ThemeState

interface ThemeValues {
    fun getAdvancedColorSchema(state: ThemeState): Map<String, String>
    fun getAtthemeMap(state: ThemeState): Map<String, String>
}