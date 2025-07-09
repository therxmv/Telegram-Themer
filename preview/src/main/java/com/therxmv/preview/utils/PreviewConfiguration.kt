package com.therxmv.preview.utils

import com.therxmv.preview.components.chat.message.MessageModel.Date
import com.therxmv.preview.components.chat.message.MessageModel.Message
import com.therxmv.preview.components.chatslist.ChatModel

object PreviewConfiguration {
    val chatListItems = listOf(
        ChatModel(
            isActionMessage = true,
        ),
        ChatModel(
            isUnread = true,
            isMuted = true,
            isOnline = true,
        ),
        ChatModel(
            isUnread = true,
            shouldShowSender = true,
        ),
        ChatModel(
            isSecret = true,
        ),
        ChatModel(
            shouldShowSentCheck = true,
        ),
        ChatModel(
            isOnline = true,
            isUnread = true,
        ),
        ChatModel(
            isMuted = true,
        )
    )

    val chatMessages = listOf(
        Message.TextMessage(isIncome = false, isReply = true),
        Message.TextMessage(isIncome = true, isReply = false),
        Message.TextMessage(isIncome = true, isReply = true),

        Message.VoiceMessage(isIncome = false),
        Message.FileMessage(isIncome = true),

        Date,
        Message.TextMessage(isIncome = false, isReply = false),
        Message.TextMessage(isIncome = false, isReply = false),
    )
}