package com.therxmv.telegramthemer.data.theme.file

import android.content.Context
import com.therxmv.telegramthemer.data.theme.ThemeValues
import com.therxmv.telegramthemer.data.theme.utils.colorToHex
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

// TODO javadoc
class ThemeToFileAdapter @Inject constructor(
    private val context: Context,
    private val themeValues: ThemeValues,
) : ThemeFileAdapter { // TODO download json templates from github

    companion object {
        private const val DARK_LABEL = "dark"
        private const val LIGHT_LABEL = "light"
        private const val MONET_LABEL = "monet"
        private const val EXTENSION = ".attheme"
    }

    override fun createThemeFile(themeState: ThemeState): File {
        val tints = themeValues.getTintedColorSchema(themeState)
        val atthemeMap = themeValues.getAtthemeMap(themeState)

        val file = File(context.filesDir, getFileName(themeState))
        file.printWriter().use { out ->
            atthemeMap.forEach { (key, value) ->
                // themeState.overwrittenColors[value] is required for "tt_background"
                val overwrittenColor = themeState.overwrittenColors[key] ?: themeState.overwrittenColors[value]
                val color = (overwrittenColor ?: tints[value]).colorToHex()

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