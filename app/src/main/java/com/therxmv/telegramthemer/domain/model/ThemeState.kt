package com.therxmv.telegramthemer.domain.model

import com.therxmv.preview.base.view.DEFAULT_ACCENT_COLOR
import kotlinx.serialization.Serializable

@Serializable
data class ThemeState(
    val style: Styles = Styles.DEFAULT,
    val accent: Int = DEFAULT_ACCENT_COLOR,
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isGradient: Boolean = false,
    val overwrittenColors: Map<String, Int> = emptyMap(),
)

enum class Styles(val label: String) {
    DEFAULT("Default"),
    SOZA("Soza"),
}