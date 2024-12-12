package com.therxmv.preview.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout.LayoutParams

class RoundedRectangleView(
    context: Context,
    attrs: AttributeSet? = null,
) : ColorfulView(context, attrs) {
// TODO add click listener

    companion object {
        fun create(
            context: Context,
            id: Int,
            width: Int,
            height: Int,
            setUpLayoutParams: LayoutParams.() -> Unit = {},
        ) = RoundedRectangleView(context).apply {
            this.id = id
            layoutParams = LayoutParams(
                /* w = */ width,
                /* h = */ height,
            ).apply(setUpLayoutParams)
        }
    }

    private var backgroundColor = Color.WHITE // TODO default color
    private val _cornerRadius: Float get() = maxOf(width, height) * 0.2f // to have same radius with different size
    private val backgroundPaint: Paint
        get() = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            /* left = */ 0F,
            /* top = */ 0F,
            /* right = */ width.toFloat(),
            /* bottom = */ height.toFloat(),
            /* rx = */ _cornerRadius,
            /* ry = */ _cornerRadius,
            /* paint = */ backgroundPaint,
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