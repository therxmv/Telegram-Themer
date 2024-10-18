package com.therxmv.preview.utils

import android.content.Context

fun Int.dpToPx(context: Context, scaleFactor: Float = 1f): Float {
    val density = context.resources.displayMetrics.density

    return this * density * scaleFactor
}