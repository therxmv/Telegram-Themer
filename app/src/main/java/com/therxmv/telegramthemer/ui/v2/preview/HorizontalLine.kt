package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.therxmv.telegramthemer.utils.scaleMargins

class HorizontalLine(
    context: Context,
    attrs: AttributeSet,
) : View(context, attrs), ScalableView {

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.CYAN // TODO set color
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
        super.onDraw(canvas)
        canvas.drawRoundRect(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            100f,
            100f,
            backgroundPaint,
        )
    }
}