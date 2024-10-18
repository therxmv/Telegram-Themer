package com.therxmv.preview.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.HorizontalLineView
import com.therxmv.preview.common.RoundedRectangleView

class PreviewAppbar(
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(DpValues(context), context, attr)

    companion object {
        private val leftIconId = View.generateViewId()
        private val rightIconId = View.generateViewId()
        private val titleId = View.generateViewId()
    }

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

    fun setColors(colors: AppbarColors) {
        findViewById<ColorfulView>(leftIconId).setColor(colors.appbarIcon)
        findViewById<ColorfulView>(rightIconId).setColor(colors.appbarIcon)
        findViewById<ColorfulView>(titleId).setColor(colors.appbarTitle)
    }
}