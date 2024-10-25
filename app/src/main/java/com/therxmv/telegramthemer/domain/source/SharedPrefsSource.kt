package com.therxmv.telegramthemer.domain.source

import com.therxmv.telegramthemer.domain.model.ThemeState

interface SharedPrefsSource {
    fun saveThemeState(themeState: ThemeState)
    fun getThemeState(): ThemeState
}