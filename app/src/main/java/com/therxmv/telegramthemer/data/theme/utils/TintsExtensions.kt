package com.therxmv.telegramthemer.data.theme.utils

/**
 * Generates list of color tints from 0 to 10 luminosity. 0 is black and 10 is white
 */
fun generateAllTints(baseColor: String): List<String> =
    (0..10).map {
        when {
            it < 5 -> baseColor.getDarkerColor(1 - it * 0.2f)
            it > 5 -> baseColor.getLighterColor((it - 5) * 0.2f)
            else -> baseColor
        }
    }

fun String.getLighterColor(factor: Float): String {
    val rgbList = this.hexToRgb()
    val lighterList = rgbList.map { color ->
        (color + (factor * (255 - color))).toInt().coerceIn(0, 255)
    }

    return lighterList.rgbToHex()
}

fun String.getDarkerColor(factor: Float): String {
    val rgbList = this.hexToRgb()
    val darkerList = rgbList.map { color ->
        (color * (1 - factor)).toInt().coerceIn(0, 255)
    }

    return darkerList.rgbToHex()
}