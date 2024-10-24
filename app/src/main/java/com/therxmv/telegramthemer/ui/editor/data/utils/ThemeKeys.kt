package com.therxmv.telegramthemer.ui.editor.data.utils

object BaseThemeKeys {
    const val background = "background"
    const val onBackground = "onBackground"
    const val accent = "accent"
    const val gray = "gray"
    const val red = "red"
    const val orange = "orange"
    const val yellow = "yellow"
    const val green = "green"
    const val blue = "blue"
    const val purple = "purple"
    const val transparent = "transparent"
}

/**
 * Number after the name stays for luminosity, the higher number - the lighter color.
 * From 1 to 9, 5 is default.
 */
object AdvancedThemeKeys {
    const val background = "tt_background"
    const val onBackground = "tt_onBackground"
    const val gray_1 = "gray_1"
    const val gray_3 = "gray_3"
    const val gray_5 = "gray_5"
    const val gray_8 = "gray_8"
    const val gray_9 = "gray_9"
    const val accent_2 = "accent_2"
    const val accent_3 = "accent_3"
    const val accent_4 = "accent_4"
    const val accent_5 = "accent_5"
    const val accent_7 = "accent_7"
    const val accent_9 = "accent_9"
    const val red_5 = "red_5"
    const val orange_5 = "orange_5"
    const val yellow_5 = "yellow_5"
    const val green_5 = "green_5"
    const val blue_5 = "blue_5"
    const val purple_5 = "purple_5"
    const val transparent_0 = "transparent_0"
    const val tr_accent_5 = "tr_accent_5"
    const val tr_accent_7 = "tr_accent_7"
    const val tr_gray_5 = "tr_gray_5"
    const val tr_gray_3 = "tr_gray_3"
}

object AtthemePreviewKeys {
    val actionBarDefaultIcon = listOf(
        "actionBarDefaultIcon",
        "actionBarActionModeDefaultIcon",
        "actionBarDefaultSubmenuItemIcon",
        "avatar_actionBarIconBlue",
        "avatar_actionBarIconCyan",
        "avatar_actionBarIconGreen",
        "avatar_actionBarIconOrange",
        "avatar_actionBarIconPink",
        "avatar_actionBarIconRed",
        "avatar_actionBarIconViolet",
    )
    val actionBarDefaultTitle = listOf("actionBarDefaultTitle")
    val chats_actionBackground = listOf("chats_actionBackground")
    val actionBarTabUnactiveText = listOf("actionBarTabUnactiveText")
    val actionBarTabActiveText = listOf("actionBarTabActiveText")
    val actionBarTabLine = listOf("actionBarTabLine")
    val chats_tabUnreadActiveBackground = listOf("chats_tabUnreadActiveBackground")
    val chats_tabUnreadUnactiveBackground = listOf("chats_tabUnreadUnactiveBackground")
    val chats_date = listOf("chats_date")
    val chats_unreadCounter = listOf("chats_unreadCounter")
    val chats_unreadCounterMuted = listOf("chats_unreadCounterMuted")
    val avatar_backgroundBlue = listOf("avatar_backgroundBlue")
    val chats_name = listOf("chats_name")
    val chats_nameMessage = listOf("chats_nameMessage")
    val chats_message = listOf("chats_message")
    val chats_actionMessage = listOf("chats_actionMessage", "chats_attachMessage")
    val chats_muteIcon = listOf("chats_muteIcon")
    val chats_onlineCircle = listOf("chats_onlineCircle")
    val chats_secretIcon = listOf("chats_secretIcon")
    val chats_secretName = listOf("chats_secretName")
    val chats_sentReadCheck = listOf("chats_sentReadCheck")
}