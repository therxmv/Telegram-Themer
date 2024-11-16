package com.therxmv.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.doOnLayout
import androidx.core.view.setPadding
import com.therxmv.preview.components.PreviewAppbar
import com.therxmv.preview.components.PreviewBackground
import com.therxmv.preview.components.chat.MessagePanel
import com.therxmv.preview.components.chat.PlayerPanel
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.utils.dpToPx

class ChatPreview(
    context: Context,
    attr: AttributeSet,
) : RelativeLayout(context, attr) {

    companion object {
        private val backgroundId = View.generateViewId()
        private val appbarId = View.generateViewId()
        private val messagePanelId = View.generateViewId()
        private val playerPanelId = View.generateViewId()

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
                addMessagePanel()
                addPlayerPanel()
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
        PreviewAppbar(dpValues = dpValues, isInChat = true, context = context).apply {
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

    private fun PreviewBackground.addMessagePanel() {
        MessagePanel(dpValues = dpValues, context = context).apply {
            id = messagePanelId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                addRule(ALIGN_PARENT_BOTTOM)
            }
            this@addMessagePanel.addView(this@apply)
        }
    }

    private fun PreviewBackground.addPlayerPanel() {
        PlayerPanel(dpValues = dpValues, context = context).apply {
            id = playerPanelId
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
            ).apply {
                topMargin = dpValues.dp20
                addRule(BELOW, appbarId)
            }
            this@addPlayerPanel.addView(this@apply)
        }
    }

    fun setColors(colors: PreviewColorsModel) {
        findViewById<PreviewBackground>(backgroundId)?.setColors(colors.background, colors.accent)
        findViewById<PreviewAppbar>(appbarId)?.setColors(colors.appbarColors)
        findViewById<MessagePanel>(messagePanelId)?.setColors(colors.chatColors.messagePanelColors)
        findViewById<PlayerPanel>(playerPanelId)?.setColors(colors.chatColors.playerPanelColors)
    }
}