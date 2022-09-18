package com.therxmv.telegramthemer

import android.util.Log
import kotlin.math.roundToInt

fun getColorTints(accent: String): Map<String, String> {
    val ac_500 = getRgb(accent)

    val ac_200 = ac_500.map { getLighter(it, 0.8) }
    val ac_300 = ac_500.map { getLighter(it, 0.2) }

    val ac_700 = ac_500.map { getDarker(it, 0.2) }
    val ac_800 = ac_500.map { getDarker(it, 0.4) }

    return mapOf<String, String>(
        "ac_200" to getHex(ac_200),
        "ac_300" to getHex(ac_300),
        "ac_500" to getHex(ac_500),
        "ac_700" to getHex(ac_700),
        "ac_800" to getHex(ac_800),
    )
}

// make color lighter
fun getLighter(n: Int, factor: Double): Int {
    return (n + (factor * (255 - n))).roundToInt()
}

// make color darker
fun getDarker(n: Int, factor: Double): Int {
    return (n * (1 - factor)).roundToInt()
}

// hex to rgb
fun getRgb(accent: String): List<Int> {
    val r: Int = accent.substring(0, 2).toInt(16) // 16 for hex
    val g: Int = accent.substring(2, 4).toInt(16) // 16 for hex
    val b: Int = accent.substring(4, 6).toInt(16) // 16 for hex

    return listOf(r, g, b)
}

// rgb to hex
fun getHex(rgb: List<Int>): String {
    return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2])
}