package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import javax.inject.Inject

class GetRecentAccentColorsUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {
    companion object {
        private val DEFAULT_ACCENT_COLORS = listOf(
            0xFF299FE9.toInt(),
            0xFF7B5CE0.toInt(),
            0xFFE0568F.toInt(),
            0xFFE8973A.toInt(),
            0xFF2FB27A.toInt(),
        )
    }

    operator fun invoke(): List<Int> = themeDataSource.getRecentAccentColors().ifEmpty { DEFAULT_ACCENT_COLORS }
}
