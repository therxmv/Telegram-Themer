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
        private const val DEFAULT_LIGHT = "android_default_light.json"
        private const val DEFAULT_DARK = "android_default_dark.json"
        private const val SOZA_LIGHT = "android_soza_light.json"
        private const val SOZA_DARK = "android_soza_dark.json"

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
