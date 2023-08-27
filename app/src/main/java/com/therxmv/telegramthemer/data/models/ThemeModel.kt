package com.therxmv.telegramthemer.data.models

import android.os.Parcelable
import com.therxmv.telegramthemer.utils.DEFAULT_COLOR
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeModel(
    val color: String = DEFAULT_COLOR,
    val isDefault: Boolean = true,
    val isDark: Boolean = false,
    val isAmoled: Boolean = false,
    val isMonet: Boolean = false,
    val isMonetBackground: Boolean = false,
    val isGradient: Boolean = false,
): Parcelable