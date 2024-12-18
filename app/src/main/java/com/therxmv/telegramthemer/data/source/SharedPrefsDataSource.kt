package com.therxmv.telegramthemer.data.source

import com.tencent.mmkv.MMKV
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import javax.inject.Inject

class SharedPrefsDataSource @Inject constructor(
    private val mmkv: MMKV, // TODO try data store lib
) : SharedPrefsSource {

    companion object {
        private const val SHARED_PREFS_THEME_KEY = "ThemeStateKey"
    }

    override fun saveThemeState(themeState: ThemeState) {
        mmkv.encode(SHARED_PREFS_THEME_KEY, themeState)
    }

    override fun getThemeState(): ThemeState =
        mmkv.decodeParcelable(SHARED_PREFS_THEME_KEY, ThemeState::class.java) ?: ThemeState()
}