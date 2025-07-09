package com.therxmv.preview.base.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

val DEFAULT_ACCENT_COLOR = Color.parseColor("#299FE9")

abstract class ColorfulView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    var backgroundViewColor: Int = DEFAULT_ACCENT_COLOR
    abstract fun setColor(color: Int)
}