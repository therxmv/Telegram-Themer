package com.therxmv.preview.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout.LayoutParams

class CircleView(
    context: Context,
    attrs: AttributeSet? = null,
) : ColorfulView(context, attrs) {

    companion object {
        fun create(
            context: Context,
            id: Int,
            width: Int,
            height: Int,
            setUpLayoutParams: LayoutParams.() -> Unit = {},
        ) = CircleView(context).apply {
            this.id = id
            layoutParams = LayoutParams(
                /* w = */ width,
                /* h = */ height,
            ).apply(setUpLayoutParams)
        }
    }

    private var backgroundColor = Color.WHITE // TODO default color
    private val backgroundPaint: Paint
        get() = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            /* cx = */ width / 2f,
            /* cy = */ height / 2f,
            /* radius = */ width / 2f,
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