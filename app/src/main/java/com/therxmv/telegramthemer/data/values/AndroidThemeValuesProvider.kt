package com.therxmv.telegramthemer.data.values

import android.content.Context
import com.therxmv.telegramthemer.data.extensions.jsonToMap
import com.therxmv.telegramthemer.domain.model.Styles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Loads the Android `.attheme` template map from assets.
 */
class AndroidThemeValuesProvider @Inject constructor(
    private val context: Context,
) : ThemeValues { // TODO download json templates from github

    companion object {
        private const val DEFAULT_LIGHT = "default_light_template.json"
        private const val DEFAULT_DARK = "default_dark_template.json"
        private const val SOZA_LIGHT = "soza_light_template.json"
        private const val SOZA_DARK = "soza_dark_template.json"

        private const val GRADIENT_KEY = "chat_outBubbleGradient"
    }

    /**
     * Reads correct theme file based on [state] and returns it as Map<String, String>.
     */
    override fun getTemplateMap(state: ThemeState): Map<String, String> {
        val jsonName = when (state.style) {
            Styles.DEFAULT -> DEFAULT_DARK.takeIf { state.isDark } ?: DEFAULT_LIGHT
            Styles.SOZA -> SOZA_DARK.takeIf { state.isDark } ?: SOZA_LIGHT
        }
        val reader = context.assets.open(jsonName).bufferedReader()

        return reader.jsonToMap().filter {
            if (state.isGradient.not()) {
                it.key != GRADIENT_KEY
            } else true
        }
    }
}
