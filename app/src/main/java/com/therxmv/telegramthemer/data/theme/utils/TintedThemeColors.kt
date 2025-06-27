package com.therxmv.telegramthemer.data.theme.utils

import android.graphics.Color

/**
 * Specific list of all [TintedColor].
 */
data class TintedThemeColors(
    val colors: List<TintedColor>,
) {
    operator fun get(name: String): Int =
        getHex(name)?.let { Color.parseColor(it) } ?: Color.BLACK

    private fun getHex(name: String): String? =
        colors.firstOrNull { it.name == name }?.value
}

/**
 * [name] consists of actual name and luminosity number, the higher number - the lighter color.
 * From 1 to 9, 5 is default.
 * [value] represents color in hexadecimal format
 */
data class TintedColor(
    val name: String,
    val value: String,
)