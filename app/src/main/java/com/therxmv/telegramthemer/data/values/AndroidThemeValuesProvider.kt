package com.therxmv.telegramthemer.data.values

import com.therxmv.telegramthemer.data.values.AndroidThemeValuesProvider.Companion.GRADIENT_KEY
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Loads the Android `.attheme` template map - cached download if one exists
 * ([TemplateLocalSource]), else the bundled asset.
 */
class AndroidThemeValuesProvider @Inject constructor(
    private val templateLocalSource: TemplateLocalSource,
) : ThemeValues {

    companion object {
        private const val GRADIENT_KEY = "chat_outBubbleGradient"
    }

    /**
     * Reads correct theme file based on [state] and returns it as Map<String, String>.
     */
    override fun getTemplateMap(state: ThemeState): Map<String, String> {
        val jsonName = TemplateFiles.fileName(Platform.ANDROID, state.style, state.isDark)

        return templateLocalSource.getTemplateMap(jsonName).filter {
            if (state.isGradient.not()) {
                it.key != GRADIENT_KEY
            } else true
        }
    }

    /**
     * Whether the resolved template actually defines [GRADIENT_KEY] - checked
     * against the template with gradient forced on, since [getTemplateMap]
     * would otherwise filter the key out before we could see it.
     */
    override fun hasGradientSupport(state: ThemeState): Boolean =
        GRADIENT_KEY in getTemplateMap(state.copy(isGradient = true))
}
