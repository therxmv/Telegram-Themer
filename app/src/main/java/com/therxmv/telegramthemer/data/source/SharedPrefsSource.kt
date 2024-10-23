package com.therxmv.telegramthemer.data.source

import com.therxmv.telegramthemer.ui.editor.data.ThemeState

interface SharedPrefsSource {
    fun saveThemeState(themeState: ThemeState)
    fun getThemeState(): ThemeState
}