package com.therxmv.preview.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet

class RoundedRectangleView(
    context: Context,
    attrs: AttributeSet? = null,
) : ColorfulView(context, attrs) {
// TODO add click listener

    private var backgroundColor = Color.WHITE // TODO default color
    private val _cornerRadius: () -> Float = { // to have same radius with different size
        maxOf(width, height) * 0.2f
    }
    private val backgroundPaint: () -> Paint = {
        Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            _cornerRadius(),
            _cornerRadius(),
            backgroundPaint(),
        )
        super.onDraw(canvas)
    }

    override fun setColor(color: Int) {
        if (backgroundColor != color) {
            backgroundColor = color
            invalidate()
        }
    }
}