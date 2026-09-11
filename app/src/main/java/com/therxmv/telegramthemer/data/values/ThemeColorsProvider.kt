package com.therxmv.telegramthemer.data.values

import android.content.Context
import androidx.core.content.ContextCompat
import com.therxmv.preview.utils.AtthemePreviewKeys.tt_background
import com.therxmv.preview.utils.AtthemePreviewKeys.tt_onBackground
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.data.extensions.colorToHex
import com.therxmv.telegramthemer.data.extensions.generateAllTints
import com.therxmv.telegramthemer.domain.model.BaseThemeColors
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.model.TintedColor
import com.therxmv.telegramthemer.domain.model.TintedThemeColors
import com.therxmv.telegramthemer.domain.model.accent
import com.therxmv.telegramthemer.domain.model.blue
import com.therxmv.telegramthemer.domain.model.gray
import com.therxmv.telegramthemer.domain.model.green
import com.therxmv.telegramthemer.domain.model.orange
import com.therxmv.telegramthemer.domain.model.purple
import com.therxmv.telegramthemer.domain.model.red
import com.therxmv.telegramthemer.domain.model.trAccent
import com.therxmv.telegramthemer.domain.model.trBackground
import com.therxmv.telegramthemer.domain.model.trGray
import com.therxmv.telegramthemer.domain.model.transparent
import com.therxmv.telegramthemer.domain.model.yellow
import com.therxmv.telegramthemer.domain.values.ThemeColors
import javax.inject.Inject

/**
 * Provides tinted colors that will be used in
 * [com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter] and [com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter],
 * shared as-is across platforms - only the template maps that get resolved
 * against these tints ([com.therxmv.telegramthemer.domain.values.ThemeValues],
 * implemented once per platform) differ.
 */
class ThemeColorsProvider @Inject constructor(
    private val context: Context,
) : ThemeColors {

    companion object {
        private val TINTS_RANGE = 1..9

        private const val AMOLED_BLACK = "#000000"
        private const val DARK_BLACK = "#181818"
        private const val WHITE = "#FFFFFF"
        private const val GRAY = "#919191"
    }

    /**
     * Uses [BaseThemeColors] to generate tints with luminosity from 1 to 9 (the higher number - the lighter color).
     */
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

        val colors = buildList {
            TintedColor(name = tt_background.name, value = baseColors.background).also(::add)
            TintedColor(name = tt_onBackground.name, value = baseColors.onBackground).also(::add)

            TINTS_RANGE.map { TintedColor(name = gray(it), value = grays[it]) }.also(::addAll)
            TINTS_RANGE.map { TintedColor(name = accent(it), value = accents[it]) }.also(::addAll)

            TintedColor(name = red(5), value = baseColors.red).also(::add)
            TintedColor(name = orange(5), value = baseColors.orange).also(::add)
            TintedColor(name = yellow(5), value = baseColors.yellow).also(::add)
            TintedColor(name = green(5), value = baseColors.green).also(::add)
            TintedColor(name = blue(5), value = baseColors.blue).also(::add)
            TintedColor(name = purple(5), value = baseColors.purple).also(::add)

            TintedColor(name = transparent(0), value = baseColors.transparent).also(::add)
            TintedColor(name = trAccent(5), value = "#77${accents[5].drop(1)}").also(::add)
            TintedColor(name = trAccent(7), value = "#44${accents[7].drop(1)}").also(::add)
            TintedColor(name = trGray(5), value = "#77${grays[5].drop(1)}").also(::add)
            TintedColor(name = trGray(3), value = "#AA${grays[3].drop(1)}").also(::add)
            TintedColor(name = trBackground(9), value = "#E5${baseColors.background.drop(1)}").also(::add)
        }

        return TintedThemeColors(colors)
    }

    /**
     * Defines [BaseThemeColors] object with colors based on [state].
     * e.g. decides between amoled black and regular dark black; only the
     * neutral gray base (not the background/onBackground surfaces) is
     * wallpaper-derived under Monet.
     */
    private fun getBaseColors(state: ThemeState): BaseThemeColors {
        // tt_background/tt_onBackground stay a fixed near-black/white regardless
        // of Monet: windowBackgroundWhite (the chat list, most screens) and its
        // recessed "card" siblings (windowBackgroundGray etc.) share this one id,
        // and a wallpaper-extracted neutral tone can land far lighter than a
        // real dark background - only the accent-tinted gray-ramp extremes below
        // (grays[1]/[8]/[9]) carry the wallpaper hue into recessed surfaces.
        val black = when {
            state.isAmoled && state.isDark -> AMOLED_BLACK
            else -> DARK_BLACK
        }
        val white = WHITE
        val gray = when {
            state.isMonet -> ContextCompat.getColor(context, R.color.theme_neutral1_400).colorToHex()
            else -> GRAY
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
}
