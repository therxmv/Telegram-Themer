package com.therxmv.telegramthemer.domain.usecase.storage

import com.therxmv.telegramthemer.data.source.SharedPrefsSource
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {

    operator fun invoke(themeState: ThemeState) {
        themeDataSource.saveThemeState(themeState)
    }
}