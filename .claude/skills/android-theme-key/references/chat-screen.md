> Part of the [android-theme-key](../SKILL.md) skill's Android key reference. Covers §5 Chat screen in full (5.1–5.15: wallpaper/service messages, bubbles, text selection, replies/forwards/previews, media/loaders, audio/voice, files, cards, top panels, composer/recording, attach sheet, emoji/sticker panel, bots, quotes/code/tables, Instant View buttons). The largest single surface — 347 of the 819 keys. Relation-tag vocabulary and markers are defined once in `SKILL.md`.

## 5. Chat screen

336 of the 819 keys — more than 40% of the whole template. The organising
principle is the **`chat_in*` / `chat_out*` split**: nearly every element inside a
message bubble exists twice, once for received messages and once for sent ones.
Then `*Selected` doubles many of those again for the multi-select state. If you
touch an `in` key, look for its `out` twin; if you touch a base key, look for its
`*Selected` twin.

### 5.1 Wallpaper, service messages, and screen chrome

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_wallpaper` | The chat background behind all bubbles | — | pairs with `chat_wallpaper_gradient_rotation`, `wallpaperFileOffset`; `pair:rest/selected` with `chat_selectedBackground` |
| `chat_wallpaper_gradient_rotation` | **Not a color.** The rotation angle of the wallpaper gradient, in degrees | — | needs gradient stop keys to have any effect — see Open questions |
| `chat_wallpaper_gradient_to` † | The second stop of the wallpaper gradient. Note the name has no `1` — Telegram spells the first stop this way | — | `family`: wallpaper gradient, with the two `key_`-prefixed stops below; pairs with `chat_wallpaper_gradient_rotation` |
| `key_chat_wallpaper_gradient_to2` † | The third stop — `key_`-prefixed, which is its real spelling | — | `family`: wallpaper gradient |
| `key_chat_wallpaper_gradient_to3` † | The fourth stop — also `key_`-prefixed | — | `family`: wallpaper gradient |
| `chat_serviceBackground` | The centered pill behind date separators and "X joined the group" | — | `fill→on-fill`: `chat_serviceText`, `chat_serviceIcon`, `chat_serviceLink`. If omitted, Telegram derives it from the wallpaper; `pair:rest/selected` with `chat_serviceBackgroundSelected` |
| `chat_serviceBackgroundSelected` | That pill while the message is multi-selected | — | `pair:rest/selected` with `chat_serviceBackground` |
| `chat_serviceBackgroundSelector` † | The press ripple on a tappable service pill | `#20FFFFFF` | `family`: service pill |
| `chat_reactionServiceButtonBackgroundSelected` † | The reaction chip on a service message once you have reacted | `#FFFFFF` | `mirror`: `chat_serviceBackgroundSelected`; `fill→on-fill`: `chat_reactionServiceButtonTextSelected` |
| `chat_reactionServiceButtonTextSelected` † | The count on that chip | `#000000` | `fill→on-fill` with `chat_reactionServiceButtonBackgroundSelected` |
| `chat_serviceText` | Text inside the service pill ("August 17", "September 1") | `#FFFFFF` | `fill→on-fill` with `chat_serviceBackground` |
| `chat_serviceIcon` | Icon inside the service pill | `#FFFFFF` | `fill→on-fill` with `chat_serviceBackground` |
| `chat_serviceLink` | A tappable link inside the service pill | `#FFFFFF` | `fill→on-fill` with `chat_serviceBackground` |
| `chat_selectedBackground` | The tint over the whole row of a multi-selected message | `#280A90F0` | `pair:rest/selected` with `chat_wallpaper` |
| `chat_BlurAlpha` | The blur overlay's alpha layer. One of only two literal (non-role) colors in the Android templates | `#B2000000` | `pair` with `chat_BlurAlphaSlow` † |
| `chat_BlurAlphaSlow` † | The same overlay for the slower blur pass | `#C1000000` | `pair` with `chat_BlurAlpha` |
| `chat_unreadMessagesStartBackground` | The "Unread messages" divider bar | `#FFFFFF` | `fill→on-fill`: `chat_unreadMessagesStartText`, `chat_unreadMessagesStartArrowIcon` |
| `chat_unreadMessagesStartText` | The label on that divider | `#5695CC` | `fill→on-fill` with `chat_unreadMessagesStartBackground` |
| `chat_unreadMessagesStartArrowIcon` | The arrow on that divider | `#A2B5C7` | `fill→on-fill` with `chat_unreadMessagesStartBackground` |
| `chat_goDownButton` | The floating "scroll to bottom" circle | `#FFFFFF` | pairs with its counter |
| `chat_goDownButtonCounterBackground` | The unread badge on that circle | `#229AF0` | `fill→on-fill`: `chat_goDownButtonCounter` |
| `chat_goDownButtonCounter` | The number in that badge | `#FFFFFF` | `fill→on-fill` with `chat_goDownButtonCounterBackground` |
| `chat_status` | The "online" / "last seen" subtitle in the chat's action bar | `#298ACF` | `mirror`: `avatar_subtitleInProfileBlue` |
| `chat_muteIcon` | The muted-bell icon in the chat's action bar | `#79817E` | `mirror`: `chats_muteIcon` |
| `chat_lockIcon` | The lock icon marking a secret chat in the header | `#222222` | `family`: secret chat, with `chat_secretChatStatusText` |
| `chat_secretChatStatusText` | The "waiting for X to come online" status in a secret chat | `#7F7F7F` | `family`: secret chat |
| `chat_secretTimeText` | The self-destruct countdown on a secret-chat message | `#E4E2E0` | `family`: secret chat |
| `chat_addContact` | The "Add to contacts" bar shown above an unknown sender's chat | `#298ACF` | — |
| `chat_recordTime` | The elapsed-time readout while recording a voice message | `#8E959B` | `family`: voice recording (§5.11) |
| `chat_searchPanelIcons` | Icons in the in-chat search bar (prev/next result) | `#676A6F` | `pair` with `chat_searchPanelText` |
| `chat_searchPanelText` | The "N of M" result counter in that bar | `#676A6F` | `pair` with `chat_searchPanelIcons` |
| `chat_gifSaveHintBackground` | The dark toast that appears after saving a GIF | `#E21F2B38` | `fill→on-fill`: `chat_gifSaveHintText` |
| `chat_gifSaveHintText` | The text in that toast | `#FFFFFF` | `fill→on-fill` with `chat_gifSaveHintBackground` |
| `chat_stickersHintPanel` | The popup above the input suggesting a sticker for the typed emoji | `#FFFFFF` | `family`: input suggestions, with `chat_inlineResultIcon` |
| `chat_inlineResultIcon` | The icon on an inline bot result row | `#5795CC` | `family`: input suggestions |
| `chat_botSwitchToInlineText` | The "Switch to inline mode" prompt from a bot | `#4391CC` | `family`: bots (§5.13) |
| `chat_fieldOverlayText` | Accent text overlaid on the input field ("SCHEDULE", "EDIT") | `#298ACF` | `family`: composer (§5.11) |
| `chat_editMediaButton` † | The edit (pencil/crop) button on media in the composer | `#1A9CFF` | `family`: composer |
| `chat_sentError` | The red error disc beside a message that failed to send | `#DB3535` | `fill→on-fill`: `chat_sentErrorIcon`; `mirror`: `chats_sentError` |
| `chat_sentErrorIcon` | The "!" inside that disc | `#FFFFFF` | `fill→on-fill` with `chat_sentError` |
| `chat_previewDurationText` | Video duration text on a link-preview thumbnail | `#FFFFFF` | `pair` with `chat_previewGameText` |
| `chat_previewGameText` | Title text on a game link preview | `#FFFFFF` | `pair` with `chat_previewDurationText` |
| `chat_tagAdmin` † | The "admin" tag chip beside a group member's name | `#40A920` | `pair` with `chat_tagCreator` †; visible as the green "Староста" chip |
| `chat_tagCreator` † | The "owner/creator" tag chip | `#955CDB` | `pair` with `chat_tagAdmin` † |
| `chat_adminText` | The plain "admin" label above a group message | `#C0C6CB` | `pair:rest/selected` with `chat_adminSelectedText`; `pair:in/out` with `chat_outAdminText` † |
| `chat_adminSelectedText` | That label while the message is selected | `#89B4C1` | `pair:rest/selected` with `chat_adminText` |
| `chat_outAdminText` † | The same admin label on an outgoing message | `#70B15C` | `pair:in/out` with `chat_adminText`; `pair:rest/selected` with `chat_outAdminSelectedText` † |
| `chat_outAdminSelectedText` † | Its selected state | `#70B15C` | `pair:rest/selected` with `chat_outAdminText` † |
| `chat_inPsaNameText` † | The name label on a public-service-announcement forward, incoming | `#5A9C39` | `pair:in/out` with `chat_outPsaNameText` † |
| `chat_outPsaNameText` † | The same on an outgoing PSA forward | `#5A9C39` | `pair:in/out` with `chat_inPsaNameText` † |

### 5.2 Bubbles, message text, and status markers

The single most consequential group. In the Default style the outgoing bubble is
accent-filled and everything drawn on it is inverted to `tt_background` — see
[`../templates/android/default/CLAUDE.md`](../templates/android/default/CLAUDE.md).

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inBubble` | The received-message bubble fill | `#FFFFFF` | `pair:in/out` with `chat_outBubble`; `pair:rest/selected` with `chat_inBubbleSelected` |
| `chat_inBubbleSelected` | That bubble while multi-selected | `#ECF7FD` | `pair:rest/selected` with `chat_inBubble` |
| `chat_inBubbleShadow` | The drop shadow under the received bubble | `#1D3753` | `pair:in/out` with `chat_outBubbleShadow` |
| `chat_outBubble` | The sent-message bubble fill | `#EFFFDE` | `pair:in/out` with `chat_inBubble`; `pair` with `chat_outBubbleGradient` †; `pair:rest/selected` with `chat_outBubbleSelected` |
| `chat_outBubbleSelected` | That bubble while multi-selected | `#D9F7C5` | `pair:rest/selected` with `chat_outBubble` |
| `chat_outBubbleShadow` | The drop shadow under the sent bubble | `#1E750C` | `pair:in/out` with `chat_inBubbleShadow` |
| `chat_outBubbleGradient` † | The second stop of the outgoing bubble's gradient. **The generator drops this key entirely when the user turns the gradient option off** | — | `pair` with `chat_outBubble`; `pair:rest/selected` with `chat_outBubbleGradientSelectedOverlay` † |
| `chat_outBubbleGradientSelectedOverlay` † | The darkening laid over that gradient when selected | `#14000000` | `pair:rest/selected` with `chat_outBubbleGradient` † |
| `chat_inBubbleSelectedOverlay` † | The tint laid over a received bubble when it is multi-selected — the overlay form of the effect `chat_inBubbleSelected` achieves by swapping the fill | — | `pair:in/out` with `chat_outBubbleSelectedOverlay`; `mirror`: `chat_inBubbleSelected` |
| `chat_outBubbleSelectedOverlay` † | The same tint over a sent bubble | — | `pair:in/out` with `chat_inBubbleSelectedOverlay`; `mirror`: `chat_outBubbleSelected` |
| `chat_outBubbleGradient2` † | The third stop of the outgoing bubble gradient | — | `family`: bubble gradient, with `chat_outBubbleGradient` and `chat_outBubbleGradient3` |
| `chat_outBubbleGradient3` † | The fourth stop of it | — | `family`: bubble gradient |
| `chat_outBubbleGradientAnimated` † | The animated variant of that gradient, used when the bubble gradient moves as messages send | — | `family`: bubble gradient |
| `chat_messageTextIn` | Body text of a received message | `#000000` | `pair:in/out` with `chat_messageTextOut`; `fill→on-fill` with `chat_inBubble` |
| `chat_messageTextOut` | Body text of a sent message | `#000000` | `pair:in/out` with `chat_messageTextIn`; `fill→on-fill` with `chat_outBubble` |
| `chat_messageLinkIn` | A hyperlink inside a received message | `#2678B6` | `pair:in/out` with `chat_messageLinkOut` |
| `chat_messageLinkOut` | A hyperlink inside a sent message | `#2678B6` | `pair:in/out` with `chat_messageLinkIn` |
| `chat_inTimeText` | Timestamp in the corner of a received bubble | `#A1AAB3` | `pair:in/out` with `chat_outTimeText`; `pair:rest/selected` with `chat_inTimeSelectedText` |
| `chat_inTimeSelectedText` | That timestamp while selected | `#89B4C1` | `pair:rest/selected` with `chat_inTimeText` |
| `chat_outTimeText` | Timestamp in the corner of a sent bubble | `#70B15C` | `pair:in/out` with `chat_inTimeText`; `pair:rest/selected` with `chat_outTimeSelectedText` |
| `chat_outTimeSelectedText` | That timestamp while selected | `#70B15C` | `pair:rest/selected` with `chat_outTimeText` |
| `chat_inSentClock` | The pending-clock icon on a received message | `#A1AAB3` | `pair:in/out` with `chat_outSentClock`; `pair:rest/selected` with `chat_inSentClockSelected` |
| `chat_inSentClockSelected` | Its selected state | `#93BDCA` | `pair:rest/selected` with `chat_inSentClock` |
| `chat_outSentClock` | The pending-clock icon on a sent message | `#75BD5E` | `pair:in/out` with `chat_inSentClock`; `family`: delivery marks; `pair:rest/selected` with `chat_outSentClockSelected` |
| `chat_outSentClockSelected` | Its selected state | `#75BD5E` | `pair:rest/selected` with `chat_outSentClock` |
| `chat_outSentCheck` | The single ✓ (sent, not yet read) | `#5DB050` | `family`: delivery marks; `mirror`: `chats_sentCheck`; `pair:rest/selected` with `chat_outSentCheckSelected` |
| `chat_outSentCheckSelected` | Its selected state | `#5DB050` | `pair:rest/selected` with `chat_outSentCheck` |
| `chat_outSentCheckRead` | The double ✓✓ (read) | `#5DB050` | `family`: delivery marks; `mirror`: `chats_sentReadCheck`; `pair:rest/selected` with `chat_outSentCheckReadSelected` |
| `chat_outSentCheckReadSelected` | Its selected state | `#5DB050` | `pair:rest/selected` with `chat_outSentCheckRead` |
| `chat_inViews` | The eye + view count on a received channel post | `#A1AAB3` | `pair:in/out` with `chat_outViews`; `pair:rest/selected` with `chat_inViewsSelected` |
| `chat_inViewsSelected` | Its selected state | `#93BDCA` | `pair:rest/selected` with `chat_inViews` |
| `chat_outViews` | The eye + view count on a sent channel post | `#6EB257` | `pair:in/out` with `chat_inViews`; `pair:rest/selected` with `chat_outViewsSelected` |
| `chat_outViewsSelected` | Its selected state | `#6EB257` | `pair:rest/selected` with `chat_outViews` |
| `chat_inMenu` | The ⋮ button on a received bubble | `#B6BDC5` | `pair:in/out` with `chat_outMenu`; `pair:rest/selected` with `chat_inMenuSelected` |
| `chat_inMenuSelected` | Its selected state | `#98C1CE` | `pair:rest/selected` with `chat_inMenu` |
| `chat_outMenu` | The ⋮ button on a sent bubble | `#91CE7E` | `pair:in/out` with `chat_inMenu`; `pair:rest/selected` with `chat_outMenuSelected` |
| `chat_outMenuSelected` | Its selected state | `#91CE7E` | `pair:rest/selected` with `chat_outMenu` |
| `chat_stickerNameText` | The sender's name above a sticker (no bubble to sit on) | `#FFFFFF` | `family`: sticker messages — all five draw on the wallpaper, not a bubble |
| `chat_stickerReplyNameText` | The quoted sender's name in a reply attached to a sticker | `#FFFFFF` | `family`: sticker messages; `mirror`: `chat_inReplyNameText` |
| `chat_stickerReplyMessageText` | The quoted message text there | `#FFFFFF` | `family`: sticker messages; `mirror`: `chat_inReplyMessageText` |
| `chat_stickerReplyLine` | The vertical quote bar there | `#FFFFFF` | `family`: sticker messages; `mirror`: `chat_inReplyLine` |
| `chat_stickerViaBotNameText` | The "via @bot" label on a sticker | `#FFFFFF` | `family`: sticker messages; `mirror`: `chat_inViaBotNameText` |

### 5.3 Text selection

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_TextSelectionCursor` | The caret handle when selecting text in a received message | `#419FE8` | `pair:in/out` with `chat_outTextSelectionCursor` — note the base key has no `in` in its name |
| `chat_outTextSelectionCursor` | The same handle in a sent message | `#419FE8` | `pair:in/out` with `chat_TextSelectionCursor` |
| `chat_inTextSelectionHighlight` | The highlight behind selected text in a received message | `#5062A9E3` | `pair:in/out` with `chat_outTextSelectionHighlight` |
| `chat_outTextSelectionHighlight` | The same highlight in a sent message | `#2E3F9923` | `pair:in/out` with `chat_inTextSelectionHighlight` |
| `chat_textSelectBackground` | The generic text-selection highlight elsewhere in the chat | `#6662A9E3` | `mirror`: the two `*TextSelectionHighlight` keys |
| `chat_linkSelectBackground` | Highlight behind a pressed link in a received message | `#3362A9E3` | `pair:in/out` with `chat_outLinkSelectBackground` |
| `chat_outLinkSelectBackground` | The same in a sent message | `#3362A9E3` | `pair:in/out` with `chat_linkSelectBackground` |

### 5.4 Replies, forwards, and link previews

Every key here has an exact `in`/`out` twin. The `*ReplyLine` and `*PreviewLine`
keys draw the vertical bar down the left edge of a quote — the visual anchor for
the whole block, and the one worth getting right first.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inReplyLine` | The vertical quote bar on a reply in a received message | `#599FD8` | `pair:in/out` with `chat_outReplyLine` |
| `chat_outReplyLine` | The same bar in a sent message | `#6EB969` | `pair:in/out` with `chat_inReplyLine` |
| `chat_inReplyNameText` | The quoted sender's name, received | `#298ACF` | `pair:in/out` with `chat_outReplyNameText` |
| `chat_outReplyNameText` | The quoted sender's name, sent | `#55AB4F` | `pair:in/out` with `chat_inReplyNameText` |
| `chat_inReplyMessageText` | The quoted message text, received | `#000000` | `pair:in/out` with `chat_outReplyMessageText` |
| `chat_outReplyMessageText` | The quoted message text, sent | `#000000` | `pair:in/out` with `chat_inReplyMessageText` |
| `chat_inReplyMediaMessageText` | The quoted caption when the quoted message is media, received | `#A1AAB3` | `pair:in/out` with `chat_outReplyMediaMessageText`; `pair:rest/selected` with `chat_inReplyMediaMessageSelectedText` |
| `chat_inReplyMediaMessageSelectedText` | Its selected state | `#89B4C1` | `pair:rest/selected` with `chat_inReplyMediaMessageText` |
| `chat_outReplyMediaMessageText` | The same, sent | `#65B05B` | `pair:in/out` with `chat_inReplyMediaMessageText`; `pair:rest/selected` with `chat_outReplyMediaMessageSelectedText` |
| `chat_outReplyMediaMessageSelectedText` | Its selected state | `#65B05B` | `pair:rest/selected` with `chat_outReplyMediaMessageText` |
| `chat_inForwardedNameText` | The "Forwarded from X" header, received | `#3886C7` | `pair:in/out` with `chat_outForwardedNameText` |
| `chat_outForwardedNameText` | The same, sent | `#55AB4F` | `pair:in/out` with `chat_inForwardedNameText` |
| `chat_inViaBotNameText` | The "via @bot" attribution, received | `#298ACF` | `pair:in/out` with `chat_outViaBotNameText`; `mirror`: `chat_stickerViaBotNameText` |
| `chat_outViaBotNameText` | The same, sent | `#55AB4F` | `pair:in/out` with `chat_inViaBotNameText` |
| `chat_inSiteNameText` | The site title in a link preview, received ("Android Lead") | `#298ACF` | `pair:in/out` with `chat_outSiteNameText` |
| `chat_outSiteNameText` | The same, sent | `#55AB4F` | `pair:in/out` with `chat_inSiteNameText` |
| `chat_inPreviewLine` | The vertical bar down the left of a link preview, received | `#70B4E8` | `pair:in/out` with `chat_outPreviewLine`; `mirror`: `chat_inReplyLine` |
| `chat_outPreviewLine` | The same, sent | `#88C97B` | `pair:in/out` with `chat_inPreviewLine` |
| `chat_inPreviewInstantText` | The "Instant View" link under a preview, received | `#298ACF` | `pair:in/out` with `chat_outPreviewInstantText` |
| `chat_outPreviewInstantText` | The same link, sent | `#55AB4F` | `pair:in/out` with `chat_inPreviewInstantText` |
| `chat_inInstant` | The Instant View button on a received message | `#298ACF` | `pair:in/out` with `chat_outInstant`; `pair:rest/selected` with `chat_inInstantSelected` |
| `chat_inInstantSelected` | Its selected state | `#3079B5` | `pair:rest/selected` with `chat_inInstant` |
| `chat_outInstant` | The same button, sent | `#55AB4F` | `pair:in/out` with `chat_inInstant`; `pair:rest/selected` with `chat_outInstantSelected` |
| `chat_outInstantSelected` | Its selected state | `#489943` | `pair:rest/selected` with `chat_outInstant` |

### 5.5 Photos, video, and download loaders

Three parallel loader families: `chat_in*`/`chat_out*Loader` for files inside a
bubble, `chat_*LoaderPhoto` for the ring drawn directly on a photo, and
`chat_media*` for controls that sit over full-bleed media with no bubble behind
them.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inLoader` | The download progress ring on a received attachment | `#229AF0` | `pair:in/out` with `chat_outLoader`; `pair:rest/selected` with `chat_inLoaderSelected` |
| `chat_inLoaderSelected` | Its selected state | `#65ABE0` | `pair:rest/selected` with `chat_inLoader` |
| `chat_outLoader` | The same ring on a sent attachment | `#78C272` | `pair:in/out` with `chat_inLoader`; `pair:rest/selected` with `chat_outLoaderSelected` |
| `chat_outLoaderSelected` | Its selected state | `#6AB564` | `pair:rest/selected` with `chat_outLoader` |
| `chat_inLoaderPhoto` | The ring drawn straight onto a received photo | `#A2B8C8` | — |
| `chat_mediaLoaderPhoto` | The loader ring over full-bleed media (photo viewer, no bubble) | `#66000000` | `pair:rest/selected` with `chat_mediaLoaderPhotoSelected` |
| `chat_mediaLoaderPhotoSelected` | Its selected state | `#7F000000` | `pair:rest/selected` with `chat_mediaLoaderPhoto` |
| `chat_mediaLoaderPhotoIcon` | The arrow inside it | `#FFFFFF` | `fill→on-fill` with `chat_mediaLoaderPhoto`; `pair:rest/selected` with `chat_mediaLoaderPhotoIconSelected` |
| `chat_mediaLoaderPhotoIconSelected` | Its selected state | `#D9D9D9` | `pair:rest/selected` with `chat_mediaLoaderPhotoIcon` |
| `chat_inMediaIcon` | The play/file glyph on media inside a received bubble | `#FFFFFF` | `pair:in/out` with `chat_outMediaIcon`; `pair:rest/selected` with `chat_inMediaIconSelected` |
| `chat_inMediaIconSelected` | Its selected state | `#EFF8FE` | `pair:rest/selected` with `chat_inMediaIcon` |
| `chat_outMediaIcon` | The same glyph, sent | `#EFFFDE` | `pair:in/out` with `chat_inMediaIcon`; `pair:rest/selected` with `chat_outMediaIconSelected` |
| `chat_outMediaIconSelected` | Its selected state | `#E1F8CF` | `pair:rest/selected` with `chat_outMediaIcon` |
| `chat_mediaProgress` | The progress arc over full-bleed media | `#FFFFFF` | `family`: media overlay |
| `chat_mediaTimeBackground` | The dark rounded chip behind the timestamp on a photo | `#66000000` | `fill→on-fill`: `chat_mediaTimeText` |
| `chat_mediaTimeText` | The timestamp inside that chip | `#FFFFFF` | `fill→on-fill` with `chat_mediaTimeBackground` |
| `chat_mediaInfoText` | Size/duration text drawn over media | `#FFFFFF` | `family`: media overlay |
| `chat_mediaViews` | The view count drawn over media | `#FFFFFF` | `mirror`: `chat_inViews` |
| `chat_mediaSentCheck` | The delivery ✓ drawn over media | `#FFFFFF` | `mirror`: `chat_outSentCheck` |
| `chat_mediaSentClock` | The pending clock drawn over media | `#FFFFFF` | `mirror`: `chat_outSentClock` |
| `chat_mediaMenu` | The ⋮ button drawn over media | `#FFFFFF` | `mirror`: `chat_outMenu` |

### 5.6 Audio files and voice messages

`Audio` = a music file with a title and performer; `Voice` = a recorded voice
message with a waveform. They share the seekbar naming but are separate widgets.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inAudioTitleText` | Track title on a received audio file | `#4E9AD4` | `pair:in/out` with `chat_outAudioTitleText` |
| `chat_outAudioTitleText` | The same, sent | `#55AB4F` | `pair:in/out` with `chat_inAudioTitleText` |
| `chat_inAudioPerfomerText` | Artist line under it, received (Telegram's own spelling of "performer") | `#2F3438` | `pair:in/out` with `chat_outAudioPerfomerText`; `pair:rest/selected` with `chat_inAudioPerfomerSelectedText` |
| `chat_inAudioPerfomerSelectedText` | Its selected state | `#2F3438` | `pair:rest/selected` with `chat_inAudioPerfomerText` |
| `chat_outAudioPerfomerText` | The same artist line, sent | `#354234` | `pair:in/out` with `chat_inAudioPerfomerText`; `pair:rest/selected` with `chat_outAudioPerfomerSelectedText` |
| `chat_outAudioPerfomerSelectedText` | Its selected state | `#354234` | `pair:rest/selected` with `chat_outAudioPerfomerText` |
| `chat_inAudioDurationText` | Elapsed/total duration, received | `#A1AAB3` | `pair:in/out` with `chat_outAudioDurationText`; `pair:rest/selected` with `chat_inAudioDurationSelectedText` |
| `chat_inAudioDurationSelectedText` | Its selected state | `#89B4C1` | `pair:rest/selected` with `chat_inAudioDurationText` |
| `chat_outAudioDurationText` | The same, sent | `#65B05B` | `pair:in/out` with `chat_inAudioDurationText`; `pair:rest/selected` with `chat_outAudioDurationSelectedText` |
| `chat_outAudioDurationSelectedText` | Its selected state | `#65B05B` | `pair:rest/selected` with `chat_outAudioDurationText` |
| `chat_inAudioSeekbar` | The unplayed track of the audio scrubber, received | `#E4EAF0` | `pair:in/out` with `chat_outAudioSeekbar`; `pair` with `chat_inAudioSeekbarFill`; `pair:on/off` with `chat_inAudioSeekbarFill`; `pair:rest/selected` with `chat_inAudioSeekbarSelected` |
| `chat_inAudioSeekbarFill` | The played portion of it | `#229AF0` | `pair:on/off` with `chat_inAudioSeekbar` |
| `chat_inAudioSeekbarSelected` | The track while the message is selected | `#BCDEE8` | `pair:rest/selected` with `chat_inAudioSeekbar` |
| `chat_inAudioCacheSeekbar` | The buffered-ahead portion of it | `#3FE4EAF0` | `family`: received audio scrubber |
| `chat_outAudioSeekbar` | The unplayed track, sent | `#BBE3AC` | `pair:in/out` with `chat_inAudioSeekbar`; `pair:on/off` with `chat_outAudioSeekbarFill`; `pair:rest/selected` with `chat_outAudioSeekbarSelected` |
| `chat_outAudioSeekbarFill` | The played portion, sent | `#78C272` | `pair:on/off` with `chat_outAudioSeekbar` |
| `chat_outAudioSeekbarSelected` | Selected state of that track | `#A9DD96` | `pair:rest/selected` with `chat_outAudioSeekbar` |
| `chat_outAudioCacheSeekbar` | The buffered portion, sent | `#3FBBE3AC` | `family`: sent audio scrubber |
| `chat_inAudioProgress` | The play/pause disc on a received audio file | `#FFFFFF` | `pair:in/out` with `chat_outAudioProgress`; `pair:rest/selected` with `chat_inAudioSelectedProgress` |
| `chat_inAudioSelectedProgress` | Its selected state | `#EFF8FE` | `pair:rest/selected` with `chat_inAudioProgress` |
| `chat_outAudioProgress` | The same disc, sent | `#EFFFDE` | `pair:in/out` with `chat_inAudioProgress`; `pair:rest/selected` with `chat_outAudioSelectedProgress` |
| `chat_outAudioSelectedProgress` | Its selected state | `#E1F8CF` | `pair:rest/selected` with `chat_outAudioProgress` |
| `chat_inVoiceSeekbar` | The unplayed part of a received voice waveform | `#DEE5EB` | `pair:in/out` with `chat_outVoiceSeekbar`; `pair:on/off` with `chat_inVoiceSeekbarFill`; `pair:rest/selected` with `chat_inVoiceSeekbarSelected` |
| `chat_inVoiceSeekbarFill` | The played part of it | `#229AF0` | `pair:on/off` with `chat_inVoiceSeekbar` |
| `chat_inVoiceSeekbarSelected` | Its selected state | `#BCDEE8` | `pair:rest/selected` with `chat_inVoiceSeekbar` |
| `chat_outVoiceSeekbar` | The unplayed part of a sent voice waveform | `#BBE3AC` | `pair:in/out` with `chat_inVoiceSeekbar`; `pair:on/off` with `chat_outVoiceSeekbarFill`; `pair:rest/selected` with `chat_outVoiceSeekbarSelected` |
| `chat_outVoiceSeekbarFill` | The played part of it | `#78C272` | `pair:on/off` with `chat_outVoiceSeekbar` |
| `chat_outVoiceSeekbarSelected` | Its selected state | `#A9DD96` | `pair:rest/selected` with `chat_outVoiceSeekbar` |

### 5.7 Document and file messages

The `.apk` and `.attheme` rows in the screenshots.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inFileBackground` | The round disc behind a received file's icon | `#EBF0F5` | `pair:in/out` with `chat_outFileBackground`; `pair:rest/selected` with `chat_inFileBackgroundSelected` |
| `chat_inFileBackgroundSelected` | Its selected state | `#CBEAF6` | `pair:rest/selected` with `chat_inFileBackground` |
| `chat_outFileBackground` | The same disc, sent | `#DAF5C3` | `pair:in/out` with `chat_inFileBackground`; `pair:rest/selected` with `chat_outFileBackgroundSelected` |
| `chat_outFileBackgroundSelected` | Its selected state | `#C5ECA7` | `pair:rest/selected` with `chat_outFileBackground` |
| `chat_inFileNameText` | The file name, received ("disco-87-stag.apk") | `#298ACF` | `pair:in/out` with `chat_outFileNameText` |
| `chat_outFileNameText` | The same, sent | `#55AB4F` | `pair:in/out` with `chat_inFileNameText` |
| `chat_inFileInfoText` | The size/type line under it ("76.5 MB APK"), received | `#A1AAB3` | `pair:in/out` with `chat_outFileInfoText`. In the Default style this one stays a muted gray rather than inverting; `pair:rest/selected` with `chat_inFileInfoSelectedText` |
| `chat_inFileInfoSelectedText` | Its selected state | `#89B4C1` | `pair:rest/selected` with `chat_inFileInfoText` |
| `chat_outFileInfoText` | The same line, sent | `#65B05B` | `pair:in/out` with `chat_inFileInfoText`; `pair:rest/selected` with `chat_outFileInfoSelectedText` |
| `chat_outFileInfoSelectedText` | Its selected state | `#65B05B` | `pair:rest/selected` with `chat_outFileInfoText` |
| `chat_inFileProgress` | The download progress track on a received file | `#EBF0F5` | `pair:in/out` with `chat_outFileProgress`; `pair:rest/selected` with `chat_inFileProgressSelected` |
| `chat_inFileProgressSelected` | Its selected state | `#CBEAF6` | `pair:rest/selected` with `chat_inFileProgress` |
| `chat_outFileProgress` | The same track, sent | `#DAF5C3` | `pair:in/out` with `chat_inFileProgress`; `pair:rest/selected` with `chat_outFileProgressSelected` |
| `chat_outFileProgressSelected` | Its selected state | `#C5ECA7` | `pair:rest/selected` with `chat_outFileProgress` |

### 5.8 Cards inside a bubble — contact, venue, location, poll, call, reactions

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inContactBackground` | The disc behind the avatar on a received shared-contact card | `#229AF0` | `pair:in/out` with `chat_outContactBackground`; `fill→on-fill`: `chat_inContactIcon` |
| `chat_inContactIcon` | The person glyph on that disc | `#FFFFFF` | `fill→on-fill` with `chat_inContactBackground` |
| `chat_inContactNameText` | The contact's name, received | `#4E9AD4` | `pair:in/out` with `chat_outContactNameText` |
| `chat_inContactPhoneText` | The phone number under it, received | `#2F3438` | `pair:in/out` with `chat_outContactPhoneText`; `pair:rest/selected` with `chat_inContactPhoneSelectedText` |
| `chat_inContactPhoneSelectedText` | Its selected state | `#2F3438` | `pair:rest/selected` with `chat_inContactPhoneText` |
| `chat_outContactBackground` | The same disc, sent | `#78C272` | `pair:in/out` with `chat_inContactBackground` |
| `chat_outContactIcon` | The glyph on it | `#EFFFDE` | `fill→on-fill` with `chat_outContactBackground` |
| `chat_outContactNameText` | The contact's name, sent | `#55AB4F` | `pair:in/out` with `chat_inContactNameText` |
| `chat_outContactPhoneText` | The phone number, sent | `#354234` | `pair:in/out` with `chat_inContactPhoneText`; `pair:rest/selected` with `chat_outContactPhoneSelectedText` |
| `chat_outContactPhoneSelectedText` | Its selected state | `#354234` | `pair:rest/selected` with `chat_outContactPhoneText` |
| `chat_inVenueInfoText` | The address line on a received venue/place card | `#A1AAB3` | `pair:in/out` with `chat_outVenueInfoText`; `pair:rest/selected` with `chat_inVenueInfoSelectedText` |
| `chat_inVenueInfoSelectedText` | Its selected state | `#89B4C1` | `pair:rest/selected` with `chat_inVenueInfoText` |
| `chat_outVenueInfoText` | The same address line, sent | `#65B05B` | `pair:in/out` with `chat_inVenueInfoText`; `pair:rest/selected` with `chat_outVenueInfoSelectedText` |
| `chat_outVenueInfoSelectedText` | Its selected state | `#65B05B` | `pair:rest/selected` with `chat_outVenueInfoText` |
| `chat_inLocationBackground` | The map placeholder behind a received location card | `#EBF0F5` | — |
| `chat_inLocationIcon` | The pin glyph on it | `#A2B5C7` | `fill→on-fill` with `chat_inLocationBackground`; `pair:in/out` with `chat_outLocationIcon` |
| `chat_outLocationIcon` | The pin glyph, sent | `#87BF78` | `pair:in/out` with `chat_inLocationIcon` |
| `chat_inBubbleLocationPlaceholder` † | The tile behind a map thumbnail inside a received bubble, before the map loads | `#1E506373` | `pair:in/out` with `chat_outBubbleLocationPlaceholder`; `mirror`: `chat_inLocationBackground` |
| `chat_outBubbleLocationPlaceholder` † | The same tile in a sent bubble | `#1E307311` | `pair:in/out` with `chat_inBubbleLocationPlaceholder` |
| `chat_inPollCorrectAnswer` | The green check on the right answer in a received quiz | `#60C255` | `pair:in/out` with `chat_outPollCorrectAnswer`; `pair` with `chat_inPollWrongAnswer` |
| `chat_inPollWrongAnswer` | The red cross on a wrong answer, received | `#EB6060` | `pair:in/out` with `chat_outPollWrongAnswer` |
| `chat_outPollCorrectAnswer` | The same green check, sent | `#60C255` | `pair:in/out` with `chat_inPollCorrectAnswer` |
| `chat_outPollWrongAnswer` | The same red cross, sent | `#EB6060` | `pair:in/out` with `chat_inPollWrongAnswer` |
| `chat_inDownCall` | The incoming-call arrow on a received call log entry | `#00C853` | `mirror`: `calls_callReceivedGreenIcon` |
| `chat_outUpCall` | The outgoing-call arrow on a sent call log entry | `#00C853` | — |
| `chat_inReactionButtonBackground` † | The reaction chip's fill under a received message | `#229AF0` | `pair:in/out` with `chat_outReactionButtonBackground`; `fill→on-fill`: `chat_inReactionButtonText` † |
| `chat_inReactionButtonText` † | The count on that chip, received | `#298ACF` | `pair:rest/selected` with `chat_inReactionButtonTextSelected` †; `pair:in/out` with `chat_outReactionButtonText` † |
| `chat_inReactionButtonTextSelected` † | That count when you are one of the reactors | `#FFFFFF` | `pair:rest/selected` with `chat_inReactionButtonText` † |
| `chat_outReactionButtonBackground` | The reaction chip's fill under a sent message | `#78C272` | `pair:in/out` with `chat_inReactionButtonBackground` † |
| `chat_outReactionButtonText` † | The count on that chip, sent | `#55AB4F` | `pair:in/out` with `chat_inReactionButtonText` †; `pair:rest/selected` with `chat_outReactionButtonTextSelected` † |
| `chat_outReactionButtonTextSelected` † | That count when you reacted | `#FFFFFF` | `pair:rest/selected` with `chat_outReactionButtonText` † |

### 5.9 Top panels — pinned message, reply, and edit

Two stacked strips at the top of the chat. `chat_topPanel*` is the pinned-message
bar; `chat_replyPanel*` is the strip above the composer while replying or editing.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_topPanelBackground` | The pinned-message bar's background | `#FFFFFF` | `fill→on-fill`: `chat_topPanelTitle`, `chat_topPanelMessage` |
| `chat_topPanelTitle` | Its title ("Pinned Message") | `#298ACF` | `fill→on-fill` with `chat_topPanelBackground`; `mirror`: `chat_replyPanelName` |
| `chat_topPanelMessage` | The pinned message's preview text under it | `#767E7C` | — |
| `chat_topPanelLine` | The vertical accent bar at its left edge | `#3FA8EF` | `mirror`: `chat_replyPanelLine`, `chat_inReplyLine` |
| `chat_topPanelClose` | The ✕ that dismisses it | `#818786` | `mirror`: `chat_replyPanelClose` |
| `chat_replyPanelLine` | The vertical bar in the reply/edit strip | `#E8E8E8` | `mirror`: `chat_topPanelLine` |
| `chat_replyPanelName` | The name of the person being replied to | `#298ACF` | `mirror`: `chat_topPanelTitle` |
| `chat_replyPanelIcons` | The reply/edit glyph in that strip | `#229AF0` | `family`: reply panel |
| `chat_replyPanelClose` | The ✕ that cancels the reply | `#8E959B` | `mirror`: `chat_topPanelClose` |

### 5.10 Composer and voice recording

The input row at the bottom, plus everything the voice-message recorder swaps in
over it. Note the three `key_chat_messagePanelVoiceLock*` keys keep Telegram's own
`key_` prefix — that is their real spelling, not a mistake.

Confirmed on-device: the mic/camera glyph at the end of the composer is
**not** part of `chat_messagePanelIcons` (that key only tints the attach
and emoji/sticker glyphs elsewhere in the same bar). It's its own filled
circle — `chat_messagePanelVoiceBackground` is the circle's fill,
`chat_messagePanelVoicePressed` is the glyph drawn on top of it. Despite
the "…Pressed" name suggesting an interaction-state-only key, changing
`chat_messagePanelVoicePressed` visibly recolors that glyph in its normal
resting state too, not only while actively held to record.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_messagePanelBackground` | The composer bar's background | `#FFFFFF` | `fill→on-fill`: `chat_messagePanelText`, `chat_messagePanelIcons` |
| `chat_messagePanelText` | Text you have typed | `#000000` | `pair` with `chat_messagePanelHint` |
| `chat_messagePanelHint` | The "Message" / "Broadcast" placeholder | `#858A84` | `pair` with `chat_messagePanelText` |
| `chat_messagePanelCursor` | The caret in the input | `#54A1DB` | `family`: composer; `mirror`: `groupcreate_cursor` |
| `chat_messagePanelIcons` | Attach, emoji, and sticker glyphs in the bar | `#8E959B` | `fill→on-fill` with `chat_messagePanelBackground` |
| `chat_messagePanelSend` | The send arrow | `#229AF0` | — |
| `chat_messagePanelShadow` | The shadow line above the composer | `#000000` | `mirror`: `chat_emojiPanelShadowLine` |
| `chat_messagePanelCancelInlineBot` | The ✕ that clears an inline-bot query | `#ADADAD` | `family`: composer |
| `chat_messagePanelVoiceBackground` | The mic button's fill | `#229AF0` | `fill→on-fill`: `chat_messagePanelVoicePressed`; `family`: voice recording; `pair:on/off` with `chat_messagePanelVoicePressed` |
| `chat_messagePanelVoicePressed` | The mic button while held | `#FFFFFF` | `pair:on/off` with `chat_messagePanelVoiceBackground` |
| `chat_messagePanelVoiceDuration` | The running duration while holding to record | `#FFFFFF` | `family`: voice recording; `mirror`: `chat_recordTime` |
| `chat_messagePanelVoiceDelete` | The trash glyph you slide toward to cancel | `#737373` | `family`: voice recording |
| `key_chat_messagePanelVoiceLock` | The padlock glyph for hands-free recording | `#A4A4A4` | `family`: voice lock — all three keep the `key_` prefix |
| `key_chat_messagePanelVoiceLockBackground` | The capsule behind that padlock | `#FFFFFF` | `fill→on-fill` with `key_chat_messagePanelVoiceLock` |
| `key_chat_messagePanelVoiceLockShadow` | The shadow under that capsule | `#000000` | `family`: voice lock |
| `chat_recordVoiceCancel` | The "Slide to cancel" label while recording | `#3A95D4` | `family`: voice recording |
| `chat_recordedVoiceBackground` | The waveform of a finished-but-unsent voice message | `#5DADE8` | `pair` with `chat_recordedVoiceDarkerBackground` † |
| `chat_recordedVoiceDarkerBackground` † | The darker step of that waveform | `#1F89DB` | `pair` with `chat_recordedVoiceBackground` |
| `chat_recordedVoiceDot` | The pulsing red dot while recording | `#DA564D` | `family`: voice recording |
| `chat_recordedVoicePlayPause` | The play/pause control on that preview | `#FFFFFF` | — |
| `chat_recordedVoiceProgress` | The unplayed part of that preview's scrubber | `#B1DEFF` | `pair:on/off` with `chat_recordedVoiceProgressInner` |
| `chat_recordedVoiceProgressInner` | The played part of it | `#FFFFFF` | `pair:on/off` with `chat_recordedVoiceProgress` |

### 5.11 Attach sheet

The bottom sheet behind the paperclip, with its Gallery / Wallet / File /
Location / Article / Poll / Checklist / Contact / Music tab strip. Each source
type used to get its own background + icon + text triple; current Telegram keeps
only the backgrounds and a single shared `chat_attachIcon`, so most of the
per-type `Icon`/`Text` keys were dropped in the pruning — `chat_attachContactText`
is the only per-type label that survives.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_attachActiveTab` | The selected tab in the sheet's bottom strip | `#33A7F5` | `pair:on/off` with `chat_attachUnactiveTab` |
| `chat_attachUnactiveTab` | An unselected tab there | `#92999E` | `pair:on/off` with `chat_attachActiveTab` |
| `chat_attachIcon` | The glyph on every attach-source disc — the live replacement for the per-type `*Icon` keys | `#FFFFFF` | `fill→on-fill` with all six `chat_attach*Background` keys |
| `chat_attachGalleryBackground` | The Gallery source disc | `#459DF5` | `fill→on-fill`: `chat_attachIcon`; `family`: attach sources |
| `chat_attachAudioBackground` | The Music source disc | `#EB6060` | `family`: attach sources |
| `chat_attachContactBackground` | The Contact source disc | `#F2C04B` | `family`: attach sources |
| `chat_attachContactText` | Label under the Contact source — the one per-type label still live | `#DFA000` | `fill→on-fill` with `chat_attachContactBackground` |
| `chat_attachLocationBackground` | The Location source disc | `#60C255` | `family`: attach sources |
| `chat_attachPollBackground` | The Poll source disc | `#F2C04B` | `family`: attach sources |
| `chat_attachCheckBoxBackground` | The numbered selection badge on a picked photo | `#229AF0` | `fill→on-fill`: `chat_attachCheckBoxCheck` |
| `chat_attachCheckBoxCheck` | The check/number inside that badge | `#FFFFFF` | `fill→on-fill` with `chat_attachCheckBoxBackground` |
| `chat_attachPhotoBackground` | The placeholder tile behind a photo thumbnail while it loads (`PhotoPickerPhotoCell`) | `#0C000000` | `mirror`: `sharedMedia_photoPlaceholder` |
| `chat_attachEmptyImage` | The illustration shown when a source has nothing to offer | `#CCCCCC` | `mirror`: `dialogEmptyImage` † |
| `chat_attachPermissionImage` | The illustration prompting for gallery/contacts permission | `#333333` | `family`: permission prompt |
| `chat_attachPermissionText` | The explanatory text there | `#6F777A` | `family`: permission prompt |
| `chat_attachPermissionMark` | The warning mark on that prompt | `#E25050` | `family`: permission prompt |

### 5.12 Emoji, sticker, and GIF panel

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_emojiPanelBackground` | The panel body behind the emoji/sticker/GIF grid | `#F0F2F5` | `fill→on-fill`: everything else in this table |
| `chat_emojiPanelShadowLine` | The shadow separating the panel from the composer | `#12000000` | `mirror`: `chat_messagePanelShadow` |
| `chat_emojiPanelIcon` | A category icon in the panel's tab strip | `#9DA4AB` | `pair:on/off` with `chat_emojiPanelIconSelected` |
| `chat_emojiPanelIconSelected` | The selected category icon | `#5E6976` | `pair:on/off` with `chat_emojiPanelIcon` |
| `chat_emojiBottomPanelIcon` | Icons in the panel's bottom row (Emoji / GIFs / Stickers) | `#8C9197` | `family`: panel chrome |
| `chat_emojiPanelBackspace` | The backspace key at the panel's edge | `#8C9197` | `family`: panel chrome |
| `chat_emojiPanelEmptyText` | "No recent stickers" style empty text | `#949BA1` | `mirror`: `emptyListPlaceholder` |
| `chat_emojiPanelNewTrending` | The badge marking newly trending sticker packs | `#4DA6EA` | `family`: trending, with `featuredStickers_unread` |
| `chat_emojiPanelStickerPackSelector` | The pill behind the selected pack in the tab strip | `#E2E5E7` | `pair` with `chat_emojiPanelStickerPackSelectorLine` |
| `chat_emojiPanelStickerPackSelectorLine` | The underline marking that pack | `#56ABF0` | `pair` with `chat_emojiPanelStickerPackSelector` |
| `chat_emojiPanelStickerSetName` | A sticker pack's name above its grid | `#828B94` | `pair` with `chat_emojiPanelStickerSetNameHighlight`; `pair:rest/selected` with `chat_emojiPanelStickerSetNameHighlight` |
| `chat_emojiPanelStickerSetNameHighlight` | The matched substring of that name while searching | `#278DDB` | `pair:rest/selected` with `chat_emojiPanelStickerSetName` |
| `chat_emojiPanelStickerSetNameIcon` | The icon beside that name | `#B1B6BC` | `fill→on-fill` with `chat_emojiPanelBackground` |
| `chat_emojiPanelTrendingTitle` | A trending pack's title in the Trending tab | `#222222` | `pair` with `chat_emojiPanelTrendingDescription` |
| `chat_emojiPanelTrendingDescription` | The "N stickers" line under it | `#8A8A8A` | `pair` with `chat_emojiPanelTrendingTitle` |
| `chat_emojiSearchBackground` | The search field inside the panel | `#E5E9EE` | `fill→on-fill`: `chat_emojiSearchIcon`; `mirror`: `dialogSearchBackground` |
| `chat_emojiSearchIcon` | The magnifier in that field | `#94A1AF` | `fill→on-fill` with `chat_emojiSearchBackground` |

### 5.13 Bot keyboards and inline bots

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_botKeyboardButtonBackground` | A custom bot keyboard button's fill | `#66BCC3C8` | `pair:on/off` with `chat_botKeyboardButtonBackgroundPressed`; `fill→on-fill`: `chat_botKeyboardButtonText` |
| `chat_botKeyboardButtonBackgroundPressed` | That button while pressed | `#66808C94` | `pair:on/off` with `chat_botKeyboardButtonBackground` |
| `chat_botKeyboardButtonText` | The label on it | `#F0444444` | `fill→on-fill` with `chat_botKeyboardButtonBackground` |
| `chat_botButtonText` | The label on an inline button attached under a message | `#FFFFFF` | `mirror`: `chat_botKeyboardButtonText` |
| `botKeyboard_button_primary` † | The accent-colored variant of a bot button | `#229AF0` | `family`: bot button intents |
| `botKeyboard_button_success` † | The green/positive variant | `#40B135` | `family`: bot button intents |
| `botKeyboard_button_danger` † | The red/destructive variant | `#DB4646` | `family`: bot button intents |
| `bot_loadingIcon` † | The placeholder shown while a bot's mini-app loads | `#F2F2F2` | `mirror`: `dialog_inlineProgress` |

### 5.14 Quotes, code blocks, tables, and article previews

Rich content Telegram added inside message bubbles: block quotes, syntax-highlighted
code, tables, and the collapsible blocks of an Instant View preview. Every element
here is an `in`/`out` pair, and the `code_*` syntax colors at the end are shared —
they are drawn on both sides, so they must read against the incoming *and* the
outgoing bubble.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_inQuote` † | The vertical bar and accent of a block quote in a received message | `#459BD8` | `pair:in/out` with `chat_outQuote`; `mirror`: `chat_inReplyLine` |
| `chat_outQuote` † | The same in a sent message | `#6AB860` | `pair:in/out` with `chat_inQuote`; `pair` with `chat_outReplyLine2` |
| `chat_outReplyLine2` † | The second color of a sent message's reply bar, for the two-tone quote style | `#40A920` | `pair` with `chat_outQuote`; `mirror`: `chat_outReplyLine` |
| `chat_inDivider` † | A horizontal rule inside a received bubble | `#E5E5E5` | `pair:in/out` with `chat_outDivider`; `mirror`: `divider` |
| `chat_outDivider` † | The same in a sent bubble | `#336EB969` | `pair:in/out` with `chat_inDivider` |
| `chat_inCodeBackground` † | The panel behind an inline or block code snippet, received | `#6F889E` | `pair:in/out` with `chat_outCodeBackground`; `fill→on-fill`: the `code_*` set below |
| `chat_outCodeBackground` † | The same panel, sent | `#123C7503` | `pair:in/out` with `chat_inCodeBackground` |
| `chat_inTableBackground` † | A table cell's background inside a received bubble | `#F7F7F7` | `pair:in/out` with `chat_outTableBackground`; `pair` with `chat_inTableBorder`; `mirror`: `table_background` |
| `chat_outTableBackground` † | The same, sent | `#DEF0CC` | `pair:in/out` with `chat_inTableBackground` |
| `chat_inTableBorder` † | That table's grid lines, received | `#E0E0E0` | `pair:in/out` with `chat_outTableBorder`; `pair` with `chat_inTableBackground`; `mirror`: `table_border` |
| `chat_outTableBorder` † | The same, sent | `#C9DBB6` | `pair:in/out` with `chat_inTableBorder` |
| `chat_inArticleCodeBackground` † | The panel behind a code block inside an Instant View article preview, received | `#F1F5F9` | `pair:in/out` with `chat_outArticleCodeBackground`; `mirror`: `chat_inCodeBackground` |
| `chat_outArticleCodeBackground` † | The same, sent | — | `pair:in/out` with `chat_inArticleCodeBackground` |
| `chat_inArticleCodeScrollbar` † | The scrollbar thumb on that code block, received | `#C5CDD5` | `pair:in/out` with `chat_outArticleCodeScrollbar`; `pair` with `chat_inArticleCodeScrollbarBackground` |
| `chat_outArticleCodeScrollbar` † | The same thumb, sent | `#CCD5C2` | `pair:in/out` with `chat_inArticleCodeScrollbar` |
| `chat_inArticleCodeScrollbarBackground` † | That scrollbar's track, received | `#E1E6EB` | `pair` with `chat_inArticleCodeScrollbar`; `pair:in/out` with `chat_outArticleCodeScrollbarBackground` |
| `chat_outArticleCodeScrollbarBackground` † | That track, sent | `#E4EBDC` | `pair:in/out` with `chat_inArticleCodeScrollbarBackground` |
| `chat_inArticleDetailsArrow` † | The expand/collapse chevron on a details block in an article preview, received | `#9EA4A8` | `pair:in/out` with `chat_outArticleDetailsArrow`; `pair` with `chat_inArticleDetailsLine` |
| `chat_outArticleDetailsArrow` † | The same chevron, sent | `#84A37B` | `pair:in/out` with `chat_inArticleDetailsArrow` |
| `chat_inArticleDetailsLine` † | The rule marking that details block, received | `#D8D8D8` | `pair` with `chat_inArticleDetailsArrow`; `pair:in/out` with `chat_outArticleDetailsLine` |
| `chat_outArticleDetailsLine` † | That rule, sent | `#CBD8C5` | `pair:in/out` with `chat_inArticleDetailsLine` |
| `code_keyword` † | Language keywords in a highlighted code block | `#E05356` | `family`: syntax colors — shared by incoming and outgoing bubbles; `mirror`: `color_red` |
| `code_string` † | String literals there | `#37C123` | `family`: syntax colors; `mirror`: `color_green` |
| `code_number` † | Numeric literals there | `#327FE5` | `family`: syntax colors; `mirror`: `color_blue` |
| `code_function` † | Function names there | `#F28C39` | `family`: syntax colors; `mirror`: `color_orange` |
| `code_constant` † | Constants there | `#7F79F3` | `family`: syntax colors; `mirror`: `color_purple` |
| `code_operator` † | Operators and punctuation there | `#4DBBFF` | `family`: syntax colors; `mirror`: `color_lightblue` |
| `code_comment` † | Comments there — the one that must stay recessive | `#80000000` | `family`: syntax colors |

### 5.15 Instant View and mini-app buttons in a bubble

The buttons Telegram draws inside a message for a bot mini-app or Instant View
article. The naming is fully systematic: **intent** (`Default`, `DefaultInline`,
`Primary`, `Success`, `Danger`) × **side** (`In`, `Out`) × **part** (fill,
`Pressed`, `Text`). Thirty keys, no exceptions — so any one of them tells you the
other twenty-nine exist. Telegram ships every `Pressed` value identical to its
resting fill, which is why they share a role here.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chat_msgIvButtonDefaultIn` † | A neutral button's fill in a received message | `#EDEDED` | `pair:in/out` with `chat_msgIvButtonDefaultOut`; `fill→on-fill`: `chat_msgIvButtonDefaultInText`; `pair:on/off` with `chat_msgIvButtonDefaultInPressed` † |
| `chat_msgIvButtonDefaultInPressed` † | That button while pressed | `#EDEDED` | `pair:on/off` with `chat_msgIvButtonDefaultIn` |
| `chat_msgIvButtonDefaultInText` † | Its label | `#1A1D21` | `fill→on-fill` with `chat_msgIvButtonDefaultIn` |
| `chat_msgIvButtonDefaultOut` † | The same neutral button in a sent message | `#DCF4CB` | `pair:in/out` with `chat_msgIvButtonDefaultIn`; `pair:on/off` with `chat_msgIvButtonDefaultOutPressed` † |
| `chat_msgIvButtonDefaultOutPressed` † | Its pressed state | `#DCF4CB` | `pair:on/off` with `chat_msgIvButtonDefaultOut` |
| `chat_msgIvButtonDefaultOutText` † | Its label | `#53AB49` | `fill→on-fill` with `chat_msgIvButtonDefaultOut` |
| `chat_msgIvButtonDefaultInlineIn` † | The inline (borderless) neutral variant, received | `#E9F3FA` | `pair:in/out` with `chat_msgIvButtonDefaultInlineOut`; `mirror`: `chat_msgIvButtonDefaultIn`; `pair:on/off` with `chat_msgIvButtonDefaultInlineInPressed` † |
| `chat_msgIvButtonDefaultInlineInPressed` † | Its pressed state | `#E9F3FA` | `pair:on/off` with `chat_msgIvButtonDefaultInlineIn` |
| `chat_msgIvButtonDefaultInlineInText` † | Its label — accent-colored, unlike the plain Default variant | `#298ACF` | `fill→on-fill` with `chat_msgIvButtonDefaultInlineIn`; `mirror`: `chat_inInstant` |
| `chat_msgIvButtonDefaultInlineOut` † | The inline neutral variant, sent | `#DCF4CB` | `pair:in/out` with `chat_msgIvButtonDefaultInlineIn`; `pair:on/off` with `chat_msgIvButtonDefaultInlineOutPressed` † |
| `chat_msgIvButtonDefaultInlineOutPressed` † | Its pressed state | `#DCF4CB` | `pair:on/off` with `chat_msgIvButtonDefaultInlineOut` |
| `chat_msgIvButtonDefaultInlineOutText` † | Its label | `#53AB49` | `fill→on-fill` with `chat_msgIvButtonDefaultInlineOut`; `mirror`: `chat_outInstant` |
| `chat_msgIvButtonPrimaryIn` † | The filled primary button, received | `#229AF0` | `pair:in/out` with `chat_msgIvButtonPrimaryOut`; `fill→on-fill`: `chat_msgIvButtonPrimaryInText`; `pair:on/off` with `chat_msgIvButtonPrimaryInPressed` † |
| `chat_msgIvButtonPrimaryInPressed` † | Its pressed state | `#229AF0` | `pair:on/off` with `chat_msgIvButtonPrimaryIn` |
| `chat_msgIvButtonPrimaryInText` † | Its label — drawn on a saturated fill, so it inverts | `#F8FCFF` | `fill→on-fill` with `chat_msgIvButtonPrimaryIn` |
| `chat_msgIvButtonPrimaryOut` † | The filled primary button, sent | `#53AB49` | `pair:in/out` with `chat_msgIvButtonPrimaryIn`; `pair:on/off` with `chat_msgIvButtonPrimaryOutPressed` † |
| `chat_msgIvButtonPrimaryOutPressed` † | Its pressed state | `#53AB49` | `pair:on/off` with `chat_msgIvButtonPrimaryOut` |
| `chat_msgIvButtonPrimaryOutText` † | Its label | `#FAFCF9` | `fill→on-fill` with `chat_msgIvButtonPrimaryOut` |
| `chat_msgIvButtonSuccessIn` † | The positive-intent button's fill, received | `#E8F4E4` | `pair:in/out` with `chat_msgIvButtonSuccessOut`; `family`: button intents; `pair:on/off` with `chat_msgIvButtonSuccessInPressed` † |
| `chat_msgIvButtonSuccessInPressed` † | Its pressed state | `#E8F4E4` | `pair:on/off` with `chat_msgIvButtonSuccessIn` |
| `chat_msgIvButtonSuccessInText` † | Its label — the part that carries the "success" meaning | `#40A920` | `fill→on-fill` with `chat_msgIvButtonSuccessIn`; `mirror`: `botKeyboard_button_success` |
| `chat_msgIvButtonSuccessOut` † | The same button, sent | `#DCF4CB` | `pair:in/out` with `chat_msgIvButtonSuccessIn`; `pair:on/off` with `chat_msgIvButtonSuccessOutPressed` † |
| `chat_msgIvButtonSuccessOutPressed` † | Its pressed state | `#DCF4CB` | `pair:on/off` with `chat_msgIvButtonSuccessOut` |
| `chat_msgIvButtonSuccessOutText` † | Its label | `#40A920` | `fill→on-fill` with `chat_msgIvButtonSuccessOut` |
| `chat_msgIvButtonDangerIn` † | The destructive button's fill, received | `#F8EAE9` | `pair:in/out` with `chat_msgIvButtonDangerOut`; `family`: button intents; `pair:on/off` with `chat_msgIvButtonDangerInPressed` † |
| `chat_msgIvButtonDangerInPressed` † | Its pressed state | `#F8EAE9` | `pair:on/off` with `chat_msgIvButtonDangerIn` |
| `chat_msgIvButtonDangerInText` † | Its label — the part that carries the "danger" meaning | `#CC5049` | `fill→on-fill` with `chat_msgIvButtonDangerIn`; `mirror`: `botKeyboard_button_danger` |
| `chat_msgIvButtonDangerOut` † | The same button, sent | `#EAE9CB` | `pair:in/out` with `chat_msgIvButtonDangerIn`; `pair:on/off` with `chat_msgIvButtonDangerOutPressed` † |
| `chat_msgIvButtonDangerOutPressed` † | Its pressed state | `#EAE9CB` | `pair:on/off` with `chat_msgIvButtonDangerOut` |
| `chat_msgIvButtonDangerOutText` † | Its label | `#CC5049` | `fill→on-fill` with `chat_msgIvButtonDangerOut` |
