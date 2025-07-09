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
 * Converts Integer color to hexadecimal color without transparency
 *
 * Input: 0xFF50AAFF; Output: "#50AAFF"
 */
fun Int.colorToHex(): String =
    "#" + Integer.toHexString(this).drop(2) // TODO think about transparency