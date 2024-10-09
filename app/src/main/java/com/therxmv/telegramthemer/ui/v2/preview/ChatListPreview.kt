package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import com.therxmv.telegramthemer.R

class ChatListPreview(
    context: Context,
    attrs: AttributeSet,
) : RelativeLayout(context, attrs) {

    companion object {
        private val backgroundId = View.generateViewId()
        private val appbarId = View.generateViewId()
        private val tabsId = View.generateViewId()
        private val actionButtonId = View.generateViewId()

        private val chatItemIds = (0..6).map { View.generateViewId() } // TODO change impl
    }

    private val scaleFactor: Float
    private val dpValues: DpValues

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ChatListPreview)
        scaleFactor = attributes.getFloat(R.styleable.ChatListPreview_scale_factor, 1f)
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
        PreviewTabs(scaleFactor, context).apply {
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
        for (i in 0..6) {
            PreviewChatItem(scaleFactor, context).apply {
                id = chatItemIds[i]
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dpValues.dp40,
                ).apply {
                    val id = if (i == 0) tabsId else chatItemIds[i - 1]
                    addRule(BELOW, id)
                    topMargin = dpValues.dp20
                }
                this@addChatItems.addView(this@apply)
            }
        }
    }

    fun setThemeColors() { // TODO data class? with colors

    }
}