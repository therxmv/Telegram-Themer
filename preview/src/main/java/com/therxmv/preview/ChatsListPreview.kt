package com.therxmv.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import com.therxmv.preview.ChatListItems.items
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.components.PreviewAppbar
import com.therxmv.preview.components.PreviewBackground
import com.therxmv.preview.components.PreviewChatItem
import com.therxmv.preview.components.PreviewTabs

class ChatsListPreview(
    context: Context,
    attrs: AttributeSet,
) : RelativeLayout(context, attrs) {

    companion object {
        private val backgroundId = View.generateViewId()
        private val appbarId = View.generateViewId()
        private val tabsId = View.generateViewId()
        private val actionButtonId = View.generateViewId()
    }

    private val scaleFactor: Float
    private val dpValues: DpValues

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ChatsListPreview)
        scaleFactor = attributes.getFloat(R.styleable.ChatsListPreview_scale_factor, 1f)
        dpValues = DpValues(context, scaleFactor)

        val background = attachBackground()
        with(background) {
            addAppbar()
            addTabs()
            addChatItems()
            addActionButton()
        }

        attributes.recycle()
    }

    private fun attachBackground() =
        PreviewBackground(scaleFactor, context).apply {
            id = backgroundId
            attachViewToParent(
                /* child = */ this@apply,
                /* index = */ 0,
                /* params = */ LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                ).apply {
                    setPadding(dpValues.dp20)
                }
            )
        }

    private fun PreviewBackground.addAppbar() {
        PreviewAppbar(scaleFactor, context).apply {
            id = com.therxmv.preview.ChatsListPreview.appbarId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
            }
            this@addAppbar.addView(this@apply)
        }
    }

    private fun PreviewBackground.addTabs() {
        PreviewTabs(scaleFactor, context).apply {
            id = com.therxmv.preview.ChatsListPreview.tabsId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                topMargin = dpValues.dp20
                addRule(BELOW, com.therxmv.preview.ChatsListPreview.appbarId)
            }
            this@addTabs.addView(this@apply)
        }
    }

    private fun PreviewBackground.addActionButton() {
        RoundedRectangleView(context).apply {
            id = com.therxmv.preview.ChatsListPreview.actionButtonId
            layoutParams = LayoutParams(
                dpValues.dp50,
                dpValues.dp50,
            ).apply {
                addRule(ALIGN_PARENT_END)
                addRule(ALIGN_PARENT_BOTTOM)
            }
            this@addActionButton.addView(this@apply)
        }
    }

    private fun PreviewBackground.addChatItems() {
        items.forEachIndexed { index, model ->
            PreviewChatItem(model, scaleFactor, context).apply {
                id = model.id
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dpValues.dp40,
                ).apply {
                    val id = if (index == 0) tabsId else items[index - 1].id
                    addRule(BELOW, id)
                    topMargin = dpValues.dp20
                }
                this@addChatItems.addView(this@apply)
            }
        }
    }

    fun setColors(colors: PreviewColorsModel) {
        findViewById<PreviewBackground>(backgroundId).setColors(colors.background, colors.accent)
        findViewById<ColorfulView>(actionButtonId).setColor(colors.actionButton)
        findViewById<PreviewAppbar>(appbarId).setColors(colors.appbarColors)
        findViewById<PreviewTabs>(tabsId).setColors(colors.tabsColors)

        ChatListItems.items.forEach {
            findViewById<PreviewChatItem>(it.id).setColors(it, colors.chatsColors)
        }
    }
}