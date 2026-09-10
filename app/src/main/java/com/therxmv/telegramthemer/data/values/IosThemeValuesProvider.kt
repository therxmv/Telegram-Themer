package com.therxmv.telegramthemer.data.values

import android.content.Context
import com.therxmv.telegramthemer.data.extensions.jsonToMap
import com.therxmv.telegramthemer.domain.model.Styles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Loads the iOS `.tgios-theme` template map from assets. Keys are dot-paths
 * (e.g. "root.tabBar.background") that [com.therxmv.telegramthemer.data.adapter.IosThemeFileAdapter]
 * re-nests at export time - see the `ios-theme-key` project skill.
 */
class IosThemeValuesProvider @Inject constructor(
    private val context: Context,
) : ThemeValues { // TODO download json templates from github

    companion object {
        private const val DEFAULT_LIGHT = "ios_default_light.json"
        private const val DEFAULT_DARK = "ios_default_dark.json"
        private const val SOZA_LIGHT = "ios_soza_light.json"
        private const val SOZA_DARK = "ios_soza_dark.json"

        // Android's toggle filters one key (chat_outBubbleGradient, the outgoing bubble's
        // gradient); the iOS templates split that into a withWp/withoutWp pair.
        private val GRADIENT_KEYS = setOf(
            "chat.message.outgoing.bubble.withWp.gradientBg",
            "chat.message.outgoing.bubble.withoutWp.gradientBg",
        )
    }

    override fun getTemplateMap(state: ThemeState): Map<String, String> {
        val jsonName = when (state.style) {
            Styles.DEFAULT -> DEFAULT_DARK.takeIf { state.isDark } ?: DEFAULT_LIGHT
            Styles.SOZA -> SOZA_DARK.takeIf { state.isDark } ?: SOZA_LIGHT
        }
        val reader = context.assets.open(jsonName).bufferedReader()

        return reader.jsonToMap().filter {
            if (state.isGradient.not()) {
                it.key !in GRADIENT_KEYS
            } else true
        }
    }
}
