package com.therxmv.telegramthemer.ui.editor.data.preview

import android.graphics.Color
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.ChatsColors
import com.therxmv.preview.PreviewColorsModel
import com.therxmv.preview.TabsColors
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import com.therxmv.telegramthemer.ui.editor.data.ThemeValues
import com.therxmv.telegramthemer.ui.editor.data.utils.AdvancedThemeKeys.accent_2
import com.therxmv.telegramthemer.ui.editor.data.utils.AdvancedThemeKeys.accent_5
import com.therxmv.telegramthemer.ui.editor.data.utils.AdvancedThemeKeys.accent_9
import com.therxmv.telegramthemer.ui.editor.data.utils.AdvancedThemeKeys.background
import com.therxmv.telegramthemer.ui.editor.data.utils.AtthemePreviewKeys
import javax.inject.Inject

class ThemeToPreviewAdapter @Inject constructor(
    private val themeValues: ThemeValues,
): PreviewColorsAdapter {

    override fun getDefaultThemeColors(themeState: ThemeState): PreviewColorsModel {
        val values = themeValues.getAdvancedColorSchema(themeState)
        val atthemeMap = themeValues.getAtthemeMap(themeState)
        val get: Collection<String>.() -> Int = {
            val colorKey = atthemeMap.getValue(this.first())
            Color.parseColor(values.getValue(colorKey))
        }
        val backgroundKeys = atthemeMap.filter { it.value == background }.keys
        val background = backgroundKeys.get()

        val previewBackground = Color.parseColor(values.getValue(accent_9)).takeIf { themeState.isDark }
            ?: Color.parseColor(values.getValue(accent_2))

        return PreviewColorsModel(
            accent = Color.parseColor(values.getValue(accent_5)),
            background = background,
            previewBackground = previewBackground,
            actionButton = AtthemePreviewKeys.chats_actionBackground.get(),
            appbarColors = AppbarColors(
                appbarIcon = AtthemePreviewKeys.actionBarDefaultIcon.get(),
                appbarTitle = AtthemePreviewKeys.actionBarDefaultTitle.get(),
            ),
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
    }
}