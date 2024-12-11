package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import javax.inject.Inject

class GetCachedThemeUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {

    operator fun invoke(): ThemeState =
        themeDataSource.getThemeState()
}