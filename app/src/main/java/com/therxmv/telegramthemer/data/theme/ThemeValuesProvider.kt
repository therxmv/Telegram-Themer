package com.therxmv.telegramthemer.data.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.data.theme.utils.BaseThemeColors
import com.therxmv.telegramthemer.data.theme.utils.TintedColor
import com.therxmv.telegramthemer.data.theme.utils.TintedThemeColors
import com.therxmv.telegramthemer.data.theme.utils.colorToHex
import com.therxmv.telegramthemer.data.theme.utils.generateAllTints
import com.therxmv.telegramthemer.domain.model.Styles
import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.Reader
import javax.inject.Inject

// TODO javadoc
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

    override fun getTintedColorSchema(state: ThemeState): TintedThemeColors {
        val baseColors = getBaseColors(state)

        val grays = generateAllTints(baseColors.gray).toMutableList()
        val accents = generateAllTints(baseColors.accent)

        // Gray should be more tinted in monet
        if (state.isMonet) {
            grays[1] = accents[1]
            grays[8] = accents[8]
            grays[9] = accents[9]
        }

        // TODO make some fancy const for names
        val colors = buildList {
            TintedColor(name = "tt_background", value = baseColors.background).also(::add)
            TintedColor(name = "tt_onBackground", value = baseColors.onBackground).also(::add)

            (1..9).map {
                TintedColor(name = "gray_$it", value = grays[it])
            }.also { addAll(it) }
            (1..9).map {
                TintedColor(name = "accent_$it", value = accents[it])
            }.also { addAll(it) }

            TintedColor(name = "red_5", value = baseColors.red).also(::add)
            TintedColor(name = "orange_5", value = baseColors.orange).also(::add)
            TintedColor(name = "yellow_5", value = baseColors.yellow).also(::add)
            TintedColor(name = "green_5", value = baseColors.green).also(::add)
            TintedColor(name = "blue_5", value = baseColors.blue).also(::add)
            TintedColor(name = "purple_5", value = baseColors.purple).also(::add)

            TintedColor(name = "transparent_0", value = baseColors.transparent).also(::add)
            TintedColor(name = "tr_accent_5", value = "#77${accents[5].drop(1)}").also(::add)
            TintedColor(name = "tr_accent_7", value = "#44${accents[7].drop(1)}").also (::add)
            TintedColor(name = "tr_gray_5", value = "#77${grays[5].drop(1)}").also (::add)
            TintedColor(name = "tr_gray_3", value = "#AA${grays[3].drop(1)}").also (::add)
        }

        return TintedThemeColors(colors)
    }

    private fun getBaseColors(state: ThemeState): BaseThemeColors {
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

        return BaseThemeColors(
            background = background,
            onBackground = onBackground,
            accent = state.accent.colorToHex(),
            gray = gray,
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