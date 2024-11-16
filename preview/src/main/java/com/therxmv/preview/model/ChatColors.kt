package com.therxmv.preview.model

data class ChatColors(
    val messagePanelColors: MessagePanelColors,
    val playerPanelColors: PlayerPanelColors,
)

data class MessagePanelColors(
    val message: Int,
    val icon: Int,
)

data class PlayerPanelColors(
    val play: Int,
    val name: Int,
    val icons: Int,
)