package com.therxmv.telegramthemer.domain.values

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.model.TintedThemeColors

/**
 * The accent-derived color palette (tints) - shared across platforms, unlike
 * the platform-specific template map ([ThemeValues], implemented once per
 * platform) that gets resolved against it.
 */
interface ThemeColors {
    fun getTintedColorSchema(state: ThemeState): TintedThemeColors
}
