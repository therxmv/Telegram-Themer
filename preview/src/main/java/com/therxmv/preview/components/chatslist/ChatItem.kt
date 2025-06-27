package com.therxmv.preview.components.chatslist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.preview.ClickablePreview
import com.therxmv.preview.common.preview.ColorfulPreview
import com.therxmv.preview.common.view.CircleView
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.common.view.RoundedRectangleView
import com.therxmv.preview.model.ChatsColors
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.avatar_backgroundBlue
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_actionMessage
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_date
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_message
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_muteIcon
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_name
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_nameMessage
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_onlineCircle
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_secretIcon
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_secretName
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_sentReadCheck
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_unreadCounter
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_unreadCounterMuted

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

class ChatItem(
    private val model: ChatModel,
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr),
    ColorfulPreview<ChatsColors>,
    ClickablePreview {

    constructor(
        context: Context,
        attr: AttributeSet? = null,
    ) : this(model = ChatModel(), dpValues = DpValues(context), context = context, attr = attr)

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

    init {
        drawAvatar()
        drawName()
        drawMessage()
        drawTime()

        if (model.isOnline) {
            drawOnline()
        }
        if (model.shouldShowSentCheck) {
            drawSentCheck()
        }
        if (model.isUnread) {
            drawCounter()
        }
        if (model.isMuted) {
            drawMuteIcon()
        }
        if (model.shouldShowSender) {
            drawSender()
        }
        if (model.isSecret) {
            drawSecretIcon()
        }
    }

    private fun drawSecretIcon() {
        CircleView.create(
            context = context,
            id = secretId,
            width = dpValues.dp10,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(ALIGN_PARENT_TOP)
                addRule(RIGHT_OF, avatarId)
            }
        ).also { addView(it) }
    }

    private fun drawMuteIcon() {
        CircleView.create(
            context = context,
            id = muteId,
            width = dpValues.dp10,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp4
                addRule(RIGHT_OF, nameId)
            }
        ).also { addView(it) }
    }

    private fun drawCounter() {
        CircleView.create(
            context = context,
            id = counterId,
            width = dpValues.dp20,
            height = dpValues.dp20,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_BOTTOM)
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    private fun drawSentCheck() {
        CircleView.create(
            context = context,
            id = sentCheckId,
            width = dpValues.dp10,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginEnd = dpValues.dp4
                addRule(LEFT_OF, timeId)
            }
        ).also { addView(it) }
    }

    private fun drawTime() {
        RoundedRectangleView.create(
            context = context,
            id = timeId,
            width = dpValues.dp30,
            height = dpValues.dp10,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    private fun drawSender() {
        RoundedRectangleView.create(
            context = context,
            id = senderId,
            width = dpValues.dp40,
            height = dpValues.dp10,
            setUpLayoutParams = {
                bottomMargin = dpValues.dp4
                marginStart = dpValues.dp10
                addRule(ALIGN_PARENT_BOTTOM)
                addRule(RIGHT_OF, avatarId)
            }
        ).also { addView(it) }
    }

    private fun drawMessage() {
        RoundedRectangleView.create(
            context = context,
            id = messageId,
            width = dpValues.dp100,
            height = dpValues.dp10,
            setUpLayoutParams = {
                if (model.shouldShowSender) {
                    marginStart = dpValues.dp4
                    addRule(RIGHT_OF, senderId)
                } else {
                    marginStart = dpValues.dp10
                    addRule(RIGHT_OF, avatarId)
                }
                bottomMargin = dpValues.dp4
                addRule(ALIGN_PARENT_BOTTOM)
            }
        ).also { addView(it) }
    }

    private fun drawName() {
        RoundedRectangleView.create(
            context = context,
            id = nameId,
            width = dpValues.dp80,
            height = dpValues.dp10,
            setUpLayoutParams = {
                if (model.isSecret) {
                    marginStart = dpValues.dp4
                    addRule(RIGHT_OF, secretId)
                } else {
                    marginStart = dpValues.dp10
                    addRule(ALIGN_PARENT_TOP)
                    addRule(RIGHT_OF, avatarId)
                }
            }
        ).also { addView(it) }
    }

    private fun drawOnline() {
        CircleView.create(
            context = context,
            id = onlineBgId,
            width = dpValues.dp14,
            height = dpValues.dp14,
            setUpLayoutParams = {
                addRule(ALIGN_RIGHT, avatarId)
                addRule(ALIGN_PARENT_BOTTOM, avatarId)
            }
        ).also { addView(it) }
        CircleView.create(
            context = context,
            id = onlineId,
            width = dpValues.dp7,
            height = dpValues.dp7,
            setUpLayoutParams = {
                marginEnd = dpValues.dp7 / 2
                bottomMargin = dpValues.dp7 / 2
                addRule(ALIGN_RIGHT, avatarId)
                addRule(ALIGN_PARENT_BOTTOM, avatarId)
            }
        ).also { addView(it) }
    }

    private fun drawAvatar() {
        CircleView.create(
            context = context,
            id = avatarId,
            width = dpValues.dp40,
            height = dpValues.dp40,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_START)
            }
        ).also { addView(it) }
    }

    override fun setColors(colors: ChatsColors) {
        findViewById<ColorfulView>(avatarId)?.setColor(colors.avatarColor)
        findViewById<ColorfulView>(nameId)?.setColor(if (model.isSecret) colors.secretName else colors.chatName)
        findViewById<ColorfulView>(messageId)?.setColor(if (model.isActionMessage) colors.actionMessage else colors.message)
        findViewById<ColorfulView>(timeId)?.setColor(colors.chatDate)

        findViewById<ColorfulView>(onlineBgId)?.setColor(colors.background)
        findViewById<ColorfulView>(onlineId)?.setColor(colors.online)
        findViewById<ColorfulView>(counterId)?.setColor(if (model.isMuted) colors.unreadCounterMuted else colors.unreadCounter)
        findViewById<ColorfulView>(muteId)?.setColor(colors.muteIcon)
        findViewById<ColorfulView>(secretId)?.setColor(colors.secretIcon)
        findViewById<ColorfulView>(senderId)?.setColor(colors.senderName)
        findViewById<ColorfulView>(sentCheckId)?.setColor(colors.sentCheck)
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<ColorfulView>(avatarId)?.openColorPicker(avatar_backgroundBlue)
        findViewById<ColorfulView>(nameId)?.openColorPicker(if (model.isSecret) chats_secretName else chats_name)
        findViewById<ColorfulView>(messageId)?.openColorPicker(if (model.isActionMessage) chats_actionMessage else chats_message)
        findViewById<ColorfulView>(timeId)?.openColorPicker(chats_date)

        findViewById<ColorfulView>(onlineId)?.openColorPicker(chats_onlineCircle)
        findViewById<ColorfulView>(counterId)?.openColorPicker(if (model.isMuted) chats_unreadCounterMuted else chats_unreadCounter)
        findViewById<ColorfulView>(muteId)?.openColorPicker(chats_muteIcon)
        findViewById<ColorfulView>(secretId)?.openColorPicker(chats_secretIcon)
        findViewById<ColorfulView>(senderId)?.openColorPicker(chats_nameMessage)
        findViewById<ColorfulView>(sentCheckId)?.openColorPicker(chats_sentReadCheck)
    }
}