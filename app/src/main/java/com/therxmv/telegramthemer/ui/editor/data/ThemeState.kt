package com.therxmv.telegramthemer.ui.editor.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeState(
    val style: Styles, // TODO implementation
    val accent: Int,
    val isDark: Boolean,
    val isAmoled: Boolean,
    val isMonet: Boolean,
    val isGradient: Boolean, // TODO implementation
) : Parcelable

enum class Styles(val label: String) {
    DEFAULT("Default"),
    SOZA("Soza"),
}