package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

data class ChatModel(
    val id: Int = View.generateViewId(),
    val isSecret: Boolean = false, // view and color
    val isActionMessage: Boolean = false, // color
    val isMuted: Boolean = false, // view and color
    val isOnline: Boolean = false, // view
    val isUnread: Boolean = false, // view
    val shouldShowSentCheck: Boolean = false, // view
    val shouldShowSender: Boolean = false, // view
)

class PreviewChatItem(
    private val data: ChatModel,
    scaleFactor: Float,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(
        context: Context,
        attr: AttributeSet? = null,
    ) : this(data = ChatModel(), scaleFactor = 1f, context = context, attr = attr)

    companion object {
        private val avatarId = View.generateViewId()
        private val onlineBgId = View.generateViewId()
        private val onlineId = View.generateViewId()
        private val nameId = View.generateViewId()
        private val messageId = View.generateViewId()
        private val timeId = View.generateViewId()
        private val counterId = View.generateViewId()
        private val muteId = View.generateViewId()
        private val secretId = View.generateViewId()
        private val senderId = View.generateViewId()
        private val sentCheckId = View.generateViewId()
    }

    private val dpValues = DpValues(context, scaleFactor)

    init {
        addAvatar()
        addName()
        addMessage()
        addTime()

        if (data.isOnline) {
            addOnline()
        }
        if (data.shouldShowSentCheck) {
            addSentCheck()
        }
        if (data.isUnread) {
            addCounter()
        }
        if (data.isMuted) {
            addMuteIcon()
        }
        if (data.shouldShowSender) {
            addSender()
        }
        if (data.isSecret) {
            addSecretIcon()
        }
    }

    private fun addSecretIcon() {
        CircleView(context).apply {
            id = secretId
            layoutParams = LayoutParams(
                dpValues.dp10,
                dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(ALIGN_PARENT_TOP)
                addRule(RIGHT_OF, avatarId)
            }
            addView(this)
        }
    }

    private fun addMuteIcon() {
        CircleView(context).apply {
            id = muteId
            layoutParams = LayoutParams(
                dpValues.dp10,
                dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp4
                addRule(RIGHT_OF, nameId)
            }
            addView(this)
        }
    }

    private fun addCounter() {
        CircleView(context).apply {
            id = counterId
            layoutParams = LayoutParams(
                dpValues.dp20,
                dpValues.dp20,
            ).apply {
                addRule(ALIGN_PARENT_BOTTOM)
                addRule(ALIGN_PARENT_END)
            }
            addView(this)
        }
    }

    private fun addSentCheck() {
        CircleView(context).apply {
            id = sentCheckId
            layoutParams = LayoutParams(
                dpValues.dp10,
                dpValues.dp10,
            ).apply {
                marginEnd = dpValues.dp4
                addRule(LEFT_OF, timeId)
            }
            addView(this)
        }
    }

    private fun addTime() {
        HorizontalLineView(context).apply {
            id = timeId
            layoutParams = LayoutParams(
                dpValues.dp30,
                dpValues.dp10,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_END)
            }
            addView(this)
        }
    }

    private fun addSender() {
        HorizontalLineView(context).apply {
            id = senderId
            layoutParams = LayoutParams(
                dpValues.dp40,
                dpValues.dp10,
            ).apply {
                bottomMargin = dpValues.dp4
                marginStart = dpValues.dp10
                addRule(ALIGN_PARENT_BOTTOM)
                addRule(RIGHT_OF, avatarId)
            }
            addView(this)
        }
    }

    private fun addMessage() {
        HorizontalLineView(context).apply {
            id = messageId
            layoutParams = LayoutParams(
                dpValues.dp100,
                dpValues.dp10,
            ).apply {
                if (data.shouldShowSender) {
                    marginStart = dpValues.dp4
                    addRule(RIGHT_OF, senderId)
                } else {
                    marginStart = dpValues.dp10
                    addRule(RIGHT_OF, avatarId)
                }
                bottomMargin = dpValues.dp4
                addRule(ALIGN_PARENT_BOTTOM)
            }
            addView(this)
        }
    }

    private fun addName() {
        HorizontalLineView(context).apply {
            id = nameId
            layoutParams = LayoutParams(
                dpValues.dp80,
                dpValues.dp10,
            ).apply {
                if (data.isSecret) {
                    marginStart = dpValues.dp4
                    addRule(RIGHT_OF, secretId)
                } else {
                    marginStart = dpValues.dp10
                    addRule(ALIGN_PARENT_TOP)
                    addRule(RIGHT_OF, avatarId)
                }
            }
            addView(this)
        }
    }

    private fun addOnline() {
        CircleView(context).apply {// TODO color of background like stroke
            id = onlineBgId
            layoutParams = LayoutParams(
                dpValues.dp14,
                dpValues.dp14,
            ).apply {
                addRule(ALIGN_RIGHT, avatarId)
                addRule(ALIGN_PARENT_BOTTOM, avatarId)
            }
            addView(this)
        }
        CircleView(context).apply {
            id = onlineId
            layoutParams = LayoutParams(
                dpValues.dp7,
                dpValues.dp7,
            ).apply {
                marginEnd = dpValues.dp7 / 2
                bottomMargin = dpValues.dp7 / 2
                addRule(ALIGN_RIGHT, avatarId)
                addRule(ALIGN_PARENT_BOTTOM, avatarId)
            }
            addView(this)
        }
    }

    private fun addAvatar() {
        CircleView(context).apply {
            id = avatarId
            layoutParams = LayoutParams(
                dpValues.dp40,
                dpValues.dp40,
            ).apply {
                addRule(ALIGN_PARENT_START)
            }
            addView(this)
        }
    }
}