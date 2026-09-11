package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.preview.base.view.DEFAULT_ACCENT_COLOR
import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import javax.inject.Inject

class GetRecentAccentColorsUseCase @Inject constructor(
    private val themeDataSource: SharedPrefsSource,
) {
    companion object {
        private val DEFAULT_ACCENT_COLORS = listOf(
            DEFAULT_ACCENT_COLOR,
            0xFF7B5CE0.toInt(),
            0xFFE0568F.toInt(),
            0xFFE8973A.toInt(),
            0xFF2FB27A.toInt(),
            0xFFE05656.toInt(),
        )

        val MAX_RECENT_ACCENT_COLORS = DEFAULT_ACCENT_COLORS.size
    }

    operator fun invoke(): List<Int> = themeDataSource.getRecentAccentColors().ifEmpty { DEFAULT_ACCENT_COLORS }
}
