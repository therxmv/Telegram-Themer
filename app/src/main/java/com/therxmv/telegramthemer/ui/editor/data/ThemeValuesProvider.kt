package com.therxmv.telegramthemer.ui.editor.data

import android.content.Context
import androidx.core.content.ContextCompat
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.ui.editor.data.utils.AdvancedThemeKeys
import com.therxmv.telegramthemer.ui.editor.data.utils.ThemeKeys
import com.therxmv.telegramthemer.ui.editor.data.utils.colorToHex
import com.therxmv.telegramthemer.ui.editor.data.utils.hexToRgb
import com.therxmv.telegramthemer.ui.editor.data.utils.rgbToHex
import javax.inject.Inject

class ThemeValuesProvider @Inject constructor(
    private val context: Context,
): ThemeValues {

    override fun getAdvancedColorSchema(state: ThemeState): Map<String, String> {
        val baseColors = getBaseColors(state)
        val get: String.() -> String = {
            baseColors.getValue(this) // to avoid nullable because key should exist
        }

        val grays = generateAllTints(ThemeKeys.gray.get())
        val accents = generateAllTints(ThemeKeys.accent.get())

        // Gray should be more tinted in monet
        val gray1 = accents[1].takeIf { state.isMonet } ?: grays[1]
        val gray8 = accents[8].takeIf { state.isMonet } ?: grays[8]
        val gray9 = accents[9].takeIf { state.isMonet } ?: grays[9]

        return mapOf(
            AdvancedThemeKeys.background to ThemeKeys.background.get(),
            AdvancedThemeKeys.onBackground to ThemeKeys.onBackground.get(),
            AdvancedThemeKeys.black to ThemeKeys.black.get(),
            AdvancedThemeKeys.white to ThemeKeys.white.get(),
            AdvancedThemeKeys.gray_1 to gray1, // TODO maybe add all 1..9 values
            AdvancedThemeKeys.gray_3 to grays[3],
            AdvancedThemeKeys.gray_5 to grays[5],
            AdvancedThemeKeys.gray_6 to grays[6],
            AdvancedThemeKeys.gray_8 to gray8,
            AdvancedThemeKeys.gray_9 to gray9,
            AdvancedThemeKeys.accent_2 to accents[2],
            AdvancedThemeKeys.accent_3 to accents[3],
            AdvancedThemeKeys.accent_4 to accents[4],
            AdvancedThemeKeys.accent_5 to accents[5],
            AdvancedThemeKeys.accent_7 to accents[7],
            AdvancedThemeKeys.accent_9 to accents[9],
            AdvancedThemeKeys.red_5 to ThemeKeys.red.get(),
            AdvancedThemeKeys.orange_5 to ThemeKeys.orange.get(),
            AdvancedThemeKeys.yellow_5 to ThemeKeys.yellow.get(),
            AdvancedThemeKeys.green_5 to ThemeKeys.green.get(),
            AdvancedThemeKeys.blue_5 to ThemeKeys.blue.get(),
            AdvancedThemeKeys.purple_5 to ThemeKeys.purple.get(),
            AdvancedThemeKeys.transparent_0 to ThemeKeys.transparent.get(),
            AdvancedThemeKeys.tr_accent_5 to "#77${accents[5].drop(1)}",
            AdvancedThemeKeys.tr_accent_7 to "#44${accents[7].drop(1)}",
            AdvancedThemeKeys.tr_gray_5 to "#77${grays[5].drop(1)}",
            AdvancedThemeKeys.tr_gray_8 to "#AA${grays[8].drop(1)}",
        )
    }

    private fun getBaseColors(state: ThemeState): Map<String, String> {
        val black = when {
            state.isMonet -> ContextCompat.getColor(context, R.color.theme_neutral1_900).colorToHex()
            state.isAmoled && state.isDark -> "#000000"
            else -> "#181818"
        }
        val white = when {
            state.isMonet -> ContextCompat.getColor(context, R.color.theme_neutral1_50).colorToHex()
            else -> "#FFFFFF"
        }
        val gray = when {
            state.isMonet -> ContextCompat.getColor(context, R.color.theme_neutral1_400).colorToHex()
            else -> "#919191"
        }

        val background = black.takeIf { state.isDark } ?: white
        val onBackground = white.takeIf { state.isDark } ?: black

        return mapOf(
            ThemeKeys.background to background,
            ThemeKeys.onBackground to onBackground,
            ThemeKeys.accent to state.accent.colorToHex(),
            ThemeKeys.black to black,
            ThemeKeys.white to white,
            ThemeKeys.gray to gray,
            ThemeKeys.yellow to "#E3B727",
            ThemeKeys.orange to "#DF9700",
            ThemeKeys.red to "#E23333",
            ThemeKeys.green to "#52CF2C",
            ThemeKeys.blue to "#299FE9",
            ThemeKeys.purple to "#776BF5",
            ThemeKeys.transparent to "#00000000",
        )
    }

    private fun generateAllTints(baseColor: String): List<String> =
        (0..10).map {
            when {
                it < 5 -> baseColor.getDarkerColor(1 - it * 0.2f)
                it > 5 -> baseColor.getLighterColor((it - 5) * 0.2f)
                else -> baseColor
            }
        }

    private fun String.getLighterColor(factor: Float): String {
        val rgbList = this.hexToRgb()
        val lighterList = rgbList.map { color ->
            (color + (factor * (255 - color))).toInt().coerceIn(0, 255)
        }

        return lighterList.rgbToHex()
    }

    private fun String.getDarkerColor(factor: Float): String {
        val rgbList = this.hexToRgb()
        val darkerList = rgbList.map { color ->
            (color * (1 - factor)).toInt().coerceIn(0, 255)
        }

        return darkerList.rgbToHex()
    }
}