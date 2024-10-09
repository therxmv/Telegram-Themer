package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.YELLOW // TODO set color
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            width / 2f,
            backgroundPaint,
        )
        super.onDraw(canvas)
    }
}