package com.therxmv.preview.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.ColorfulView
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

    private val stickerIconId = View.generateViewId()
    private val messageId = View.generateViewId()
    private val attachIconId = View.generateViewId()
    private val voiceIconId = View.generateViewId()

    init {
        drawStickerIcon()
        drawMessage()
        drawAttachIcon()
        drawVoiceIcon()
    }

    private fun drawStickerIcon() {
        RoundedRectangleView.create(
            context = context,
            id = stickerIconId,
            width = dpValues.dp20,
            height = dpValues.dp20,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_START)
            }
        ).also { addView(it) }
    }

    private fun drawMessage() {
        RoundedRectangleView.create(
            context = context,
            id = messageId,
            width = dpValues.dp80,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(END_OF, stickerIconId)
                addRule(CENTER_VERTICAL)
            }
        ).also { addView(it) }
    }

    private fun drawAttachIcon() {
        RoundedRectangleView.create(
            context = context,
            id = attachIconId,
            width = dpValues.dp10,
            height = dpValues.dp20,
            setUpLayoutParams = {
                marginEnd = dpValues.dp10
                addRule(START_OF, voiceIconId)
            }
        ).also { addView(it) }
    }

    private fun drawVoiceIcon() {
        RoundedRectangleView.create(
            context = context,
            id = voiceIconId,
            width = dpValues.dp10,
            height = dpValues.dp20,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    fun setColors(colors: MessagePanelColors) {
        findViewById<ColorfulView>(stickerIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(attachIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(voiceIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(messageId)?.setColor(colors.message)
    }
}