package com.therxmv.telegramthemer.ui.editor.data

import android.graphics.Color
import android.os.Parcelable
import com.therxmv.telegramthemer.utils.DEFAULT_HEX_COLOR
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeState(
    val style: Styles = Styles.DEFAULT, // TODO implementation
    val accent: Int = Color.parseColor(DEFAULT_HEX_COLOR),
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isGradient: Boolean = false, // TODO implementation
) : Parcelable

enum class Styles(val label: String) {
    DEFAULT("Default"),
    SOZA("Soza"),
}