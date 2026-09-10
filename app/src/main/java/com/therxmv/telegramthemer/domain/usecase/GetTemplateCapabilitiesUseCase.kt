package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.model.TemplateCapabilities
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

class GetTemplateCapabilitiesUseCase @Inject constructor(
    private val templateLocalSource: TemplateLocalSource,
    private val themeValues: ThemeValues,
) {
    operator fun invoke(state: ThemeState): TemplateCapabilities {
        val hasLight = templateLocalSource.hasTemplate(
            TemplateFiles.fileName(state.platform, state.style, isDark = false)
        )
        val hasDark = templateLocalSource.hasTemplate(
            TemplateFiles.fileName(state.platform, state.style, isDark = true)
        )

        // Checked against the variant that will actually be used once isDark
        // is clamped to what's available - not just "false if unavailable"
        // (a dark-only style must resolve to dark, not silently to light).
        val effectiveIsDark = when {
            state.isDark && hasDark -> true
            !state.isDark && hasLight -> false
            else -> hasDark
        }
        val hasGradient = themeValues.hasGradientSupport(state.copy(isDark = effectiveIsDark))

        return TemplateCapabilities(hasLight = hasLight, hasDark = hasDark, hasGradient = hasGradient)
    }
}
