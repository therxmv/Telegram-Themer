package com.therxmv.telegramthemer.data.values

import com.therxmv.telegramthemer.data.values.IosThemeValuesProvider.Companion.GRADIENT_KEYS
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Loads the iOS `.tgios-theme` template map - cached download if one exists
 * ([TemplateLocalSource]), else the bundled asset. Keys are dot-paths (e.g.
 * "root.tabBar.background") that [com.therxmv.telegramthemer.data.adapter.IosThemeFileAdapter]
 * re-nests at export time - see the `ios-theme-key` project skill.
 */
class IosThemeValuesProvider @Inject constructor(
    private val templateLocalSource: TemplateLocalSource,
) : ThemeValues {

    companion object {
        // Android's toggle filters one key (chat_outBubbleGradient, the outgoing bubble's
        // gradient); the iOS templates split that into a withWp/withoutWp pair.
        private val GRADIENT_KEYS = setOf(
            "chat.message.outgoing.bubble.withWp.gradientBg",
            "chat.message.outgoing.bubble.withoutWp.gradientBg",
        )
    }

    override fun getTemplateMap(state: ThemeState): Map<String, String> {
        val jsonName = TemplateFiles.fileName(Platform.IOS, state.style, state.isDark)

        return templateLocalSource.getTemplateMap(jsonName).filter {
            if (state.isGradient.not()) {
                it.key !in GRADIENT_KEYS
            } else true
        }
    }

    /**
     * Whether the resolved template defines at least one of [GRADIENT_KEYS] -
     * checked against the template with gradient forced on, since
     * [getTemplateMap] would otherwise filter them out before we could see them.
     */
    override fun hasGradientSupport(state: ThemeState): Boolean =
        getTemplateMap(state.copy(isGradient = true)).keys.any { it in GRADIENT_KEYS }
}
