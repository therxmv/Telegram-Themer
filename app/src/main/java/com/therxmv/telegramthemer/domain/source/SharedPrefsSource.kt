package com.therxmv.telegramthemer.domain.source

import com.therxmv.telegramthemer.domain.model.ThemeState

interface SharedPrefsSource {
    fun saveThemeState(themeState: ThemeState)
    fun getThemeState(): ThemeState
    fun saveRecentAccentColors(colors: List<Int>)
    fun getRecentAccentColors(): List<Int>
    fun saveLastReviewRequestTimestamp(timestamp: Long)
    fun getLastReviewRequestTimestamp(): Long
}