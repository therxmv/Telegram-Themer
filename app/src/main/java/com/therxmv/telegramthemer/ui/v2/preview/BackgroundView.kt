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
    private val _edge: () -> Float = { // to make stroke inside
        _strokeWidth / 2
    }
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK // TODO set color
        style = Paint.Style.FILL
    }
    private val strokePaint: () -> Paint = {
        Paint().apply {
            isAntiAlias = true
            color = Color.GREEN // TODO set color
            style = Paint.Style.STROKE
            strokeWidth = _strokeWidth
        }
    }

    override fun scaleView(factor: Float) {
        _cornerRadius *= factor
        _strokeWidth *= factor
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            _edge(),
            _edge(),
            width - _edge(),
            height - _edge(),
            _cornerRadius,
            _cornerRadius,
            backgroundPaint,
        )
        canvas.drawRoundRect(
            _edge(),
            _edge(),
            width - _edge(),
            height - _edge(),
            _cornerRadius,
            _cornerRadius,
            strokePaint(),
        )
        super.onDraw(canvas)
    }
}