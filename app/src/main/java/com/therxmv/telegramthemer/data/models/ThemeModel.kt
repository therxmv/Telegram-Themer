package com.therxmv.telegramthemer.data.models

import com.therxmv.telegramthemer.utils.DEFAULT_COLOR

class ThemeModel(
    val color: String = DEFAULT_COLOR,
    val isDefault: Boolean = true,
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isGradient: Boolean = false,
) {
    fun copy(
        color: String = this.color,
        isDefault: Boolean = this.isDefault,
        isDark: Boolean = this.isDark,
        isAmoled: Boolean = this.isAmoled,
        isMonet: Boolean = this.isMonet,
        isGradient: Boolean = this.isGradient,
    ) = ThemeModel(
        color,
        isDefault,
        isDark,
        isAmoled,
        isMonet,
        isGradient
    )
}
