package com.therxmv.preview.model

data class ChatListColors(
    val actionButton: Int,
    val tabsColors: TabsColors,
    val chatsColors: ChatsColors,
)

data class TabsColors(
    val tab: Int,
    val selectedTab: Int,
    val tabSelector: Int,
    val selectedTabUnread: Int,
    val tabUnread: Int,
)

data class ChatsColors(
    val background: Int,
    val chatDate: Int,
    val unreadCounter: Int,
    val unreadCounterMuted: Int,
    val avatarColor: Int,
    val chatName: Int,
    val senderName: Int,
    val message: Int,
    val actionMessage: Int,
    val muteIcon: Int,
    val online: Int,
    val secretIcon: Int,
    val secretName: Int,
    val sentCheck: Int,
)