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

        // TODO change numbers to correct
        val gray5 = ThemeKeys.gray.get()
        val gray1 = gray5.getLighterColor(0.9f)
        val gray3 = gray5.getLighterColor(0.6f)
        val gray6 = gray5.getDarkerColor(0.2f)
        val gray8 = gray5.getDarkerColor(0.5f)
        val gray9 = gray5.getDarkerColor(0.7f)

        val accent5 = ThemeKeys.accent.get()
        val accent2 = accent5.getLighterColor(0.8f)
        val accent3 = accent5.getLighterColor(0.6f)
        val accent4 = accent5.getLighterColor(0.2f)
        val accent7 = accent5.getDarkerColor(0.3f)
        val accent9 = accent5.getDarkerColor(0.7f)

        return mapOf( // TODO add "tt_" before each to separate from attheme (or we will build file in runtime)
            AdvancedThemeKeys.background to ThemeKeys.background.get(),
            AdvancedThemeKeys.onBackground to ThemeKeys.onBackground.get(),
            AdvancedThemeKeys.black to ThemeKeys.black.get(),
            AdvancedThemeKeys.white to ThemeKeys.white.get(),
            AdvancedThemeKeys.gray_1 to gray1,
            AdvancedThemeKeys.gray_3 to gray3,
            AdvancedThemeKeys.gray_5 to gray5,
            AdvancedThemeKeys.gray_6 to gray6,
            AdvancedThemeKeys.gray_8 to gray8,
            AdvancedThemeKeys.gray_9 to gray9,
            AdvancedThemeKeys.accent_2 to accent2,
            AdvancedThemeKeys.accent_3 to accent3,
            AdvancedThemeKeys.accent_4 to accent4,
            AdvancedThemeKeys.accent_5 to accent5,
            AdvancedThemeKeys.accent_7 to accent7,
            AdvancedThemeKeys.accent_9 to accent9,
            AdvancedThemeKeys.red_5 to ThemeKeys.red.get(), // TODO 3 and 7 was dropped
            AdvancedThemeKeys.orange_5 to ThemeKeys.orange.get(), // TODO orange
            AdvancedThemeKeys.yellow_5 to ThemeKeys.yellow.get(),
            AdvancedThemeKeys.green_5 to ThemeKeys.green.get(), // TODO 3 and 7 was dropped
            AdvancedThemeKeys.blue_5 to ThemeKeys.blue.get(),
            AdvancedThemeKeys.purple_5 to ThemeKeys.purple.get(),
            AdvancedThemeKeys.transparent_0 to ThemeKeys.transparent.get(),
            AdvancedThemeKeys.tr_accent_3 to "#44${accent3.drop(1)}",
            AdvancedThemeKeys.tr_accent_5 to "#77${accent5.drop(1)}",
            AdvancedThemeKeys.tr_gray_5 to "#77${gray5.drop(1)}",
            AdvancedThemeKeys.tr_gray_8 to "#AA${gray8.drop(1)}",
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

        val background = black.takeIf { state.isDark } ?: white
        val onBackground = white.takeIf { state.isDark } ?: black

        return mapOf(
            ThemeKeys.background to background,
            ThemeKeys.onBackground to onBackground,
            ThemeKeys.accent to state.accent.colorToHex(),
            ThemeKeys.black to black,
            ThemeKeys.white to white,
            ThemeKeys.gray to "#919191", // maybe depends on isMonet
            ThemeKeys.yellow to "#E3B727",
            ThemeKeys.orange to "#DF9700",
            ThemeKeys.red to "#E23333",
            ThemeKeys.green to "#52CF2C",
            ThemeKeys.blue to "#299FE9",
            ThemeKeys.purple to "#776BF5",
            ThemeKeys.transparent to "#00000000",
        )
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