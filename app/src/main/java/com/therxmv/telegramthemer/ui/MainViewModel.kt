package com.therxmv.telegramthemer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.data.source.ThemeDataSource
import com.therxmv.telegramthemer.data.source.ThemeSharedPrefsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val themeSharedPrefsDataSource: ThemeDataSource
): ViewModel() {
    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, ): T {
            return MainViewModel(ThemeSharedPrefsDataSource()) as T
        }
    }

    private val _themePropsState = MutableStateFlow(ThemeModel())
    val themePropsState = _themePropsState.asStateFlow()

    fun loadFromSharedPrefs() {
        _themePropsState.value = themeSharedPrefsDataSource.load()
    }

    fun saveToSharedPrefs() {
        themeSharedPrefsDataSource.save(_themePropsState.value)
    }

    fun setThemeColor(hex: String) {
        _themePropsState.value = _themePropsState.value.copy(color = hex)
    }

    fun setThemeStyle(default: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isDefault = default)
    }

    fun setThemeMode(dark: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isDark = dark)
    }

    fun setThemeAmoled(amoled: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isAmoled = amoled)
    }

    fun setThemeMonet(monet: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isMonet = monet)
    }

    fun setThemeMonetBg(monetBg: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isMonetBackground = monetBg)
    }

    fun setThemeGradient(gradient: Boolean) {
        _themePropsState.value = _themePropsState.value.copy(isGradient = gradient)
    }

    fun getFilesNames(): List<String> {
        val filesNames = mutableListOf("theday.attheme", "theday_template.attheme")
        val theme = _themePropsState.value

        if(theme.isDefault) {
            if(theme.isDark) {
                filesNames[0] = if(theme.isAmoled) "TheAmoled.attheme" else "TheNight.attheme"
                filesNames[1] = "theday_dark_template.attheme"
            }
            else {
                filesNames[0] = "TheDay.attheme"
                filesNames[1] =  "theday_template.attheme"
            }
        }
        else {
            if(theme.isDark) {
                filesNames[0] = if(theme.isAmoled) "Soza Amoled.attheme" else "Soza Night.attheme"
                filesNames[1] = "thesoza_dark_template.attheme"
            }
            else {
                filesNames[0] = "Soza Day.attheme"
                filesNames[1] =  "thesoza_template.attheme"
            }
        }

        return filesNames
    }
}