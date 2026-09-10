package com.therxmv.telegramthemer.data.adapter

import android.content.Context
import com.therxmv.telegramthemer.data.extensions.colorToHex
import com.therxmv.telegramthemer.data.values.AndroidThemeValuesProvider
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.values.ThemeColors
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

/**
 * Uses tints from [ThemeColors] and the template map from
 * [AndroidThemeValuesProvider] (injected concretely, not through the
 * [com.therxmv.telegramthemer.domain.values.ThemeValues] interface, so this
 * always resolves Android regardless of [ThemeState.platform]) to replace
 * keys("accent_5") in json file with given hexadecimal color.
 */
class AndroidThemeFileAdapter @Inject constructor(
    private val context: Context,
    private val themeColors: ThemeColors,
    private val androidThemeValues: AndroidThemeValuesProvider,
) : ThemeFileAdapter { // TODO download json templates from github

    companion object {
        private const val DARK_LABEL = "dark"
        private const val LIGHT_LABEL = "light"
        private const val MONET_LABEL = "monet"
        private const val EXTENSION = ".attheme"
    }

    override fun createThemeFile(themeState: ThemeState): File {
        val tints = themeColors.getTintedColorSchema(themeState)
        val atthemeMap = androidThemeValues.getTemplateMap(themeState)

        val file = File(context.filesDir, getFileName(themeState))
        file.printWriter().use { out ->
            atthemeMap.forEach { (key, value) ->
                // themeState.overwrittenColors[value] is required for "tt_background"
                val overwrittenColor = themeState.overwrittenColors[key] ?: themeState.overwrittenColors[value]
                // rawHex keeps the exact digit count (6 or 8) of the role's tint,
                // e.g. tr_accent_5 stays translucent instead of losing its alpha
                // byte. A value that isn't a known role (a literal like
                // "#BE000000", "-1" or "0") has no matching tint, so it's used
                // as-is instead of silently falling back to opaque black.
                val color = overwrittenColor?.colorToHex() ?: tints.rawHex(value) ?: value

                out.println("$key=$color")
            }
        }

        return file
    }

    private fun getFileName(state: ThemeState): String {
        val style = state.style.label
        val dark = DARK_LABEL.takeIf { state.isDark } ?: LIGHT_LABEL
        val color = MONET_LABEL.takeIf { state.isMonet }
            ?: state.accent.colorToHex().drop(1)
        val uniqueId = Random.nextInt(100, 999)

        return "$style-$dark-$color-$uniqueId$EXTENSION"
    }
}