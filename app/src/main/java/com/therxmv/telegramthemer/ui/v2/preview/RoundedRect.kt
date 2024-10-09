package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.therxmv.telegramthemer.utils.scaleMargins

class RoundedRect(
    context: Context,
    attrs: AttributeSet,
) : View(context, attrs), ScalableView {
// TODO add click listener

    private val _cornerRadius: () -> Float = { // to have same radius with different size
        maxOf(width, height) * 0.2f
    }
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN // TODO set color
        style = Paint.Style.FILL
    }

    override fun scaleView(factor: Float) {
        layoutParams.apply {
            width = (width * factor).toInt()
            height = (height * factor).toInt()
            scaleMargins(factor)
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
            backgroundPaint,
        )
        super.onDraw(canvas)
    }
}