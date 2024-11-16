package com.therxmv.preview.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.HorizontalLineView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.model.PlayerPanelColors

class PlayerPanel(
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(
        dpValues = DpValues(context),
        context = context,
        attr = attr,
    )

    companion object {
        private val playId = View.generateViewId()
        private val nameId = View.generateViewId()
        private val speedId = View.generateViewId()
        private val closeId = View.generateViewId()
    }

    init {
        addPlayButton()
        addName()
        addSpeedButton()
        addCloseButton()
    }

    private fun addPlayButton() {
        RoundedRectangleView(context).apply {
            id = playId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp10,
                /* h = */ dpValues.dp10,
            ).apply {
                addRule(ALIGN_PARENT_START)
            }
            addView(this)
        }
    }

    private fun addName() {
        HorizontalLineView(context).apply {
            id = nameId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp80,
                /* h = */ dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(END_OF, playId)
            }
            addView(this)
        }
    }

    private fun addSpeedButton() {
        RoundedRectangleView(context).apply {
            id = speedId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp20,
                /* h = */ dpValues.dp10,
            ).apply {
                marginEnd = dpValues.dp10
                addRule(START_OF, closeId)
            }
            addView(this)
        }
    }

    private fun addCloseButton() {
        RoundedRectangleView(context).apply {
            id = closeId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp10,
                /* h = */ dpValues.dp10,
            ).apply {
                addRule(ALIGN_PARENT_END)
            }
            addView(this)
        }
    }

    fun setColors(colors: PlayerPanelColors) {
        findViewById<ColorfulView>(playId)?.setColor(colors.play)
        findViewById<ColorfulView>(nameId)?.setColor(colors.name)
        findViewById<ColorfulView>(speedId)?.setColor(colors.icons)
        findViewById<ColorfulView>(closeId)?.setColor(colors.icons)
    }
}