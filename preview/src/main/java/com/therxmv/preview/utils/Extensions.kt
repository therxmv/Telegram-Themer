package com.therxmv.preview.utils

import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.components.PreviewBackground
import com.therxmv.preview.components.chat.message.MessageItem

fun Int.dpToPx(context: Context, scaleFactor: Float = 1f): Float {
    val density = context.resources.displayMetrics.density

    return this * density * scaleFactor
}

fun View.getColor(): Int =
    when(this) {
        is ColorfulView -> this.backgroundViewColor
        is PreviewBackground -> this.backgroundViewColor
        is MessageItem -> this.backgroundBubbleColor
        else -> Color.BLACK
    }

fun View.setOnSwipeListener(
    onTap: (View) -> Unit = {},
    onSwipeLeft: (View) -> Unit = {},
    onSwipeRight: (View) -> Unit = {},
) {
    var initialX = 0f

    setOnTouchListener { view, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.rawX
            }

            MotionEvent.ACTION_UP -> {
                when {
                    initialX == event.rawX -> onTap(this@setOnSwipeListener)
                    initialX > event.rawX -> onSwipeLeft(this@setOnSwipeListener)
                    initialX < event.rawX -> onSwipeRight(this@setOnSwipeListener)
                }
            }
        }

        view.performClick()
        true
    }
}