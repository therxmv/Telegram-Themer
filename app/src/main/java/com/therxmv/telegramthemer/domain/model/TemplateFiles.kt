package com.therxmv.telegramthemer.domain.model

/**
 * Filename convention for theme template assets/downloads - single source of
 * truth, used to read (`AndroidThemeValuesProvider`/`IosThemeValuesProvider`
 * via `TemplateLocalSource`), to sync (`TemplateRemoteDataSource`), and to
 * resolve availability (`GetTemplateCapabilitiesUseCase`/`GetAvailableStylesUseCase`).
 * Pure naming logic with no framework dependency, so it lives in domain
 * rather than data - both layers are allowed to depend on it.
 */
object TemplateFiles {

    const val TEMPLATES_INDEX = "templates_index.json"

    /**
     * e.g. `fileName(Platform.ANDROID, "soza", isDark = true)` -> "android_soza_dark.json".
     * [styleId] is lowercased so a style id restored from a pre-migration cached
     * [ThemeState] (persisted when `style` was still an uppercase enum name,
     * e.g. "DEFAULT") still resolves correctly.
     */
    fun fileName(platform: Platform, styleId: String, isDark: Boolean): String {
        val mode = if (isDark) "dark" else "light"
        return "${platform.name.lowercase()}_${styleId.lowercase()}_$mode.json"
    }
}
