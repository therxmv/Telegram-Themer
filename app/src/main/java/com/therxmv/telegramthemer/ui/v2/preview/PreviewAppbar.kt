package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class PreviewAppbar(
    scaleFactor: Float,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(1f, context, attr)

    companion object {
        private val leftIconId = View.generateViewId()
        private val rightIconId = View.generateViewId()
        private val titleId = View.generateViewId()
    }

    private val dpValues = DpValues(context, scaleFactor)

    init {
        addLeftIcon()
        addTitle()
        addRightIcon()
    }

    private fun addLeftIcon() {
        RoundedRectangleView(context).apply {
            id = leftIconId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp30, // TODO scale size at this point
                /* h = */ dpValues.dp30,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_START)
            }
            addView(this)
        }
    }

    private fun addRightIcon() {
        RoundedRectangleView(context).apply {
            id = rightIconId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp14,
                /* h = */ dpValues.dp30,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_END)
            }
            addView(this)
        }
    }

    private fun addTitle() {
        HorizontalLineView(context).apply {
            id = titleId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp80,
                /* h = */ dpValues.dp20,
            ).apply {
                marginStart = dpValues.dp20
                addRule(END_OF, leftIconId)
                addRule(CENTER_VERTICAL)
            }
            addView(this)
        }
    }
}