package com.therxmv.telegramthemer.data.adapter

import com.therxmv.preview.model.AppbarColors
import com.therxmv.preview.model.ChatColors
import com.therxmv.preview.model.ChatListColors
import com.therxmv.preview.model.ChatsColors
import com.therxmv.preview.model.MessageColors
import com.therxmv.preview.model.MessagePanelColors
import com.therxmv.preview.model.PlayerPanelColors
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.model.TabsColors
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarDefaultIcon
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarDefaultSubtitle
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarDefaultTitle
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabActiveText
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabLine
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabUnactiveText
import com.therxmv.preview.utils.AtthemePreviewKeys.avatar_backgroundBlue
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inAudioDurationText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inBubble
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inFileInfoText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inFileNameText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inLoader
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inReplyLine
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inReplyMessageText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inReplyNameText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inVoiceSeekbar
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_inVoiceSeekbarFill
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messagePanelIcons
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messagePanelText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messageTextIn
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_messageTextOut
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outAudioDurationText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outBubble
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outFileInfoText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outFileNameText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outLoader
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outReplyLine
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outReplyMessageText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outReplyNameText
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outVoiceSeekbar
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_outVoiceSeekbarFill
import com.therxmv.preview.utils.AtthemePreviewKeys.chat_serviceBackground
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_actionBackground
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
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_tabUnreadActiveBackground
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_tabUnreadUnactiveBackground
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_unreadCounter
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_unreadCounterMuted
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerClose
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerPerformer
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerPlayPause
import com.therxmv.preview.utils.AtthemePreviewKeys.tt_background
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.model.accent
import com.therxmv.telegramthemer.domain.values.ThemeValues
import javax.inject.Inject

/**
 * Uses tints and attheme map from [ThemeValues]
 * to create objects for [com.therxmv.preview.ChatPreview] and [com.therxmv.preview.ChatsListPreview].
 */
@Suppress("RemoveRedundantQualifierName")
class ThemeToPreviewAdapter @Inject constructor(
    private val themeValues: ThemeValues,
) : PreviewColorsAdapter {

    override fun getThemePreviewColors(themeState: ThemeState): PreviewColorsModel {
        val tints = themeValues.getTintedColorSchema(themeState)
        val atthemeMap = themeValues.getAtthemeMap(themeState)

        val getTint: AtthemePreviewKeys.() -> Int = {
            val tintKey = atthemeMap.getValue(this.name)
            // themeState.overwrittenColors[tintKey] is required for "tt_background"
            val overwrittenColor = (themeState.overwrittenColors[this.name] ?: themeState.overwrittenColors[tintKey])
            overwrittenColor ?: tints[tintKey]
        }

        val background = themeState.overwrittenColors[tt_background.name] ?: tints[tt_background.name]
        val accentColor = tints[accent(5)]
        val previewBackground = tints[accent(9)].takeIf { themeState.isDark } ?: tints[accent(2)]
        val previewGradient = when {
            themeState.isDark -> listOf(previewBackground, accentColor, background)
            else -> listOf(background, accentColor, previewBackground)
        }

        return PreviewColorsModel(
            accent = accentColor,
            background = background,
            previewGradient = previewGradient,
            appbarColors = AppbarColors(
                appbarIcon = actionBarDefaultIcon.getTint(),
                appbarTitle = actionBarDefaultTitle.getTint(),
                appbarSubtitle = actionBarDefaultSubtitle.getTint(),
                appbarAvatar = avatar_backgroundBlue.getTint(),
            ),
            chatListColors = getChatListColors(background, getTint),
            chatColors = getChatColors(getTint),
        )
    }

    private fun getChatListColors(background: Int, getTint: AtthemePreviewKeys.() -> Int) = ChatListColors(
        actionButton = chats_actionBackground.getTint(),
        tabsColors = TabsColors(
            tab = actionBarTabUnactiveText.getTint(),
            selectedTab = actionBarTabActiveText.getTint(),
            tabSelector = actionBarTabLine.getTint(),
            selectedTabUnread = chats_tabUnreadActiveBackground.getTint(),
            tabUnread = chats_tabUnreadUnactiveBackground.getTint(),
        ),
        chatsColors = ChatsColors(
            background = background,
            chatDate = chats_date.getTint(),
            unreadCounter = chats_unreadCounter.getTint(),
            unreadCounterMuted = chats_unreadCounterMuted.getTint(),
            avatarColor = avatar_backgroundBlue.getTint(),
            chatName = chats_name.getTint(),
            senderName = chats_nameMessage.getTint(),
            message = chats_message.getTint(),
            actionMessage = chats_actionMessage.getTint(),
            muteIcon = chats_muteIcon.getTint(),
            online = chats_onlineCircle.getTint(),
            secretIcon = chats_secretIcon.getTint(),
            secretName = chats_secretName.getTint(),
            sentCheck = chats_sentReadCheck.getTint(),
        ),
    )

    private fun getChatColors(getTint: AtthemePreviewKeys.() -> Int) = ChatColors(
        messagePanelColors = MessagePanelColors(
            icon = chat_messagePanelIcons.getTint(),
            message = chat_messagePanelText.getTint(),
        ),
        playerPanelColors = PlayerPanelColors(
            play = inappPlayerPlayPause.getTint(),
            name = inappPlayerPerformer.getTint(),
            icons = inappPlayerClose.getTint(),
        ),
        inMessageColors = MessageColors(
            background = chat_inBubble.getTint(),
            text = chat_messageTextIn.getTint(),
            date = chat_serviceBackground.getTint(),
            replyColors = MessageColors.ReplyColors(
                line = chat_inReplyLine.getTint(),
                sender = chat_inReplyNameText.getTint(),
                text = chat_inReplyMessageText.getTint(),
            ),
            fileColors = MessageColors.FileColors(
                loader = chat_inLoader.getTint(),
                name = chat_inFileNameText.getTint(),
                info = chat_inFileInfoText.getTint(),
            ),
            voiceColors = MessageColors.VoiceColors(
                loader = chat_inLoader.getTint(),
                seekbar = chat_inVoiceSeekbar.getTint(),
                seekbarFill = chat_inVoiceSeekbarFill.getTint(),
                info = chat_inAudioDurationText.getTint(),
            ),
        ),
        outMessageColors = MessageColors(
            background = chat_outBubble.getTint(),
            text = chat_messageTextOut.getTint(),
            date = chat_serviceBackground.getTint(),
            replyColors = MessageColors.ReplyColors(
                line = chat_outReplyLine.getTint(),
                sender = chat_outReplyNameText.getTint(),
                text = chat_outReplyMessageText.getTint(),
            ),
            fileColors = MessageColors.FileColors(
                loader = chat_outLoader.getTint(),
                name = chat_outFileNameText.getTint(),
                info = chat_outFileInfoText.getTint(),
            ),
            voiceColors = MessageColors.VoiceColors(
                loader = chat_outLoader.getTint(),
                seekbar = chat_outVoiceSeekbar.getTint(),
                seekbarFill = chat_outVoiceSeekbarFill.getTint(),
                info = chat_outAudioDurationText.getTint(),
            ),
        ),
    )
}