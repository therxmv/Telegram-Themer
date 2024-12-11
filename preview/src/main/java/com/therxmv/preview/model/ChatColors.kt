package com.therxmv.preview.model

data class ChatColors(
    val messagePanelColors: MessagePanelColors,
    val playerPanelColors: PlayerPanelColors,
    val inMessageColors: MessageColors,
    val outMessageColors: MessageColors,
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

data class MessageColors(
    val background: Int,
    val text: Int,
    val date: Int,
    val replyColors: ReplyColors,
    val fileColors: FileColors,
    val voiceColors: VoiceColors,
) {
    data class ReplyColors(
        val line: Int,
        val sender: Int,
        val text: Int,
    )

    data class FileColors(
        val loader: Int,
        val name: Int,
        val info: Int,
    )

    data class VoiceColors(
        val loader: Int,
        val seekbar: Int,
        val seekbarFill: Int,
        val info: Int,
    )
}