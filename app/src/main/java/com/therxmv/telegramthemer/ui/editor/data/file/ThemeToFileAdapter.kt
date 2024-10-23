package com.therxmv.telegramthemer.ui.editor.data.file

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.therxmv.telegramthemer.ui.editor.data.Styles
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import com.therxmv.telegramthemer.ui.editor.data.ThemeValues
import com.therxmv.telegramthemer.ui.editor.data.utils.colorToHex
import java.io.File
import java.io.Reader
import javax.inject.Inject

class ThemeToFileAdapter @Inject constructor(
    private val context: Context,
    private val themeValues: ThemeValues,
) : ThemeFileAdapter {

    companion object {
        private const val DEFAULT_LIGHT = "default_light_template.json"
        private const val DEFAULT_DARK = "default_dark_template.json"
        private const val SOZA_LIGHT = "soza_light_template.json"
        private const val SOZA_DARK = "soza_dark_template.json"

        private const val DARK_LABEL = "dark"
        private const val LIGHT_LABEL = "light"
        private const val EXTENSION = ".attheme"
    }

    override fun createThemeFile(themeState: ThemeState): File {
        val colors = themeValues.getAdvancedColorSchema(themeState)
        val themeMap = getJsonReader(themeState).jsonToMap()
        val file = File(context.filesDir, getFileName(themeState))

        file.printWriter().use { out ->
            themeMap.forEach { (key, value) ->
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

        return "$style-$dark-$color$EXTENSION"
    }

    private fun Reader.jsonToMap(): Map<String, String> {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(this, type)
    }

    private fun getJsonReader(state: ThemeState): Reader {
        val jsonName = when (state.style) {
            Styles.DEFAULT -> DEFAULT_DARK.takeIf { state.isDark } ?: DEFAULT_LIGHT
            Styles.SOZA -> SOZA_DARK.takeIf { state.isDark } ?: SOZA_LIGHT
        }

        return context.assets.open(jsonName).bufferedReader()
    }
}