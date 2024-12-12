package com.therxmv.telegramthemer.data.theme.utils

/**
 * Base colors like main accent, background, gray
 */
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
 * Specific color keys for attheme template file.
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

/**
 * Each variable contains all relatable theme keys. First key in list will be used for Preview.
 * List will be used to change values with the Pro mode.
 */
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
    val actionBarDefaultSubtitle = listOf("actionBarDefaultSubtitle")
    val chats_actionBackground = listOf("chats_actionBackground")
    val actionBarTabUnactiveText = listOf("actionBarTabUnactiveText")
    val actionBarTabActiveText = listOf("actionBarTabActiveText")
    val actionBarTabLine = listOf("actionBarTabLine")

    val chats_tabUnreadActiveBackground = listOf("chats_tabUnreadActiveBackground")
    val chats_tabUnreadUnactiveBackground = listOf("chats_tabUnreadUnactiveBackground")
    val chats_date = listOf("chats_date")
    val chats_unreadCounter = listOf("chats_unreadCounter")
    val chats_unreadCounterMuted = listOf("chats_unreadCounterMuted")
    val avatar_backgroundBlue = listOf(
        "avatar_backgroundBlue",
        "avatar_background2Blue",
        "avatar_backgroundCyan",
        "avatar_background2Cyan",
        "avatar_backgroundGreen",
        "avatar_background2Green",
        "avatar_backgroundOrange",
        "avatar_background2Orange",
        "avatar_backgroundPink",
        "avatar_background2Pink",
        "avatar_backgroundRed",
        "avatar_background2Red",
        "avatar_backgroundSaved",
        "avatar_background2Saved",
        "avatar_backgroundViolet",
        "avatar_background2Violet",
    )
    val chats_name = listOf("chats_name")
    val chats_nameMessage = listOf("chats_nameMessage")
    val chats_message = listOf("chats_message")
    val chats_actionMessage = listOf("chats_actionMessage", "chats_attachMessage")
    val chats_muteIcon = listOf("chats_muteIcon")
    val chats_onlineCircle = listOf("chats_onlineCircle")
    val chats_secretIcon = listOf("chats_secretIcon")
    val chats_secretName = listOf("chats_secretName")
    val chats_sentReadCheck = listOf("chats_sentReadCheck")

    val chat_messagePanelText = listOf("chat_messagePanelText")
    val chat_messagePanelIcons = listOf("chat_messagePanelIcons")

    val inappPlayerPlayPause = listOf("inappPlayerPlayPause")
    val inappPlayerClose = listOf("inappPlayerClose")
    val inappPlayerPerformer = listOf("inappPlayerPerformer")

    val chat_outBubble = listOf("chat_outBubble", "chat_outBubbleSelected")
    val chat_inBubble = listOf("chat_inBubble", "chat_inBubbleSelected")
    val chat_messageTextOut = listOf("chat_messageTextOut")
    val chat_messageTextIn = listOf("chat_messageTextIn")
    val chat_serviceBackground = listOf("chat_serviceBackground")

    val chat_outReplyNameText = listOf("chat_outReplyNameText")
    val chat_outReplyLine = listOf("chat_outReplyLine")
    val chat_outReplyMessageText = listOf("chat_outReplyMessageText", "chat_outReplyMediaMessageText", "chat_outReplyMediaMessageSelectedText")
    val chat_inReplyNameText = listOf("chat_inReplyNameText")
    val chat_inReplyLine = listOf("chat_inReplyLine")
    val chat_inReplyMessageText = listOf("chat_inReplyMessageText", "chat_inReplyMediaMessageText", "chat_inReplyMediaMessageSelectedText")

    val chat_inLoader = listOf("chat_inLoader", "chat_inLoaderSelected")
    val chat_outLoader = listOf("chat_outLoader", "chat_outLoaderSelected")
    val chat_outFileNameText = listOf("chat_outFileNameText")
    val chat_outFileInfoText = listOf("chat_outFileInfoText")
    val chat_inFileNameText = listOf("chat_inFileNameText")
    val chat_inFileInfoText = listOf("chat_inFileInfoText")

    // TODO maybe add audio seekbar as well
    val chat_outVoiceSeekbar = listOf("chat_outVoiceSeekbar", "chat_outVoiceSeekbarSelected")
    val chat_outVoiceSeekbarFill = listOf("chat_outVoiceSeekbarFill")
    val chat_outAudioDurationText = listOf("chat_outAudioDurationText", "chat_outAudioDurationSelectedText")
    val chat_inVoiceSeekbar = listOf("chat_inVoiceSeekbar", "chat_inVoiceSeekbarSelected")
    val chat_inVoiceSeekbarFill = listOf("chat_inVoiceSeekbarFill")
    val chat_inAudioDurationText = listOf("chat_inAudioDurationText", "chat_inAudioDurationSelectedText")
}