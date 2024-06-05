package com.therxmv.telegramthemer.domain.usecase.storage

import com.therxmv.telegramthemer.domain.model.ThemeModel
import com.therxmv.telegramthemer.data.source.ThemeDataSource
import com.therxmv.telegramthemer.data.source.ThemeSharedPrefsDataSource

class SaveThemeUseCase(
    private val themeDataSource: ThemeDataSource = ThemeSharedPrefsDataSource(),
) {

    operator fun invoke(themeModel: ThemeModel) {
        themeDataSource.save(themeModel)
    }
}