package com.therxmv.telegramthemer.ui.editor.data.file

import android.content.Context
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import com.therxmv.telegramthemer.ui.editor.data.ThemeValues
import com.therxmv.telegramthemer.ui.editor.data.utils.colorToHex
import java.io.File
import java.util.UUID
import javax.inject.Inject

class ThemeToFileAdapter @Inject constructor(
    private val context: Context,
    private val themeValues: ThemeValues,
) : ThemeFileAdapter { // TODO download json templates from github

    companion object {
        private const val DARK_LABEL = "dark"
        private const val LIGHT_LABEL = "light"
        private const val EXTENSION = ".attheme"
    }

    override fun createThemeFile(themeState: ThemeState): File {
        val colors = themeValues.getAdvancedColorSchema(themeState)
        val atthemeMap = themeValues.getAtthemeMap(themeState)
        val file = File(context.filesDir, getFileName(themeState))

        file.printWriter().use { out ->
            atthemeMap.forEach { (key, value) ->
                val color = colors[value] ?: value // TODO override in future
                out.println("$key=$color")
            }
        }

        return file
    }

    private fun getFileName(state: ThemeState): String {
        val style = state.style.label
        val dark = DARK_LABEL.takeIf { state.isDark } ?: LIGHT_LABEL
        val color = state.accent.colorToHex().drop(1)

        return "$style-$dark-${UUID.randomUUID()}$EXTENSION" // TODO add monet instead of color
    }
}