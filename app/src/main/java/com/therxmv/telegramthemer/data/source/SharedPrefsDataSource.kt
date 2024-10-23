package com.therxmv.telegramthemer.data.source

import com.tencent.mmkv.MMKV
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import com.therxmv.telegramthemer.utils.SHARED_PREFS_THEME_KEY
import javax.inject.Inject

class SharedPrefsDataSource @Inject constructor(
    private val mmkv: MMKV,
) : SharedPrefsSource {

    override fun saveThemeState(themeState: ThemeState) {
        mmkv.encode(SHARED_PREFS_THEME_KEY, themeState)
    }

    override fun getThemeState(): ThemeState =
        mmkv.decodeParcelable(SHARED_PREFS_THEME_KEY, ThemeState::class.java) ?: ThemeState()
}