package com.therxmv.telegramthemer.domain.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val DEFAULT_HEX_COLOR = "#299FE9"

@Parcelize
data class ThemeState(
    val style: Styles = Styles.DEFAULT,
    val accent: Int = Color.parseColor(DEFAULT_HEX_COLOR),
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isGradient: Boolean = false,
) : Parcelable

enum class Styles(val label: String) {
    DEFAULT("Default"),
    SOZA("Soza"),
}