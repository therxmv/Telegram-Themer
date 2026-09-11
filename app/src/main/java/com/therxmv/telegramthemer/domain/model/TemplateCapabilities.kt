package com.therxmv.telegramthemer.domain.model

import kotlinx.serialization.Serializable

/**
 * What a given (platform, style) combination actually supports, since a
 * remotely-pushed style isn't guaranteed to cover every mode - e.g. a style
 * could ship only a dark template, or one whose light variant defines no
 * gradient key. [hasLight] and [hasDark] are independent: a style may lack
 * either one (not necessarily light). Drives which of the "Dark"/"Amoled"/
 * "Gradient" toggles [com.therxmv.telegramthemer.ui.editor.simple.SimpleThemeEditFragment]
 * shows, and clamps [ThemeState] back to a valid combination whenever style
 * or platform changes - see `GetTemplateCapabilitiesUseCase`.
 */
@Serializable
data class TemplateCapabilities(
    val hasLight: Boolean = true,
    val hasDark: Boolean = true,
    val hasGradient: Boolean = true,
)
