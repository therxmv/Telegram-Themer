package com.therxmv.telegramthemer.utils

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.devs.vectorchildfinder.VectorChildFinder
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.data.models.ThemeModel

class ThemeUtils {
    companion object {
        fun createTheme(context: Context, templateFile: String, themeProps: ThemeModel, imageView: ImageView): String {
            val tints: Map<String, String> = ColorsUtils.getColorTints(themeProps)
            val hexesNames: Map<String, String?>

            val black = if(themeProps.isAmoled) "#000000" else "#181818"

            hexesNames = mapOf(
                "bl_900" to black,
                "wh_100" to "#FFFFFF",
                "gr_200" to "#F0F0F0",
                "gr_300" to "#DBDBDB",
                "gr_500" to "#919191",
                "gr_700" to "#707070",
                "gr_800" to "#464646",
                "gr_900" to "#202020",
                "ac_200" to tints["ac_200"],
                "ac_300" to tints["ac_300"],
                "ac_500" to tints["ac_500"],
                "ac_700" to tints["ac_700"],
                "ac_800" to tints["ac_800"],
                "yl_500" to "#E3B727",
                "yl_700" to "#DFA000",
                "rd_300" to "#FF6666",
                "rd_500" to "#DE4747",
                "rd_700" to "#C64949",
                "gn_300" to "#9ED448",
                "gn_500" to "#50B232",
                "gn_700" to "#26972C",
                "pu_500" to "#8E85EE",
                "tr_500" to "#00000000",
                "tr_ac300" to "#44${tints["ac_300"]?.drop(1)}",
                "tr_ac500" to "#77${tints["ac_500"]?.drop(1)}",
                "tr_gr300" to "#77DBDBDB",
                "tr_gr500" to "#77919191",
                "tr_gr700" to "#AA707070",
                "tr_gr800" to "#AA464646",
            )

            var theme = templateFile

            hexesNames.forEach{
                it.value?.let { it1 -> theme = theme.replace(it.key, it1) }
            }

            if(!themeProps.isGradient) {
                theme = theme.replace("chat_outBubbleGradient", "NoGradient")
            }

            createPreview(theme, tints["ac_200"]!!, context, imageView)

            return theme
        }

        private fun createPreview(theme: String, themeBg: String, context: Context, imageView: ImageView) {
            val vector = VectorChildFinder(context, R.drawable.ic_preview, imageView)

            val themeList: MutableMap<String, String> = mutableMapOf()

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

            // names and colors from theme file
            val themeKeys = "^\\w+".toRegex(RegexOption.MULTILINE).findAll(theme).map { it.value }.toList()
            val themeValues =
                "#\\w+$".toRegex(RegexOption.MULTILINE).findAll(theme).map { it.value }.toList()

            // set themeList
            previewKeys.forEach {
                val value = if (it.last().isDigit()) it.dropLast(2) else it
                themeList[it] = themeValues[themeKeys.indexOf(value)]
            }
            themeList["theme_bg"] = themeBg

            themeList.forEach {
                val color = Color.parseColor(it.value)
                val path = vector.findPathByName(it.key)
                if (path != null) {
                    if (it.key == "chats_onlineCircle") {
                        path.strokeColor = Color.parseColor(themeList["actionBarDefault_0"])
                    }
                    else if (it.key.dropLast(2) == "actionBarDefault") {
                        path.strokeColor = Color.parseColor(themeList["chats_secretName"])
                    }
                    else if (it.key.dropLast(2) == "chat_inBubble") {
                        path.strokeColor = Color.parseColor(themeList["chat_inBubbleShadow"])
                    }

                    path.fillColor = color
                }
            }

            imageView.invalidate()
        }
    }
}