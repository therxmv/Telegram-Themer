package com.therxmv.telegramthemer.ui

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.domain.usecase.InvalidState
import com.therxmv.telegramthemer.domain.usecase.IsValidInputUseCase
import com.therxmv.telegramthemer.domain.usecase.storage.GetCachedThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.storage.SaveThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.theme.CreatePreviewUseCase
import com.therxmv.telegramthemer.domain.usecase.theme.CreateThemeUseCase
import com.therxmv.telegramthemer.domain.usecase.theme.GetColorTintsUseCase
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_200
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
import kotlinx.coroutines.flow.update
import java.io.File

class MainViewModel(
    private val getColorTintsUseCase: GetColorTintsUseCase,
    private val createThemeUseCase: CreateThemeUseCase,
    private val createPreviewUseCase: CreatePreviewUseCase,
    private val getCachedThemeUseCase: GetCachedThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val isValidInputUseCase: IsValidInputUseCase,
) : ViewModel() {

    private val _themePropsState = MutableStateFlow(ThemeModel())
    val themePropsState = _themePropsState.asStateFlow()

    private val _inputValidationState = MutableStateFlow<InvalidState?>(null)
    val inputValidationState = _inputValidationState.asStateFlow()

    fun loadFromSharedPrefs() {
        _themePropsState.update { getCachedThemeUseCase() }
    }

    fun saveToSharedPrefs() {
        saveThemeUseCase(_themePropsState.value)
    }

    fun isValidInput(input: String): Boolean {
        val state = isValidInputUseCase(input)

        _inputValidationState.update { state }

        return state == null
    }

    private fun createTheme(context: Context, templateFile: String, themeModel: ThemeModel, imageView: ImageView): String {
        val tints = getColorTintsUseCase(themeModel)

        val theme = createThemeUseCase(context, templateFile, tints, themeModel)

        createPreviewUseCase(context, theme, tints[ACCENT_200].orEmpty(), imageView)

        return theme
    }

    /**
     * Creates theme file in files directory from template
     */
    fun createThemeFile(context: Context, preview: ImageView) {
        val (fileName, templateFileName) = getFilesNames()

        val templateFile = context.assets.open(templateFileName).bufferedReader().readText()

        // creating new theme from template
        val fileString = createTheme(context, templateFile, _themePropsState.value, preview)

        File(
            context.filesDir,
            fileName
        ).writeText(fileString)
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
        _themePropsState.update {
            it.copy(
                color = hex ?: it.color,
                isDefault = default ?: it.isDefault,
                isDark = dark ?: it.isDark,
                isAmoled = amoled ?: it.isAmoled,
                isMonet = monet ?: it.isMonet,
                isMonetBackground = monetBg ?: it.isMonetBackground,
                isGradient = gradient ?: it.isGradient,
            )
        }
    }

    /**
     * Return list with two names. First for file to share, second for template file
     */
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

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                GetColorTintsUseCase(),
                CreateThemeUseCase(),
                CreatePreviewUseCase(),
                GetCachedThemeUseCase(),
                SaveThemeUseCase(),
                IsValidInputUseCase(),
            ) as T
        }
    }
}