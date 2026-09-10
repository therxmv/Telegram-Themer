package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import javax.inject.Inject

class GetAvailableStylesUseCase @Inject constructor(
    private val templateLocalSource: TemplateLocalSource,
) {
    /**
     * Styles listed in the manifest that actually have at least one variant
     * (light or dark - either could be the one missing) for [platform]. A
     * remotely-pushed style isn't guaranteed to cover every platform, and
     * offering one with neither variant would silently fall back to the
     * "default" style's content under the wrong label (see
     * [TemplateLocalSource.getTemplateMap]'s fallback).
     */
    operator fun invoke(platform: Platform): List<TemplateStyle> =
        templateLocalSource.getTemplatesIndex().styles.filter { style ->
            templateLocalSource.hasTemplate(TemplateFiles.fileName(platform, style.id, isDark = false)) ||
                templateLocalSource.hasTemplate(TemplateFiles.fileName(platform, style.id, isDark = true))
        }
}
