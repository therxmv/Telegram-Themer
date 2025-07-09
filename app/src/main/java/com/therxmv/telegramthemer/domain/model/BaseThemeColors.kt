package com.therxmv.telegramthemer.domain.model

/**
 * Base colors like main accent, background, gray
 */
data class BaseThemeColors(
    val background: String,
    val onBackground: String,
    val accent: String,
    val gray: String,
    val red: String = "#E3B727",
    val orange: String = "#DF9700",
    val yellow: String = "#E23333",
    val green: String = "#52CF2C",
    val blue: String = "#299FE9",
    val purple: String = "#776BF5",
    val transparent: String = "#00000000",
)