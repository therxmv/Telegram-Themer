package com.therxmv.telegramthemer.utils

import android.content.Context
import android.view.View

fun Boolean.toVisibility() = if(this) View.VISIBLE else View.GONE

fun checkVersionForMonet() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

fun Int.dpToPx(context: Context): Float {
    val density = context.resources.displayMetrics.density

    return this * density + 0.5f
}

fun Int.dpToPx(context: Context, scaleFactor: Float): Float = this.dpToPx(context) * scaleFactor