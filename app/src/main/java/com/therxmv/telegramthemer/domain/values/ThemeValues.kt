package com.therxmv.telegramthemer.domain.values

import com.therxmv.telegramthemer.domain.model.ThemeState

/**
 * A platform's template map: flat elementKey -> role name (or, rarely, a
 * literal value), resolved against [ThemeColors]'s tints at export/preview
 * time. One interface, one implementation per platform - see
 * `data/values/AndroidThemeValuesProvider.kt` and `IosThemeValuesProvider.kt`
 * - dispatched by `data/values/PlatformThemeValuesProvider.kt`, the same
 * shape as [com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter].
 */
interface ThemeValues {
    fun getTemplateMap(state: ThemeState): Map<String, String>
}
