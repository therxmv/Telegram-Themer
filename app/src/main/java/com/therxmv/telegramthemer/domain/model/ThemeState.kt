package com.therxmv.telegramthemer.domain.model

import android.os.Parcelable
import com.therxmv.preview.base.view.DEFAULT_ACCENT_COLOR
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeState(
    val style: Styles = Styles.DEFAULT,
    val accent: Int = DEFAULT_ACCENT_COLOR,
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isGradient: Boolean = false,
    val overwrittenColors: Map<String, Int> = emptyMap(),
) : Parcelable

enum class Styles(val label: String) {
    DEFAULT("Default"),
    SOZA("Soza"),
}