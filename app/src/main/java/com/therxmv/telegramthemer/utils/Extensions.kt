package com.therxmv.telegramthemer.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes

fun Boolean.toVisibility() = if(this) View.VISIBLE else View.GONE

fun checkVersionForMonet() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

fun Int.dpToPx(context: Context): Float {
    val density = context.resources.displayMetrics.density

    return this * density + 0.5f
}

fun Context.getAttrInPx(@AttrRes res: Int): Float { // TODO remove if no need
    val typedValue = TypedValue()
    theme.resolveAttribute(res, typedValue, true)
    return typedValue.getDimension(resources.displayMetrics)
}

fun ViewGroup.LayoutParams.scaleMargins(factor: Float) {
    if (this is ViewGroup.MarginLayoutParams) {
        val resolveMargin: (Int) -> Int = { margin ->
            (margin.takeIf { it == 0 } ?: (margin * factor)).toInt()
        }
        marginStart = resolveMargin(marginStart)
        marginEnd = resolveMargin(marginEnd)
        setMargins(
            resolveMargin(leftMargin),
            resolveMargin(topMargin),
            resolveMargin(rightMargin),
            resolveMargin(bottomMargin),
        )
    }
}