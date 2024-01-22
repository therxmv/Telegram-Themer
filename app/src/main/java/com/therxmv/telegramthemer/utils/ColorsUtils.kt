package com.therxmv.telegramthemer.utils

import com.therxmv.telegramthemer.data.models.ThemeModel
import kotlin.math.roundToInt

object ColorsUtils {
    fun getColorTints(themeProps: ThemeModel): Map<String, String> {
        val ac500 = themeProps.color.toRgbList()
        var ac200 = ac500.getLighterColor(0.8)
        var ac300 = ac500.getLighterColor(0.2)
        var ac700 = ac500.getDarkerColor(0.2)
        var ac800 = ac500.getDarkerColor(0.4)

        if (themeProps.isDefault.not()) {
            // soza theme
            ac200 = ac500.getLighterColor(0.8)
            ac300 = ac500.getLighterColor(0.6)
            ac700 = ac500.getDarkerColor(0.3)
            ac800 = ac500.getDarkerColor(0.7)
        }

        return mapOf(
            "ac_200" to ac200.toHex(),
            "ac_300" to ac300.toHex(),
            "ac_500" to ac500.toHex(),
            "ac_700" to ac700.toHex(),
            "ac_800" to ac800.toHex(),
        )
    }

    private fun List<Int>.getLighterColor(factor: Double) = this.map { (it + (factor * (255 - it))).roundToInt() }

    private fun List<Int>.getDarkerColor(factor: Double) = this.map { (it * (1 - factor)).roundToInt() }

    // hex(example: FFFFFF) to rgb list
    private fun String.toRgbList() = listOf(
        this.substring(0, 2).toInt(16),
        this.substring(2, 4).toInt(16),
        this.substring(4, 6).toInt(16),
    )

    // rgb list to hex(#FFFFFF)
    private fun List<Int>.toHex() = String.format("#%02x%02x%02x", this[0], this[1], this[2])
}