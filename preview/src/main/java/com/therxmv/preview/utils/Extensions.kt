package com.therxmv.preview.utils

import android.content.Context

fun Int.dpToPx(context: Context): Float {
    val density = context.resources.displayMetrics.density

    return this * density + 0.5f
}

fun Int.dpToPx(context: Context, scaleFactor: Float): Float = this.dpToPx(context) * scaleFactor