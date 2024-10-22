package com.therxmv.telegramthemer.ui.editor.data

import android.graphics.Color
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.ChatsColors
import com.therxmv.preview.PreviewColorsModel
import com.therxmv.preview.TabsColors
import javax.inject.Inject

class ThemeToPreviewAdapter @Inject constructor(
    private val themeValues: ThemeValues,
): PreviewColorsAdapter {

    override fun getDefaultThemeColors(themeState: ThemeState): PreviewColorsModel {
        val values = themeValues.getAdvancedColorSchema(themeState)
        val get: String.() -> Int = {
            Color.parseColor(values.getValue(this))
        }

        return PreviewColorsModel(
            accent = AdvancedThemeKeys.accent_5.get(),
            background = AdvancedThemeKeys.background.get(),
            actionButton = AdvancedThemeKeys.accent_5.get(), // chats_actionBackground
            appbarColors = AppbarColors(
                appbarIcon = AdvancedThemeKeys.gray_5.get(), // actionBarDefaultIcon
                appbarTitle = AdvancedThemeKeys.black.get(), // actionBarDefaultTitle
            ),
            tabsColors = TabsColors(
                tab = AdvancedThemeKeys.gray_5.get(), // actionBarTabUnactiveText
                selectedTab = AdvancedThemeKeys.accent_5.get(), // actionBarTabActiveText
                tabSelector = AdvancedThemeKeys.accent_5.get(), // actionBarTabLine
                tabUnread = AdvancedThemeKeys.accent_5.get(), // chats_tabUnreadActiveBackground / chats_tabUnreadUnactiveBackground
            ),
            chatsColors = ChatsColors(
                background = AdvancedThemeKeys.background.get(),
                chatDate = AdvancedThemeKeys.gray_5.get(), // chats_date
                unreadCounter = AdvancedThemeKeys.accent_3.get(), // chats_unreadCounter
                unreadCounterMuted = AdvancedThemeKeys.gray_3.get(), // chats_unreadCounterMuted
                avatarColor = AdvancedThemeKeys.accent_5.get(), // avatar_backgroundBlue
                chatName = AdvancedThemeKeys.black.get(), // chats_name
                senderName = AdvancedThemeKeys.accent_5.get(), // chats_nameMessage
                message = AdvancedThemeKeys.gray_5.get(), // chats_message
                actionMessage = AdvancedThemeKeys.accent_5.get(), // chats_actionMessage / chats_attachMessage
                muteIcon = AdvancedThemeKeys.gray_3.get(), // chats_muteIcon
                online = AdvancedThemeKeys.accent_5.get(), // chats_onlineCircle
                secretIcon = AdvancedThemeKeys.accent_5.get(), // chats_secretIcon
                secretName = AdvancedThemeKeys.accent_5.get(), // chats_secretName
                sentCheck = AdvancedThemeKeys.accent_5.get(), // chats_sentReadCheck
            ),
        )
    }
}