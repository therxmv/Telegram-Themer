package com.therxmv.preview.components.chat.message

import android.view.View

sealed class MessageModel {
    val id: Int = View.generateViewId()

    sealed class Message(open val isIncome: Boolean) : MessageModel() {

        data class TextMessage(
            override val isIncome: Boolean,
            val isReply: Boolean,
        ) : Message(isIncome)

        data class VoiceMessage(
            override val isIncome: Boolean,
        ) : Message(isIncome)

        data class FileMessage(
            override val isIncome: Boolean,
        ) : Message(isIncome)
    }

    data object Date : MessageModel()
}