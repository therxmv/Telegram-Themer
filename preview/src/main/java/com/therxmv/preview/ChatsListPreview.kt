package com.therxmv.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.doOnLayout
import com.therxmv.preview.PreviewConfiguration.chatListItems
import com.therxmv.preview.common.preview.ClickablePreview
import com.therxmv.preview.common.preview.ColorfulPreview
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.common.view.RoundedRectangleView
import com.therxmv.preview.components.PreviewAppbar
import com.therxmv.preview.components.PreviewBackground
import com.therxmv.preview.components.chatslist.ChatItem
import com.therxmv.preview.components.chatslist.Tabs
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_actionBackground
import com.therxmv.preview.utils.AtthemePreviewKeys.tt_background

class ChatsListPreview(
    context: Context,
    attrs: AttributeSet,
) : RelativeLayout(context, attrs),
    ColorfulPreview<PreviewColorsModel>,
    ClickablePreview {

    private val backgroundId = View.generateViewId()
    private val appbarId = View.generateViewId()
    private val tabsId = View.generateViewId()
    private val actionButtonId = View.generateViewId()

    init {
        val background = attachBackground()
        doOnLayout {
            with(background) {
                drawAppbar()
                drawTabs()
                drawChatItems()
                drawActionButton()
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
        PreviewAppbar(dpValues = dpValues, isInChat = false, context = context).apply {
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

    private fun PreviewBackground.drawTabs() {
        Tabs(dpValues, context).apply {
            id = tabsId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                topMargin = dpValues.dp20
                addRule(BELOW, appbarId)
            }
            this@drawTabs.addView(this@apply)
        }
    }

    private fun PreviewBackground.drawActionButton() {
        RoundedRectangleView.create(
            context = context,
            id = actionButtonId,
            width = dpValues.dp50,
            height = dpValues.dp50,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_END)
                addRule(ALIGN_PARENT_BOTTOM)
            }
        ).also { addView(it) }
    }

    private fun PreviewBackground.drawChatItems() {
        chatListItems.forEachIndexed { index, model ->
            ChatItem(model, dpValues, context).apply {
                id = model.id
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dpValues.dp40,
                ).apply {
                    val id = if (index == 0) tabsId else chatListItems[index - 1].id
                    addRule(BELOW, id)
                    topMargin = dpValues.dp20
                }
                this@drawChatItems.addView(this@apply)
            }
        }
    }

    override fun setColors(colors: PreviewColorsModel) {
        findViewById<PreviewBackground>(backgroundId)?.setColors(colors.background, colors.accent)
        findViewById<ColorfulView>(actionButtonId)?.setColor(colors.chatListColors.actionButton)
        findViewById<PreviewAppbar>(appbarId)?.setColors(colors.appbarColors)
        findViewById<Tabs>(tabsId)?.setColors(colors.chatListColors.tabsColors)

        chatListItems.forEach {
            findViewById<ChatItem>(it.id)?.setColors(colors.chatListColors.chatsColors)
        }
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<PreviewBackground>(backgroundId)?.openColorPicker(tt_background)
        findViewById<ColorfulView>(actionButtonId)?.openColorPicker(chats_actionBackground)

        findViewById<PreviewAppbar>(appbarId)?.setColorPickerAction(openColorPicker)
        findViewById<Tabs>(tabsId)?.setColorPickerAction(openColorPicker)

        chatListItems.forEach {
            findViewById<ChatItem>(it.id)?.setColorPickerAction(openColorPicker)
        }
    }
}