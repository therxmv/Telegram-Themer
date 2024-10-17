package com.therxmv.preview.common

import android.content.Context
import android.util.AttributeSet
import android.view.View

abstract class ColorfulView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    abstract fun setColor(color: Int)
}