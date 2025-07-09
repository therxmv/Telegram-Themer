package com.therxmv.telegramthemer.domain.model

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

fun gray(luminosity: Int) = "gray_$luminosity"
fun accent(luminosity: Int) = "accent_$luminosity"

fun red(luminosity: Int) = "red_$luminosity"
fun orange(luminosity: Int) = "orange_$luminosity"
fun yellow(luminosity: Int) = "yellow_$luminosity"
fun green(luminosity: Int) = "green_$luminosity"
fun blue(luminosity: Int) = "blue_$luminosity"
fun purple(luminosity: Int) = "purple_$luminosity"

fun transparent(luminosity: Int) = "transparent_$luminosity"
fun trAccent(luminosity: Int) = "tr_accent_$luminosity"
fun trGray(luminosity: Int) = "tr_gray_$luminosity"