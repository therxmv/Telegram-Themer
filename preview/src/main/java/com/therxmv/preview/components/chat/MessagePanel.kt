package com.therxmv.preview.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.HorizontalLineView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.model.MessagePanelColors

class MessagePanel(
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
        private val stickerId = View.generateViewId()
        private val messageId = View.generateViewId()
        private val attachId = View.generateViewId()
        private val voiceId = View.generateViewId()
    }

    init {
        addStickerIcon()
        addMessage()
        addAttachIcon()
        addVoiceIcon()
    }

    private fun addStickerIcon() {
        RoundedRectangleView(context).apply {
            id = stickerId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp20,
                /* h = */ dpValues.dp20,
            ).apply {
                addRule(ALIGN_PARENT_START)
            }
            addView(this)
        }
    }

    private fun addMessage() {
        HorizontalLineView(context).apply {
            id = messageId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp80,
                /* h = */ dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(END_OF, stickerId)
                addRule(CENTER_VERTICAL)
            }
            addView(this)
        }
    }

    private fun addAttachIcon() {
        RoundedRectangleView(context).apply {
            id = attachId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp10,
                /* h = */ dpValues.dp20,
            ).apply {
                marginEnd = dpValues.dp10
                addRule(START_OF, voiceId)
            }
            addView(this)
        }
    }

    private fun addVoiceIcon() {
        RoundedRectangleView(context).apply {
            id = voiceId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp10,
                /* h = */ dpValues.dp20,
            ).apply {
                addRule(ALIGN_PARENT_END)
            }
            addView(this)
        }
    }

    fun setColors(colors: MessagePanelColors) {
        findViewById<ColorfulView>(stickerId)?.setColor(colors.icon)
        findViewById<ColorfulView>(attachId)?.setColor(colors.icon)
        findViewById<ColorfulView>(voiceId)?.setColor(colors.icon)
        findViewById<ColorfulView>(messageId)?.setColor(colors.message)
    }
}