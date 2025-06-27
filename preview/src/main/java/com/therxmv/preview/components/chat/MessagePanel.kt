package com.therxmv.preview.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.preview.ClickablePreview
import com.therxmv.preview.common.preview.ColorfulPreview
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.common.view.RoundedRectangleView
import com.therxmv.preview.model.MessagePanelColors
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messagePanelIcons
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messagePanelText

class MessagePanel(
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr),
    ColorfulPreview<MessagePanelColors>,
    ClickablePreview {

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

    override fun setColors(colors: MessagePanelColors) {
        findViewById<ColorfulView>(stickerIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(attachIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(voiceIconId)?.setColor(colors.icon)
        findViewById<ColorfulView>(messageId)?.setColor(colors.message)
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<ColorfulView>(stickerIconId)?.openColorPicker(chat_messagePanelIcons)
        findViewById<ColorfulView>(attachIconId)?.openColorPicker(chat_messagePanelIcons)
        findViewById<ColorfulView>(voiceIconId)?.openColorPicker(chat_messagePanelIcons)
        findViewById<ColorfulView>(messageId)?.openColorPicker(chat_messagePanelText)
    }
}