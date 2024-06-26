package com.therxmv.telegramthemer.domain.usecase.theme

import android.content.Context
import android.graphics.Color.parseColor
import android.widget.ImageView
import com.devs.vectorchildfinder.VectorChildFinder
import com.therxmv.telegramthemer.R

class CreatePreviewUseCase {

    operator fun invoke(context: Context, theme: String, themeBg: String, imageView: ImageView) {
        val vector = VectorChildFinder(context, R.drawable.ic_preview, imageView)

        // all names from ic_preview
        val previewKeys = listOf(
            "actionBarDefault_0",
            "actionBarDefault_1",
            "actionBarDefaultIcon_0",
            "actionBarDefaultIcon_1",
            "actionBarDefaultIcon_2",
            "actionBarDefaultIcon_3",
            "actionBarDefaultTitle_0",
            "actionBarDefaultTitle_1",
            "actionBarTabActiveText",
            "chats_tabUnreadActiveBackground",
            "actionBarTabLine",
            "actionBarTabUnactiveText_0",
            "actionBarTabUnactiveText_1",
            "actionBarTabUnactiveText_2",
            "chats_tabUnreadUnactiveBackground",
            "avatar_backgroundBlue_0",
            "avatar_backgroundBlue_1",
            "avatar_backgroundBlue_2",
            "avatar_backgroundBlue_3",
            "avatar_backgroundBlue_4",
            "avatar_backgroundBlue_5",
            "avatar_backgroundBlue_6",
            "avatar_backgroundBlue_7",
            "avatar_backgroundBlue_8",
            "chats_name_0",
            "chats_name_1",
            "chats_name_2",
            "chats_name_3",
            "chats_name_4",
            "chats_name_5",
            "chats_name_6",
            "chats_muteIcon_0",
            "chats_muteIcon_1",
            "chats_muteIcon_2",
            "chats_message_0",
            "chats_message_1",
            "chats_message_2",
            "chats_message_3",
            "chats_message_4",
            "chats_message_5",
            "chats_message_6",
            "chats_date_0",
            "chats_date_1",
            "chats_date_2",
            "chats_date_3",
            "chats_date_4",
            "chats_date_5",
            "chats_date_6",
            "chats_date_7",
            "chats_unreadCounterMuted_0",
            "chats_unreadCounterMuted_1",
            "chats_unreadCounterMuted_2",
            "chats_onlineCircle",
            "chats_unreadCounter_0",
            "chats_unreadCounter_1",
            "chats_sentReadCheck",
            "chats_secretName",
            "chats_secretIcon",
            "chats_actionMessage",
            "chats_actionBackground",
            "chats_actionIcon",
            "chat_messagePanelHint",
            "chat_messagePanelIcons_0",
            "chat_messagePanelIcons_1",
            "chat_messagePanelIcons_2",
            "chat_outBubble_0",
            "chat_outBubble_1",
            "chat_outBubble_2",
            "chat_outBubble_3",
            "chat_outBubble_4",
            "chat_serviceBackground",
            "chat_inBubble_0",
            "chat_inBubble_1",
            "chat_inBubble_2",
            "chat_inBubble_3",
            "chat_inBubble_4",
            "chat_inLoader_0",
            "chat_inLoader_1",
            "chat_inMediaIcon_0",
            "chat_inMediaIcon_1",
            "chat_inFileNameText_0",
            "chat_inFileNameText_1",
            "chat_inFileInfoText_0",
            "chat_inFileInfoText_1",
            "chat_outLoader",
            "chat_outMediaIcon",
            "chat_outAudioDurationText",
            "chat_outVoiceSeekbarFill_0",
            "chat_outVoiceSeekbarFill_1",
            "chat_outVoiceSeekbarFill_2",
            "chat_outVoiceSeekbarFill_3",
            "chat_outVoiceSeekbarFill_4",
            "chat_outVoiceSeekbarFill_5",
            "chat_outVoiceSeekbarFill_6",
            "chat_outVoiceSeekbarFill_7",
            "chat_outVoiceSeekbarFill_8",
            "chat_outVoiceSeekbarFill_9",
            "chat_outVoiceSeekbar_0",
            "chat_outVoiceSeekbar_1",
            "chat_outVoiceSeekbar_2",
            "chat_outVoiceSeekbar_3",
            "chat_outVoiceSeekbar_4",
            "chat_outVoiceSeekbar_5",
            "chat_outVoiceSeekbar_6",
            "chat_outVoiceSeekbar_7",
            "chat_outVoiceSeekbar_8",
            "chat_outReplyMessageText",
            "chat_outReplyNameText",
            "chat_outReplyLine",
            "actionBarDefaultSubtitle",
            "inappPlayerPlayPause",
            "inappPlayerTitle",
            "inappPlayerClose_0",
            "inappPlayerClose_1",
            "chats_nameMessage",
            "chat_inBubbleShadow",
        )

        val themeMap = getThemeMap(previewKeys, theme)
        themeMap["theme_bg"] = themeBg

        themeMap.forEach {
            val color = parseColor(it.value)
            val path = vector.findPathByName(it.key)

            if (path != null) {
                when (it.key.unify()) {
                    "chats_onlineCircle" -> path.strokeColor = parseColor(themeMap["actionBarDefault_0"])
                    "actionBarDefault" -> path.strokeColor = parseColor(themeMap["chats_secretName"])
                    "chat_inBubble" -> path.strokeColor = parseColor(themeMap["chat_inBubbleShadow"])
                }

                path.fillColor = color
            }
        }

        imageView.invalidate()
    }

    private fun getThemeMap(previewKeys: List<String>, theme: String): MutableMap<String, String> {
        val themeKeys = "^\\w+".toRegex(RegexOption.MULTILINE).findAll(theme).map { it.value }.toList()
        val themeValues = "#\\w+$".toRegex(RegexOption.MULTILINE).findAll(theme).map { it.value }.toList()

        return previewKeys.associateWith {
            themeValues[themeKeys.indexOf(it.unify())]
        }.toMutableMap()
    }

    private fun String.unify() = if (last().isDigit()) dropLast(2) else this
}