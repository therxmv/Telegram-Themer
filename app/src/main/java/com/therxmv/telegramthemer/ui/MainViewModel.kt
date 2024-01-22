package com.therxmv.telegramthemer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.data.source.ThemeDataSource
import com.therxmv.telegramthemer.data.source.ThemeSharedPrefsDataSource
import com.therxmv.telegramthemer.utils.ThemeFileNames.DEFAULT_AMOLED_THEME
import com.therxmv.telegramthemer.utils.ThemeFileNames.DEFAULT_DAY_TEMPLATE
import com.therxmv.telegramthemer.utils.ThemeFileNames.DEFAULT_DAY_THEME
import com.therxmv.telegramthemer.utils.ThemeFileNames.DEFAULT_NIGHT_TEMPLATE
import com.therxmv.telegramthemer.utils.ThemeFileNames.DEFAULT_NIGHT_THEME
import com.therxmv.telegramthemer.utils.ThemeFileNames.SOZA_AMOLED_THEME
import com.therxmv.telegramthemer.utils.ThemeFileNames.SOZA_DAY_TEMPLATE
import com.therxmv.telegramthemer.utils.ThemeFileNames.SOZA_DAY_THEME
import com.therxmv.telegramthemer.utils.ThemeFileNames.SOZA_NIGHT_TEMPLATE
import com.therxmv.telegramthemer.utils.ThemeFileNames.SOZA_NIGHT_THEME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val themeSharedPrefsDataSource: ThemeDataSource,
) : ViewModel() {
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
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

    fun setThemeProperties(
        hex: String? = null,
        default: Boolean? = null,
        dark: Boolean? = null,
        amoled: Boolean? = null,
        monet: Boolean? = null,
        monetBg: Boolean? = null,
        gradient: Boolean? = null,
    ) {
        with(_themePropsState.value) {
            _themePropsState.value = copy(
                color = hex ?: this.color,
                isDefault = default ?: this.isDefault,
                isDark = dark ?: this.isDark,
                isAmoled = amoled ?: this.isAmoled,
                isMonet = monet ?: this.isMonet,
                isMonetBackground = monetBg ?: this.isMonetBackground,
                isGradient = gradient ?: this.isGradient,
            )
        }
    }

    fun getFilesNames(): List<String> {
        val filesNames = mutableListOf(DEFAULT_DAY_THEME, DEFAULT_DAY_TEMPLATE)
        val theme = _themePropsState.value

        if (theme.isDefault) {
            if (theme.isDark) {
                filesNames[0] = if (theme.isAmoled) DEFAULT_AMOLED_THEME else DEFAULT_NIGHT_THEME
                filesNames[1] = DEFAULT_NIGHT_TEMPLATE
            } else {
                filesNames[0] = DEFAULT_DAY_THEME
                filesNames[1] = DEFAULT_DAY_TEMPLATE
            }
        } else {
            if (theme.isDark) {
                filesNames[0] = if (theme.isAmoled) SOZA_AMOLED_THEME else SOZA_NIGHT_THEME
                filesNames[1] = SOZA_NIGHT_TEMPLATE
            } else {
                filesNames[0] = SOZA_DAY_THEME
                filesNames[1] = SOZA_DAY_TEMPLATE
            }
        }

        return filesNames
    }
}