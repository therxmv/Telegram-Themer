package com.therxmv.telegramthemer.domain.usecase.storage

import com.therxmv.telegramthemer.data.source.SharedPrefsSource
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import javax.inject.Inject

class GetCachedThemeUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {

    operator fun invoke(): ThemeState =
        themeDataSource.getThemeState()
}