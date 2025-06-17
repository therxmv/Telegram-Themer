package com.therxmv.telegramthemer.data.theme.preview

import android.graphics.Color
import com.therxmv.preview.model.AppbarColors
import com.therxmv.preview.model.ChatColors
import com.therxmv.preview.model.ChatListColors
import com.therxmv.preview.model.ChatsColors
import com.therxmv.preview.model.MessageColors
import com.therxmv.preview.model.MessagePanelColors
import com.therxmv.preview.model.PlayerPanelColors
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.model.TabsColors
import com.therxmv.telegramthemer.data.theme.ThemeValues
import com.therxmv.telegramthemer.data.theme.utils.AdvancedThemeKeys
import com.therxmv.telegramthemer.data.theme.utils.AtthemePreviewKeys
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import javax.inject.Inject

class ThemeToPreviewAdapter @Inject constructor(
    private val themeValues: ThemeValues,
): PreviewColorsAdapter {

    override fun getThemePreviewColors(themeState: ThemeState): PreviewColorsModel { // TODO lack of documentation
        val values = themeValues.getAdvancedColorSchema(themeState)
        val atthemeMap = themeValues.getAtthemeMap(themeState)
        val get: Collection<String>.() -> Int = {
            val colorKey = atthemeMap.getValue(this.first())
            Color.parseColor(values.getValue(colorKey))
        }
        val backgroundKeys = atthemeMap.filter {
            it.value == AdvancedThemeKeys.background
        }.keys // TODO investigate if need
        val background = backgroundKeys.get()

        val previewBackground = if (themeState.isDark) {
            AdvancedThemeKeys.accent_9
        } else {
            AdvancedThemeKeys.accent_2
        }.run { Color.parseColor(values.getValue(this)) }

        val accentColor = Color.parseColor(values.getValue(AdvancedThemeKeys.accent_5))

        val previewGradient = when {
            themeState.isDark -> listOf(previewBackground, accentColor, background)
            else -> listOf(background, accentColor, previewBackground)
        }

        return PreviewColorsModel(
            accent = accentColor,
            background = background,
            previewGradient = previewGradient,
            appbarColors = AppbarColors(
                appbarIcon = AtthemePreviewKeys.actionBarDefaultIcon.get(),
                appbarTitle = AtthemePreviewKeys.actionBarDefaultTitle.get(),
                appbarSubtitle = AtthemePreviewKeys.actionBarDefaultSubtitle.get(),
                appbarAvatar = AtthemePreviewKeys.avatar_backgroundBlue.get(),
            ),
            chatListColors = getChatListColors(background, get),
            chatColors = getChatColors(get),
        )
    }

    private fun getChatListColors(background: Int, get: Collection<String>.() -> Int) = ChatListColors(
        actionButton = AtthemePreviewKeys.chats_actionBackground.get(),
        tabsColors = TabsColors(
            tab = AtthemePreviewKeys.actionBarTabUnactiveText.get(),
            selectedTab = AtthemePreviewKeys.actionBarTabActiveText.get(),
            tabSelector = AtthemePreviewKeys.actionBarTabLine.get(),
            selectedTabUnread = AtthemePreviewKeys.chats_tabUnreadActiveBackground.get(),
            tabUnread = AtthemePreviewKeys.chats_tabUnreadUnactiveBackground.get(),
        ),
        chatsColors = ChatsColors(
            background = background,
            chatDate = AtthemePreviewKeys.chats_date.get(),
            unreadCounter = AtthemePreviewKeys.chats_unreadCounter.get(),
            unreadCounterMuted = AtthemePreviewKeys.chats_unreadCounterMuted.get(),
            avatarColor = AtthemePreviewKeys.avatar_backgroundBlue.get(),
            chatName = AtthemePreviewKeys.chats_name.get(),
            senderName = AtthemePreviewKeys.chats_nameMessage.get(),
            message = AtthemePreviewKeys.chats_message.get(),
            actionMessage = AtthemePreviewKeys.chats_actionMessage.get(),
            muteIcon = AtthemePreviewKeys.chats_muteIcon.get(),
            online = AtthemePreviewKeys.chats_onlineCircle.get(),
            secretIcon = AtthemePreviewKeys.chats_secretIcon.get(),
            secretName = AtthemePreviewKeys.chats_secretName.get(),
            sentCheck = AtthemePreviewKeys.chats_sentReadCheck.get(),
        ),
    )

    private fun getChatColors(get: Collection<String>.() -> Int) = ChatColors(
        messagePanelColors = MessagePanelColors(
            icon = AtthemePreviewKeys.chat_messagePanelIcons.get(),
            message = AtthemePreviewKeys.chat_messagePanelText.get(),
        ),
        playerPanelColors = PlayerPanelColors(
            play = AtthemePreviewKeys.inappPlayerPlayPause.get(),
            name = AtthemePreviewKeys.inappPlayerPerformer.get(),
            icons = AtthemePreviewKeys.inappPlayerClose.get(),
        ),
        inMessageColors = MessageColors(
            background = AtthemePreviewKeys.chat_inBubble.get(),
            text = AtthemePreviewKeys.chat_messageTextIn.get(),
            date = AtthemePreviewKeys.chat_serviceBackground.get(),
            replyColors = MessageColors.ReplyColors(
                line = AtthemePreviewKeys.chat_inReplyLine.get(),
                sender = AtthemePreviewKeys.chat_inReplyNameText.get(),
                text = AtthemePreviewKeys.chat_inReplyMessageText.get(),
            ),
            fileColors = MessageColors.FileColors(
                loader = AtthemePreviewKeys.chat_inLoader.get(),
                name = AtthemePreviewKeys.chat_inFileNameText.get(),
                info = AtthemePreviewKeys.chat_inFileInfoText.get(),
            ),
            voiceColors = MessageColors.VoiceColors(
                loader = AtthemePreviewKeys.chat_inLoader.get(),
                seekbar = AtthemePreviewKeys.chat_inVoiceSeekbar.get(),
                seekbarFill = AtthemePreviewKeys.chat_inVoiceSeekbarFill.get(),
                info = AtthemePreviewKeys.chat_inAudioDurationText.get(),
            ),
        ),
        outMessageColors = MessageColors(
            background = AtthemePreviewKeys.chat_outBubble.get(),
            text = AtthemePreviewKeys.chat_messageTextOut.get(),
            date = AtthemePreviewKeys.chat_serviceBackground.get(),
            replyColors = MessageColors.ReplyColors(
                line = AtthemePreviewKeys.chat_outReplyLine.get(),
                sender = AtthemePreviewKeys.chat_outReplyNameText.get(),
                text = AtthemePreviewKeys.chat_outReplyMessageText.get(),
            ),
            fileColors = MessageColors.FileColors(
                loader = AtthemePreviewKeys.chat_outLoader.get(),
                name = AtthemePreviewKeys.chat_outFileNameText.get(),
                info = AtthemePreviewKeys.chat_outFileInfoText.get(),
            ),
            voiceColors = MessageColors.VoiceColors(
                loader = AtthemePreviewKeys.chat_outLoader.get(),
                seekbar = AtthemePreviewKeys.chat_outVoiceSeekbar.get(),
                seekbarFill = AtthemePreviewKeys.chat_outVoiceSeekbarFill.get(),
                info = AtthemePreviewKeys.chat_outAudioDurationText.get(),
            ),
        ),
    )
}