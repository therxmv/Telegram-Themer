package com.therxmv.telegramthemer.ui.v2.preview

object ChatListItems {
    val items = listOf(
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
}