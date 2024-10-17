package com.therxmv.preview.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet

class HorizontalLineView(
    context: Context,
    attrs: AttributeSet? = null,
) : ColorfulView(context, attrs) {

    private var backgroundColor = Color.WHITE // TODO default color
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
            100f,
            100f,
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