package com.therxmv.telegramthemer.domain.usecase.theme

import com.therxmv.telegramthemer.domain.model.ThemeModel
import com.therxmv.telegramthemer.utils.DefaultTheme.DEFAULT_FACTOR_200
import com.therxmv.telegramthemer.utils.DefaultTheme.DEFAULT_FACTOR_300
import com.therxmv.telegramthemer.utils.DefaultTheme.DEFAULT_FACTOR_700
import com.therxmv.telegramthemer.utils.DefaultTheme.DEFAULT_FACTOR_800
import com.therxmv.telegramthemer.utils.SozaTheme.SOZA_FACTOR_200
import com.therxmv.telegramthemer.utils.SozaTheme.SOZA_FACTOR_300
import com.therxmv.telegramthemer.utils.SozaTheme.SOZA_FACTOR_700
import com.therxmv.telegramthemer.utils.SozaTheme.SOZA_FACTOR_800
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_200
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_300
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_500
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_700
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_800
import kotlin.math.roundToInt

class GetColorTintsUseCase {

    operator fun invoke(themeModel: ThemeModel): Map<String, String> {
        val isDefault = themeModel.isDefault

        val ac500 = themeModel.color.toRgbList()

        val ac200 = ac500.getLighterColor(isDefault.resolveFactor(DEFAULT_FACTOR_200, SOZA_FACTOR_200))
        val ac300 = ac500.getLighterColor(isDefault.resolveFactor(DEFAULT_FACTOR_300, SOZA_FACTOR_300))

        val ac700 = ac500.getDarkerColor(isDefault.resolveFactor(DEFAULT_FACTOR_700, SOZA_FACTOR_700))
        val ac800 = ac500.getDarkerColor(isDefault.resolveFactor(DEFAULT_FACTOR_800, SOZA_FACTOR_800))

        return mapOf(
            ACCENT_200 to ac200.toHex(),
            ACCENT_300 to ac300.toHex(),
            ACCENT_500 to ac500.toHex(),
            ACCENT_700 to ac700.toHex(),
            ACCENT_800 to ac800.toHex(),
        )
    }

    private fun Boolean.resolveFactor(default: Double, second: Double) = if (this) default else second

    private fun List<Int>.getLighterColor(factor: Double) = this.map { (it + (factor * (255 - it))).roundToInt() }

    private fun List<Int>.getDarkerColor(factor: Double) = this.map { (it * (1 - factor)).roundToInt() }

    /**
     * Converts hex string ("FFFFFF") to rgb list
     */
    private fun String.toRgbList() = listOf(
        this.substring(0, 2).toInt(16),
        this.substring(2, 4).toInt(16),
        this.substring(4, 6).toInt(16),
    )

    /**
     * Converts rgb list to hex string ("FFFFFF")
     */
    private fun List<Int>.toHex() = String.format("#%02x%02x%02x", this[0], this[1], this[2])
}