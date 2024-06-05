package com.therxmv.telegramthemer.data.source

import com.tencent.mmkv.MMKV
import com.therxmv.telegramthemer.domain.model.ThemeModel
import com.therxmv.telegramthemer.utils.SHARED_PREFS_THEME

class ThemeSharedPrefsDataSource: ThemeDataSource {
    private val mmkv = MMKV.defaultMMKV()

    override fun load() = mmkv.decodeParcelable(SHARED_PREFS_THEME, ThemeModel::class.java) ?: ThemeModel()

    override fun save(theme: ThemeModel) {
        mmkv.encode(SHARED_PREFS_THEME, theme)
    }
}