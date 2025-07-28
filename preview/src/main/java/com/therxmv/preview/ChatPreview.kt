package com.therxmv.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.doOnPreDraw
import com.therxmv.preview.base.preview.ClickablePreview
import com.therxmv.preview.base.preview.ColorfulPreview
import com.therxmv.preview.components.chat.MessagePanel
import com.therxmv.preview.components.chat.PlayerPanel
import com.therxmv.preview.components.chat.message.MessageItem
import com.therxmv.preview.components.chat.message.MessageModel
import com.therxmv.preview.components.common.PreviewAppbar
import com.therxmv.preview.components.common.PreviewBackground
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.tt_background
import com.therxmv.preview.utils.PreviewConfiguration.chatMessages

class ChatPreview(
    context: Context,
    attr: AttributeSet,
) : RelativeLayout(context, attr),
    ColorfulPreview<PreviewColorsModel>,
    ClickablePreview {

    private val backgroundId = generateViewId()
    private val appbarId = generateViewId()
    private val messagePanelId = generateViewId()
    private val playerPanelId = generateViewId()

    init {
        val background = attachBackground()
        doOnPreDraw {
            with(background) {
                drawAppbar()
                drawMessagePanel()
                drawPlayerPanel()
                drawMessages()
            }
        }
    }

    private fun attachBackground() =
        PreviewBackground(context).apply {
            id = backgroundId
            attachViewToParent(
                /* child = */ this@apply,
                /* index = */ 0,
                /* params = */ LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                )
            )
        }

    private fun PreviewBackground.drawAppbar() {
        PreviewAppbar(dpValues = dpValues, isInChat = true, context = context).apply {
            id = appbarId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
            }
            this@drawAppbar.addView(this@apply)
        }
    }

    private fun PreviewBackground.drawMessagePanel() {
        MessagePanel(dpValues = dpValues, context = context).apply {
            id = messagePanelId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                addRule(ALIGN_PARENT_BOTTOM)
            }
            this@drawMessagePanel.addView(this@apply)
        }
    }

    private fun PreviewBackground.drawPlayerPanel() {
        PlayerPanel(dpValues = dpValues, context = context).apply {
            id = playerPanelId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                topMargin = dpValues.dp20
                addRule(BELOW, appbarId)
            }
            this@drawPlayerPanel.addView(this@apply)
        }
    }

    private fun PreviewBackground.drawMessages() {
        chatMessages.forEachIndexed { index, model ->
            MessageItem(model, dpValues, context).apply {
                id = model.id
                layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                ).apply {
                    val id = if (index == 0) playerPanelId else chatMessages[index - 1].id
                    addRule(BELOW, id)

                    when (model) {
                        MessageModel.Date -> CENTER_HORIZONTAL
                        is MessageModel.Message -> {
                            if (model.isIncome) {
                                ALIGN_PARENT_START
                            } else {
                                ALIGN_PARENT_END
                            }
                        }
                    }.also { addRule(it) }

                    topMargin = dpValues.dp10
                }
                this@drawMessages.addView(this@apply)
            }
        }
    }

    override fun setColors(colors: PreviewColorsModel) {
        findViewById<PreviewBackground>(backgroundId)?.setColors(colors.background, colors.accent)
        findViewById<PreviewAppbar>(appbarId)?.setColors(colors.appbarColors)
        findViewById<MessagePanel>(messagePanelId)?.setColors(colors.chatColors.messagePanelColors)
        findViewById<PlayerPanel>(playerPanelId)?.setColors(colors.chatColors.playerPanelColors)

        chatMessages.forEach {
            val messageColors = if (it is MessageModel.Message && it.isIncome) {
                colors.chatColors.inMessageColors
            } else {
                colors.chatColors.outMessageColors
            }
            findViewById<MessageItem>(it.id)?.setColors(messageColors)
        }
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<PreviewBackground>(backgroundId)?.openColorPicker(tt_background)
        findViewById<PreviewAppbar>(appbarId)?.setColorPickerAction(openColorPicker)
        findViewById<MessagePanel>(messagePanelId)?.setColorPickerAction(openColorPicker)
        findViewById<PlayerPanel>(playerPanelId)?.setColorPickerAction(openColorPicker)

        chatMessages.forEach {
            findViewById<MessageItem>(it.id)?.setColorPickerAction(openColorPicker)
        }
    }
}