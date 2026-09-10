package com.therxmv.telegramthemer.domain.model

import kotlinx.serialization.Serializable

/**
 * Id of the style that's always bundled as a fallback asset for every
 * platform/mode, used whenever [ThemeState.style] doesn't resolve to a known
 * or cached template (see `TemplateLocalSource`).
 */
const val DEFAULT_STYLE_ID = "default"

/**
 * Manifest of the styles available for template lookup - fetched remotely
 * (`templates_index.json`, alongside the per-style template JSON files) with
 * a bundled asset fallback, since `raw.githubusercontent.com` can't list a
 * directory. See `TemplateSource`/`TemplateRemoteSource`.
 */
@Serializable
data class TemplatesIndex(
    val styles: List<TemplateStyle> = emptyList(),
)

@Serializable
data class TemplateStyle(
    val id: String,
    val label: String,
)
