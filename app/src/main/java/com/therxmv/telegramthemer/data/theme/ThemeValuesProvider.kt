package com.therxmv.telegramthemer.data.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.data.theme.utils.AdvancedThemeKeys
import com.therxmv.telegramthemer.data.theme.utils.BaseThemeKeys
import com.therxmv.telegramthemer.data.theme.utils.colorToHex
import com.therxmv.telegramthemer.data.theme.utils.generateAllTints
import com.therxmv.telegramthemer.domain.model.Styles
import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.Reader
import javax.inject.Inject

class ThemeValuesProvider @Inject constructor(
    private val context: Context,
): ThemeValues {

    companion object {
        private const val DEFAULT_LIGHT = "default_light_template.json"
        private const val DEFAULT_DARK = "default_dark_template.json"
        private const val SOZA_LIGHT = "soza_light_template.json"
        private const val SOZA_DARK = "soza_dark_template.json"
        private const val GRADIENT_KEY = "chat_outBubbleGradient"
    }

    override fun getAdvancedColorSchema(state: ThemeState): Map<String, String> {
        val baseColors = getBaseColors(state)
        val get: String.() -> String = {
            baseColors.getValue(this) // to avoid nullable because key should exist
        }

        val grays = generateAllTints(BaseThemeKeys.gray.get())
        val accents = generateAllTints(BaseThemeKeys.accent.get())

        // Gray should be more tinted in monet
        val gray1 = accents[1].takeIf { state.isMonet } ?: grays[1]
        val gray8 = accents[8].takeIf { state.isMonet } ?: grays[8]
        val gray9 = accents[9].takeIf { state.isMonet } ?: grays[9]

        return mapOf(
            AdvancedThemeKeys.background to BaseThemeKeys.background.get(),
            AdvancedThemeKeys.onBackground to BaseThemeKeys.onBackground.get(),
            AdvancedThemeKeys.gray_1 to gray1, // TODO maybe add all 1..9 values
            AdvancedThemeKeys.gray_3 to grays[3],
            AdvancedThemeKeys.gray_5 to grays[5],
            AdvancedThemeKeys.gray_8 to gray8,
            AdvancedThemeKeys.gray_9 to gray9,
            AdvancedThemeKeys.accent_2 to accents[2],
            AdvancedThemeKeys.accent_3 to accents[3],
            AdvancedThemeKeys.accent_4 to accents[4],
            AdvancedThemeKeys.accent_5 to accents[5],
            AdvancedThemeKeys.accent_7 to accents[7],
            AdvancedThemeKeys.accent_9 to accents[9],
            AdvancedThemeKeys.red_5 to BaseThemeKeys.red.get(),
            AdvancedThemeKeys.orange_5 to BaseThemeKeys.orange.get(),
            AdvancedThemeKeys.yellow_5 to BaseThemeKeys.yellow.get(),
            AdvancedThemeKeys.green_5 to BaseThemeKeys.green.get(),
            AdvancedThemeKeys.blue_5 to BaseThemeKeys.blue.get(),
            AdvancedThemeKeys.purple_5 to BaseThemeKeys.purple.get(),
            AdvancedThemeKeys.transparent_0 to BaseThemeKeys.transparent.get(),
            AdvancedThemeKeys.tr_accent_5 to "#77${accents[5].drop(1)}",
            AdvancedThemeKeys.tr_accent_7 to "#44${accents[7].drop(1)}",
            AdvancedThemeKeys.tr_gray_5 to "#77${grays[5].drop(1)}",
            AdvancedThemeKeys.tr_gray_3 to "#AA${grays[3].drop(1)}",
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
            BaseThemeKeys.background to background,
            BaseThemeKeys.onBackground to onBackground,
            BaseThemeKeys.accent to state.accent.colorToHex(),
            BaseThemeKeys.gray to gray,
            BaseThemeKeys.yellow to "#E3B727",
            BaseThemeKeys.orange to "#DF9700",
            BaseThemeKeys.red to "#E23333",
            BaseThemeKeys.green to "#52CF2C",
            BaseThemeKeys.blue to "#299FE9",
            BaseThemeKeys.purple to "#776BF5",
            BaseThemeKeys.transparent to "#00000000",
        )
    }

    override fun getAtthemeMap(state: ThemeState): Map<String, String> {
        val jsonName = when (state.style) {
            Styles.DEFAULT -> DEFAULT_DARK.takeIf { state.isDark } ?: DEFAULT_LIGHT
            Styles.SOZA -> SOZA_DARK.takeIf { state.isDark } ?: SOZA_LIGHT
        }
        val reader = context.assets.open(jsonName).bufferedReader()
        val filteredGradient = reader.jsonToMap().filter {
            if (state.isGradient.not()) {
                it.key != GRADIENT_KEY
            } else true
        }

        return filteredGradient
    }

    private fun Reader.jsonToMap(): Map<String, String> {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(this, type)
    }
}