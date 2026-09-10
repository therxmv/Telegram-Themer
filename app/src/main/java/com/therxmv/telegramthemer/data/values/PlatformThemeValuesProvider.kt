package com.therxmv.telegramthemer.data.values

import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Routes template map lookup to the provider matching [ThemeState.platform] —
 * [AndroidThemeValuesProvider] or [IosThemeValuesProvider]. This is the
 * [ThemeValues] bound in DI; the two platform providers are plain, unbound
 * implementations only reached through this dispatcher - same shape as
 * [com.therxmv.telegramthemer.data.adapter.PlatformThemeFileAdapter].
 */
class PlatformThemeValuesProvider @Inject constructor(
    private val androidThemeValuesProvider: AndroidThemeValuesProvider,
    private val iosThemeValuesProvider: IosThemeValuesProvider,
) : ThemeValues {

    override fun getTemplateMap(state: ThemeState): Map<String, String> =
        when (state.platform) {
            Platform.ANDROID -> androidThemeValuesProvider.getTemplateMap(state)
            Platform.IOS -> iosThemeValuesProvider.getTemplateMap(state)
        }

    override fun hasGradientSupport(state: ThemeState): Boolean =
        when (state.platform) {
            Platform.ANDROID -> androidThemeValuesProvider.hasGradientSupport(state)
            Platform.IOS -> iosThemeValuesProvider.hasGradientSupport(state)
        }
}
