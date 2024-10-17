package com.therxmv.preview.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues

class PreviewBackground(
    scaleFactor: Float,
    context: Context,
    attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) { // TODO add click listener

    constructor(context: Context, attr: AttributeSet? = null) : this(
        scaleFactor = 1f,
        context = context,
        attrs = attr
    )

    private val dpValues = DpValues(context, scaleFactor)

    private var backgroundColor = Color.BLACK
    private var strokeColor = Color.WHITE

    private var _cornerRadius = dpValues.dp20.toFloat()
    private var _strokeWidth = dpValues.dp8.toFloat()
    private val _edge: () -> Float = { // to make stroke inside
        _strokeWidth / 2
    }
    private val backgroundPaint: () -> Paint = {
        Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }
    }
    private val strokePaint: () -> Paint = {
        Paint().apply {
            isAntiAlias = true
            color = strokeColor
            style = Paint.Style.STROKE
            strokeWidth = _strokeWidth
        }
    }

    init {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            _edge(),
            _edge(),
            width - _edge(),
            height - _edge(),
            _cornerRadius,
            _cornerRadius,
            backgroundPaint(),
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

    fun setColors(background: Int, accent: Int) {
        backgroundColor = background
        strokeColor = accent
        invalidate()
    }
}