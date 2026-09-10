package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import javax.inject.Inject

class SaveRecentAccentColorsUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {

    operator fun invoke(colors: List<Int>) {
        themeDataSource.saveRecentAccentColors(colors)
    }
}
