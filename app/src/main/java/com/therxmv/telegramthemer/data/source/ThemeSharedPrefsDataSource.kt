package com.therxmv.telegramthemer.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.utils.SHARED_PREFS
import com.therxmv.telegramthemer.utils.SHARED_PREFS_THEME

class ThemeSharedPrefsDataSource(
    private val context: Context
): ThemeDataSource {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    override fun load(): ThemeModel {
        val defaultTheme = Gson().toJson(ThemeModel())
        return Gson().fromJson(sharedPreferences.getString(SHARED_PREFS_THEME, defaultTheme), ThemeModel::class.java)
    }

    override fun save(theme: ThemeModel) {
        sharedPreferences.edit {
            putString(SHARED_PREFS_THEME, Gson().toJson(theme))
            apply()
        }
    }
}