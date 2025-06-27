package com.therxmv.preview.utils

/**
 * Each entry contains all relatable theme keys.
 * [similarKeys] list will be used to change values with the Advanced mode.
 */
@Suppress("EnumEntryName")
enum class AtthemePreviewKeys(val similarKeys: List<String>) {
    tt_background(listOf(tt_background.name)),
    actionBarDefaultIcon(listOf(
        actionBarDefaultIcon.name,
        "actionBarActionModeDefaultIcon",
        "actionBarDefaultSubmenuItemIcon",
        "avatar_actionBarIconBlue",
        "avatar_actionBarIconCyan",
        "avatar_actionBarIconGreen",
        "avatar_actionBarIconOrange",
        "avatar_actionBarIconPink",
        "avatar_actionBarIconRed",
        "avatar_actionBarIconViolet",
    )),
    actionBarDefaultTitle(listOf(actionBarDefaultTitle.name)),
    actionBarDefaultSubtitle(listOf(actionBarDefaultSubtitle.name)),
    chats_actionBackground(listOf(chats_actionBackground.name)),
    actionBarTabUnactiveText(listOf(actionBarTabUnactiveText.name)),
    actionBarTabActiveText(listOf(actionBarTabActiveText.name)),
    actionBarTabLine(listOf(actionBarTabLine.name)),

    chats_tabUnreadActiveBackground(listOf(chats_tabUnreadActiveBackground.name)),
    chats_tabUnreadUnactiveBackground(listOf(chats_tabUnreadUnactiveBackground.name)),
    chats_date(listOf(chats_date.name)),
    chats_unreadCounter(listOf(chats_unreadCounter.name)),
    chats_unreadCounterMuted(listOf(chats_unreadCounterMuted.name)),
    avatar_backgroundBlue(listOf(
        avatar_backgroundBlue.name,
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
    )),
    chats_name(listOf(chats_name.name)),
    chats_nameMessage(listOf(chats_nameMessage.name)),
    chats_message(listOf(chats_message.name)),
    chats_actionMessage(listOf(chats_actionMessage.name, "chats_attachMessage")),
    chats_muteIcon(listOf(chats_muteIcon.name)),
    chats_onlineCircle(listOf(chats_onlineCircle.name)),
    chats_secretIcon(listOf(chats_secretIcon.name)),
    chats_secretName(listOf(chats_secretName.name)),
    chats_sentReadCheck(listOf(chats_sentReadCheck.name)),

    chat_messagePanelText(listOf(chat_messagePanelText.name)),
    chat_messagePanelIcons(listOf(chat_messagePanelIcons.name)),

    inappPlayerPlayPause(listOf(inappPlayerPlayPause.name)),
    inappPlayerClose(listOf(inappPlayerClose.name)),
    inappPlayerPerformer(listOf(inappPlayerPerformer.name)),

    chat_outBubble(listOf(chat_outBubble.name, "chat_outBubbleSelected")),
    chat_inBubble(listOf(chat_inBubble.name, "chat_inBubbleSelected")),
    chat_messageTextOut(listOf(chat_messageTextOut.name)),
    chat_messageTextIn(listOf(chat_messageTextIn.name)),
    chat_serviceBackground(listOf(chat_serviceBackground.name)),

    chat_outReplyNameText(listOf(chat_outReplyNameText.name)),
    chat_outReplyLine(listOf(chat_outReplyLine.name)),
    chat_outReplyMessageText(listOf(chat_outReplyMessageText.name, "chat_outReplyMediaMessageText", "chat_outReplyMediaMessageSelectedText")),
    chat_inReplyNameText(listOf(chat_inReplyNameText.name)),
    chat_inReplyLine(listOf(chat_inReplyLine.name)),
    chat_inReplyMessageText(listOf(chat_inReplyMessageText.name, "chat_inReplyMediaMessageText", "chat_inReplyMediaMessageSelectedText")),

    chat_inLoader(listOf(chat_inLoader.name, "chat_inLoaderSelected")),
    chat_outLoader(listOf(chat_outLoader.name, "chat_outLoaderSelected")),
    chat_outFileNameText(listOf(chat_outFileNameText.name)),
    chat_outFileInfoText(listOf(chat_outFileInfoText.name)),
    chat_inFileNameText(listOf(chat_inFileNameText.name)),
    chat_inFileInfoText(listOf(chat_inFileInfoText.name)),

    // TODO maybe add audio seekbar as well
    chat_outVoiceSeekbar(listOf(chat_outVoiceSeekbar.name, "chat_outVoiceSeekbarSelected")),
    chat_outVoiceSeekbarFill(listOf(chat_outVoiceSeekbarFill.name)),
    chat_outAudioDurationText(listOf(chat_outAudioDurationText.name, "chat_outAudioDurationSelectedText")),
    chat_inVoiceSeekbar(listOf(chat_inVoiceSeekbar.name, "chat_inVoiceSeekbarSelected")),
    chat_inVoiceSeekbarFill(listOf(chat_inVoiceSeekbarFill.name)),
    chat_inAudioDurationText(listOf(chat_inAudioDurationText.name, "chat_inAudioDurationSelectedText")),
}