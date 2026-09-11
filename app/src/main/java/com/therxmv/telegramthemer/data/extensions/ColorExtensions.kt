package com.therxmv.telegramthemer.data.extensions

/**
 * Converts list of red, green and blue to hexadecimal color
 *
 * Input: [80, 170, 255]; Output: "#50AAFF"
 */
fun List<Int>.rgbToHex() = String.format("#%02x%02x%02x", this[0], this[1], this[2])

/**
 * Converts hexadecimal color to list of red, green and blue.
 *
 * Input: "#50AAFF"; Output: [80, 170, 255]
 */
fun String.hexToRgb() = listOf(
    this.substring(1, 3).toInt(16),
    this.substring(3, 5).toInt(16),
    this.substring(5, 7).toInt(16),
)

/**
 * Converts an Integer ARGB color to a hexadecimal color string, keeping the
 * alpha byte whenever the color isn't fully opaque.
 *
 * Input: 0xFF50AAFF; Output: "#50aaff"
 * Input: 0x77299FE9; Output: "#77299fe9"
 * Input: 0x00000000; Output: "#00000000"
 */
fun Int.colorToHex(): String {
    val alpha = (this ushr 24) and 0xFF
    val rgb = "%06x".format(this and 0xFFFFFF)
    return if (alpha == 0xFF) "#$rgb" else "#%02x%s".format(alpha, rgb)
}