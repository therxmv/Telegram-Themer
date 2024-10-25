package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.File
import javax.inject.Inject

class GetAtthemeFileUseCase @Inject constructor(
    private val themeFileAdapter: ThemeFileAdapter,
) {

    operator fun invoke(themeState: ThemeState): File =
        themeFileAdapter.createThemeFile(themeState)
}