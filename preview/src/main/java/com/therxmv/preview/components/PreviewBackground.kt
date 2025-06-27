package com.therxmv.preview.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import com.therxmv.preview.DpValues
import com.therxmv.preview.utils.dpToPx

class PreviewBackground(
    context: Context,
    attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) {

    private var _dpValues: DpValues? = null
    val dpValues: DpValues get() = requireNotNull(_dpValues) // Provides scalable values for children

    private val _cornerRadius: Float get() = dpValues.dp20.toFloat()
    private val _strokeWidth: Float get() = dpValues.dp8.toFloat()
    private val _edge: Float get() = _strokeWidth / 2 // to make stroke inside

    var backgroundViewColor = Color.BLACK
    private var strokeColor = Color.WHITE

    private val backgroundPaint: Paint
        get() = Paint().apply {
            isAntiAlias = true
            color = backgroundViewColor
            style = Paint.Style.FILL
        }
    private val strokePaint: Paint
        get() = Paint().apply {
            isAntiAlias = true
            color = strokeColor
            style = Paint.Style.STROKE
            strokeWidth = _strokeWidth
        }

    init {
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (_dpValues == null) {
            val scaleFactor = width / 280.dpToPx(context)
            _dpValues = DpValues(context, scaleFactor)
            setPadding(dpValues.dp20)
        }

        super.onLayout(changed, l, t, r, b)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            /* left = */ _edge,
            /* top = */ _edge,
            /* right = */ width - _edge,
            /* bottom = */ height - _edge,
            /* rx = */ _cornerRadius,
            /* ry = */ _cornerRadius,
            /* paint = */ backgroundPaint,
        )
        canvas.drawRoundRect(
            /* left = */ _edge,
            /* top = */ _edge,
            /* right = */ width - _edge,
            /* bottom = */ height - _edge,
            /* rx = */ _cornerRadius,
            /* ry = */ _cornerRadius,
            /* paint = */ strokePaint,
        )
        super.onDraw(canvas)
    }

    fun setColors(background: Int, accent: Int) {
        backgroundViewColor = background
        strokeColor = accent
        invalidate()
    }
}