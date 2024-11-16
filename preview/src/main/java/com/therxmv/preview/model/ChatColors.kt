package com.therxmv.preview.model

data class ChatColors(
    val messagePanelColors: MessagePanelColors
)

data class MessagePanelColors(
    val message: Int,
    val icon: Int,
)