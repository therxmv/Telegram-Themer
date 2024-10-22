package com.therxmv.telegramthemer.ui.editor.data

fun List<Int>.rgbToHex() = String.format("#%02x%02x%02x", this[0], this[1], this[2])

fun String.hexToRgb() = listOf(
    this.substring(1, 3).toInt(16),
    this.substring(3, 5).toInt(16),
    this.substring(5, 7).toInt(16),
)

fun Int.colorToHex(): String =
    Integer.toHexString(this).drop(2)