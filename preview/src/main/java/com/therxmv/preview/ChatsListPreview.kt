package com.therxmv.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.doOnLayout
import androidx.core.view.setPadding
import com.therxmv.preview.ChatListItems.items
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.components.PreviewAppbar
import com.therxmv.preview.components.PreviewBackground
import com.therxmv.preview.components.PreviewChatItem
import com.therxmv.preview.components.PreviewTabs
import com.therxmv.preview.utils.dpToPx

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

    private lateinit var dpValues: DpValues // Should be initialized before any view is drawn

    init {
        val background = attachBackground()
        doOnLayout {
            val scaleFactor = width / 280.dpToPx(context)
            dpValues = DpValues(context, scaleFactor)

            background.setDpValues(dpValues)
            background.setPadding(dpValues.dp20)

            with(background) {
                addAppbar()
                addTabs()
                addChatItems()
                addActionButton()
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

    private fun PreviewBackground.addAppbar() {
        PreviewAppbar(dpValues = dpValues, isInChat = false, context = context).apply {
            id = appbarId
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
        PreviewTabs(dpValues, context).apply {
            id = tabsId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                topMargin = dpValues.dp20
                addRule(BELOW, appbarId)
            }
            this@addTabs.addView(this@apply)
        }
    }

    private fun PreviewBackground.addActionButton() {
        RoundedRectangleView(context).apply {
            id = actionButtonId
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
            PreviewChatItem(model, dpValues, context).apply {
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
        findViewById<PreviewBackground>(backgroundId)?.setColors(colors.background, colors.accent)
        findViewById<ColorfulView>(actionButtonId)?.setColor(colors.actionButton)
        findViewById<PreviewAppbar>(appbarId)?.setColors(colors.appbarColors)
        findViewById<PreviewTabs>(tabsId)?.setColors(colors.tabsColors)

        items.forEach {
            findViewById<PreviewChatItem>(it.id)?.setColors(it, colors.chatsColors)
        }
    }
}