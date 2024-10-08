package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.therxmv.telegramthemer.utils.dpToPx

class BackgroundView(
    context: Context,
    attrs: AttributeSet,
) : View(context, attrs), ScalableView {
// TODO add click listener

    private var _cornerRadius = 20.dpToPx(context)
    private var _strokeWidth = 8.dpToPx(context)
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK // TODO set color
        style = Paint.Style.FILL
    }
    private val strokePaint: (Float) -> Paint = { stroke ->
        Paint().apply {
            isAntiAlias = true
            color = Color.GREEN // TODO set color
            style = Paint.Style.STROKE
            strokeWidth = stroke
        }
    }

    override fun scaleView(factor: Float) {
        _cornerRadius *= factor
        _strokeWidth *= factor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val edge = _strokeWidth / 2 // to make stroke inside

        canvas.drawRoundRect(
            edge,
            edge,
            width - edge,
            height - edge,
            _cornerRadius,
            _cornerRadius,
            backgroundPaint,
        )
        canvas.drawRoundRect(
            edge,
            edge,
            width - edge,
            height - edge,
            _cornerRadius,
            _cornerRadius,
            strokePaint(_strokeWidth),
        )
    }
}