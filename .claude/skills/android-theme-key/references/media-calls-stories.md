> Part of the [android-theme-key](../SKILL.md) skill's Android key reference. Covers §9 Music player, §10 Stories, §11 Location and maps, §12 Shared media/files/sticker store, §13 Group voice and video chats (`voipgroup_*`). Relation-tag vocabulary and markers are defined once in `SKILL.md`.

## 9. Music player

Three surfaces: the full player screen (`player_*`), the mini bar that docks
above the chat list (`inappPlayer*`), and the music-picker sheet
(`musicPicker_*`).

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `player_background` | The full player screen's background | `#FFFFFF` | `fill→on-fill`: `player_button`, `player_time` |
| `player_actionBarTitle` | The track title in the player's bar | `#2F3438` | `pair` with `player_actionBarSubtitle` |
| `player_actionBarSubtitle` | The artist line under it | `#8A8A8A` | `pair` with `player_actionBarTitle` |
| `player_actionBarItems` | Icons in the player's bar | `#8A8A8A` | `mirror`: `actionBarDefaultIcon` |
| `player_actionBarSelector` | Press ripple there | `#0F000000` | `mirror`: `actionBarDefaultSelector` |
| `player_button` | A transport control (play, next, shuffle) at rest | `#333333` | `pair:on/off` with `player_buttonActive` |
| `player_buttonActive` | A transport control in its active state (shuffle/repeat on) | `#4CA8EA` | `pair:on/off` with `player_button` |
| `player_progress` | The played portion of the seek bar | `#54AAEB` | `pair:on/off` with `player_progressBackground`; `family`: with `key_player_progressCachedBackground` |
| `player_progressBackground` | Its unplayed track | `#EBEDF0` | `pair:on/off` with `player_progress` |
| `key_player_progressCachedBackground` | The buffered-ahead portion of that track — `key_`-prefixed in Telegram itself | `#C5DCF0` | `family`: player seek bar |
| `player_time` | The elapsed/remaining time labels | `#8C9296` | `fill→on-fill` with `player_background` |
| `inappPlayerBackground` | The mini player bar docked above the chat list | `#FFFFFF` | `fill→on-fill`: `inappPlayerTitle`, `inappPlayerPerformer` |
| `inappPlayerTitle` | The track title in that bar | `#1A1D21` | `pair` with `inappPlayerPerformer`; `mirror`: `player_actionBarTitle` |
| `inappPlayerPerformer` | The artist line in that bar | `#1A1D21` | `pair` with `inappPlayerTitle` |
| `inappPlayerPlayPause` | Its play/pause control | `#229AF0` | `mirror`: `player_button` |
| `inappPlayerClose` | The ✕ that dismisses it | `#898B86` | `mirror`: `chat_topPanelClose` |

---

## 10. Stories

Six keys, three gradient pairs. Each pair is the two stops of the ring drawn
around an avatar with an unseen story; `1` is the start of the gradient, `2` the
end, and they only read as one ring if changed together.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `stories_circle1` | Start of the unseen-story ring's gradient | `#2C9EFC` | `pair` with `stories_circle2` |
| `stories_circle2` | End of that gradient | `#2FC183` | `pair` with `stories_circle1` |
| `stories_circle_dialog1` | Start of the same ring as drawn in the chat list | `#2C9EFC` | `pair` with `stories_circle_dialog2`; `mirror`: `stories_circle1` |
| `stories_circle_dialog2` | End of that gradient | `#2FC183` | `pair` with `stories_circle_dialog1` |
| `stories_circle_closeFriends1` | Start of the green "close friends" ring | `#81CE2D` | `pair` with `stories_circle_closeFriends2`; `mirror`: `stories_circle1` |
| `stories_circle_closeFriends2` | End of that gradient | `#18BD36` | `pair` with `stories_circle_closeFriends1` |
| `stories_circle_live1` † | Start of the ring around an avatar with a live broadcast | `#FF6B5B` | `pair` with `stories_circle_live2`; `mirror`: `stories_circle1`, `stories_circle_closeFriends1` |
| `stories_circle_live2` † | End of that gradient | `#FA4874` | `pair` with `stories_circle_live1` |

---

## 11. Location and maps

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `location_sendLocationBackground` | The disc on the "Send My Current Location" row | `#469DF6` | `fill→on-fill`: `location_sendLocationIcon`; `pair` with the live-location row below |
| `location_sendLocationIcon` | The pin glyph on it | `#FFFFFF` | `fill→on-fill` with `location_sendLocationBackground` |
| `location_sendLocationText` † | The label on that row | `#1C8AD8` | `family`: send-location row |
| `location_sendLiveLocationBackground` | The disc on the "Share My Live Location" row | `#4FC244` | `fill→on-fill`: `location_sendLiveLocationIcon` |
| `location_sendLiveLocationIcon` | The glyph on it | `#FFFFFF` | `fill→on-fill` with `location_sendLiveLocationBackground` |
| `location_sendLiveLocationText` † | The label on that row | `#36AB24` | `family`: live-location row |
| `location_liveLocationProgress` | The countdown ring on an active live-location share | `#359FE5` | `mirror`: `dialog_liveLocationProgress` |
| `location_placeLocationBackground` | The disc on a nearby-place suggestion row | `#4CA8EA` | `family`: location rows |
| `location_actionBackground` † | The floating action button over the map | `#FFFFFF` | `pair:on/off` with `location_actionPressedBackground` † |
| `location_actionPressedBackground` † | Its pressed state | `#F2F2F2` | `pair:on/off` with `location_actionBackground` † |
| `location_actionIcon` † | The glyph on it at rest | `#3A4045` | `pair:on/off` with `location_actionActiveIcon` † |
| `location_actionActiveIcon` † | That glyph while location tracking is active | `#4290E6` | `pair:on/off` with `location_actionIcon` † |

---

## 12. Shared media, files, and the sticker store

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `sharedMedia_photoPlaceholder` | The tile shown while a shared-media thumbnail loads | `#EDF3F7` | `mirror`: `chat_attachPhotoBackground` |
| `sharedMedia_linkPlaceholder` | The tile behind a shared link's preview image | `#F0F3F5` | `pair` with `sharedMedia_linkPlaceholderText` |
| `sharedMedia_linkPlaceholderText` | The letter drawn on that tile when there is no image | `#B7BEC3` | `fill→on-fill` with `sharedMedia_linkPlaceholder` |
| `sharedMedia_startStopLoadIcon` | The download/cancel glyph on a shared-media row | `#36A2EE` | `mirror`: `chat_inLoader` |
| `files_folderIconBackground` | The disc behind a folder icon in the file browser | `#5DAFEB` | `fill→on-fill`: `files_folderIcon` |
| `files_folderIcon` | The folder glyph on it | `#FFFFFF` | `fill→on-fill` with `files_folderIconBackground` |
| `files_iconText` | The extension label on a file tile ("pdf") | `#FFFFFF` | `family`: file browser |
| `stickers_menu` | The ⋮ on a sticker-set row | `#B6BDC5` | `pair` with `stickers_menuSelector` |
| `stickers_menuSelector` | Its press ripple | `#0F000000` | `pair` with `stickers_menu` |
| `featuredStickers_addButton` | The "ADD" button on a trending sticker pack | `#229AF0` | `pair:on/off` with `featuredStickers_addButtonPressed`; `fill→on-fill`: `featuredStickers_buttonText`; `pair:on/off` with `featuredStickers_addedIcon` |
| `featuredStickers_addButtonPressed` | That button while pressed | `#2288D1` | `pair:on/off` with `featuredStickers_addButton` |
| `featuredStickers_addButton2` † | A second "add" button style | `#56BAF0` | `family`: sticker-store buttons |
| `featuredStickers_addedIcon` | The check shown once a pack is installed | `#229AF0` | `pair:on/off` with `featuredStickers_addButton` |
| `featuredStickers_buttonText` | The label on the add button | `#FFFFFF` | `fill→on-fill` with `featuredStickers_addButton`; `pair:on/off` with `featuredStickers_removeButtonText` † |
| `featuredStickers_buttonProgress` | The spinner shown on it while installing | `#FFFFFF` | `family`: sticker-store buttons |
| `featuredStickers_removeButtonText` † | The label on the "remove" button | `#5093D3` | `pair:on/off` with `featuredStickers_buttonText` |
| `featuredStickers_unread` | The dot marking an unseen trending pack | `#4DA6EA` | `mirror`: `chat_emojiPanelNewTrending` |

---

## 13. Group voice and video chats (`voipgroup_*`)

63 keys — the second-largest namespace, and the most self-contained. The group
call screen keeps its own dark palette regardless of the app theme, which is why
none of these mirror anything outside the namespace. **All 63 are absent from the
test sample** (†); they arrived with commit `0c7e740`.

Three suffix conventions carry the whole section:

- **`*Unscrolled` / `*Scrolled`** — the sheet recolors as the member list slides
  up under the header. A key and its scroll-state twin must stay in the same
  hue family or the transition flickers.
- **numeric suffixes `1`/`2`/`3`** — gradient stops, not variants. Always change
  the whole set.
- **`muteButton` / `unmuteButton` / `soundButton`** — the big central control in
  its different states.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `voipgroup_actionBar` † | The call screen's top bar once scrolled | `#0F1317` | `pair` with `voipgroup_actionBarUnscrolled` † |
| `voipgroup_actionBarUnscrolled` † | That bar before scrolling | `#191F26` | `pair` with `voipgroup_actionBar` † |
| `voipgroup_actionBarItems` † | Icons in that bar | `#FFFFFF` | `fill→on-fill` with `voipgroup_actionBar` † |
| `voipgroup_actionBarItemsSelector` † | Press ripple on them | `#1EBADBFF` | `mirror`: `actionBarDefaultSelector` |
| `voipgroup_listViewBackground` † | The member list's background once scrolled | `#1C2229` | `pair` with `voipgroup_listViewBackgroundUnscrolled` † |
| `voipgroup_listViewBackgroundUnscrolled` † | That background before scrolling | `#222A33` | `pair` with `voipgroup_listViewBackground` † |
| `voipgroup_listSelector` † | Press ripple on a member row | `#0EFFFFFF` | `mirror`: `listSelectorSDK21` |
| `voipgroup_dialogBackground` † | Background of a sheet opened from the call | `#1C2229` | `mirror`: `dialogBackground` |
| `voipgroup_nameText` † | A participant's name in the list | `#FFFFFF` | `family`: member row text |
| `voipgroup_lastSeenText` † | The "listening"/status line under a name, scrolled | `#79838A` | `pair` with `voipgroup_lastSeenTextUnscrolled` † |
| `voipgroup_lastSeenTextUnscrolled` † | The same line before scrolling | `#858D94` | `pair` with `voipgroup_lastSeenText` † |
| `voipgroup_listeningText` † | The status line while that participant is listening | `#4DB8FF` | `pair:on/off` with `voipgroup_speakingText` † |
| `voipgroup_speakingText` † | The status line while they are speaking | `#77EE7D` | `pair:on/off` with `voipgroup_listeningText` † |
| `voipgroup_mutedIcon` † | The muted-mic icon on a row, scrolled | `#6F7980` | `pair` with `voipgroup_mutedIconUnscrolled` † |
| `voipgroup_mutedIconUnscrolled` † | The same before scrolling | `#7E868C` | `pair` with `voipgroup_mutedIcon` † |
| `voipgroup_mutedByAdminIcon` † | The icon marking a participant muted by an admin | `#FF7070` | `family`: muted-by-admin |
| `voipgroup_mutedByAdminMuteButton` † | The central button when an admin has muted you | `#7F78A3FF` | `pair:on/off` with `voipgroup_mutedByAdminMuteButtonDisabled` † |
| `voipgroup_mutedByAdminMuteButtonDisabled` † | That button when you cannot unmute at all | `#3378A3FF` | `pair:on/off` with `voipgroup_mutedByAdminMuteButton` † |
| `voipgroup_mutedByAdminGradient` † | Stop 1 of the muted-by-admin background gradient | `#57A4FE` | `family`: three stops |
| `voipgroup_mutedByAdminGradient2` † | Stop 2 of it | `#F05459` | `family`: three stops |
| `voipgroup_mutedByAdminGradient3` † | Stop 3 of it | `#766EE9` | `family`: three stops |
| `voipgroup_muteButton` † | The big central mute button, state 1 | `#77E55C` | `family`: `voipgroup_muteButton{,2,3}`; `pair:on/off` with `voipgroup_unmuteButton` † |
| `voipgroup_muteButton2` † | Its second state/stop | `#7DDCAA` | `family` |
| `voipgroup_muteButton3` † | Its third state/stop | `#56C7FE` | `family` |
| `voipgroup_unmuteButton` † | The central button while you are muted, stop 1 | `#539EF8` | `pair:on/off` with `voipgroup_muteButton` †; `family` with `voipgroup_unmuteButton2` † |
| `voipgroup_unmuteButton2` † | Its second stop | `#66D4FB` | `family` |
| `voipgroup_soundButton` † | The speaker/sound toggle at rest, stop 1 | `#7D2C414D` | `family`: sound button, four keys; `pair:on/off` with `voipgroup_soundButtonActive` † |
| `voipgroup_soundButton2` † | Its second stop | `#7D28593A` | `family`; `pair:on/off` with `voipgroup_soundButtonActive2` † |
| `voipgroup_soundButtonActive` † | That toggle when on, stop 1 | `#7D22A4EB` | `pair:on/off` with `voipgroup_soundButton` † |
| `voipgroup_soundButtonActive2` † | Its second stop | `#7D18B751` | `pair:on/off` with `voipgroup_soundButton2` † |
| `voipgroup_soundButtonActiveScrolled` † | The active toggle once the sheet is scrolled | `#8233B4FF` | `pair` with `voipgroup_soundButtonActive` † |
| `voipgroup_soundButtonActive2Scrolled` † | Its second stop, scrolled | `#8224BF46` | `pair` with `voipgroup_soundButtonActive2` † |
| `voipgroup_leaveButton` † | The "Leave" button | `#7DF75C5C` | `pair` with `voipgroup_leaveButtonScrolled` † |
| `voipgroup_leaveButtonScrolled` † | It once the sheet is scrolled | `#82D14D54` | `pair` with `voipgroup_leaveButton` † |
| `voipgroup_leaveCallMenu` † | The destructive item in the call's overflow menu | `#FF7575` | `mirror`: `fill_RedNormal` † |
| `voipgroup_disabledButton` † | A call control that cannot currently be used | `#1C2229` | `pair:on/off` with `voipgroup_disabledButtonActive` † |
| `voipgroup_disabledButtonActive` † | Its active-but-still-disabled variant | `#2C3A45` | `pair:on/off` with `voipgroup_disabledButton` † |
| `voipgroup_disabledButtonActiveScrolled` † | That variant once scrolled | `#8277A1FC` | `pair` with `voipgroup_disabledButtonActive` † |
| `voipgroup_inviteMembersBackground` † | The "Invite Members" row's background | `#222A33` | `family`: call sheet rows |
| `voipgroup_checkMenu` † | The check mark in the call's overflow menu | `#6BB6F9` | `mirror`: `chats_menuItemCheck` |
| `voipgroup_connectingProgress` † | The spinner while joining the call | `#28BAFF` | `mirror`: `progressCircle` |
| `voipgroup_scrollUp` † | The sheet's drag handle | `#394654` | `mirror`: `key_sheet_scrollUp` |
| `voipgroup_rtmpButton` † | The RTMP / "stream with software" button | `#2A3853` | `family`: call sheet rows |
| `voipgroup_searchBackground` † | The member-search field | `#303B47` | `fill→on-fill`: `voipgroup_searchText` † |
| `voipgroup_searchText` † | Typed text in it | `#FFFFFF` | `pair` with `voipgroup_searchPlaceholder` † |
| `voipgroup_searchPlaceholder` † | Its placeholder | `#858D94` | `pair` with `voipgroup_searchText` † |
| `voipgroup_windowBackgroundWhiteInputField` † | An unfocused input's underline inside a call sheet | `#DBDBDB` | `pair:on/off` with the activated key below; `mirror`: `windowBackgroundWhiteInputField` |
| `voipgroup_windowBackgroundWhiteInputFieldActivated` † | The focused input's underline | `#229AF0` | `pair:on/off` with the key above |
| `voipgroup_topPanelGray` † | The wave panel at the top of the call in its neutral state | `#8599AA` | `family`: top panel, five keys |
| `voipgroup_topPanelBlue1` † | That panel's blue gradient, stop 1 | `#60C7FB` | `pair` with `voipgroup_topPanelBlue2` † |
| `voipgroup_topPanelBlue2` † | Stop 2 of it | `#519FF9` | `pair` with `voipgroup_topPanelBlue1` † |
| `voipgroup_topPanelGreen1` † | That panel's green (speaking) gradient, stop 1 | `#52CE5D` | `pair` with `voipgroup_topPanelGreen2` † |
| `voipgroup_topPanelGreen2` † | Stop 2 of it | `#00B1C0` | `pair` with `voipgroup_topPanelGreen1` † |
| `voipgroup_overlayBlue1` † | The full-screen overlay's blue gradient, stop 1 | `#2BCEFF` | `pair` with `voipgroup_overlayBlue2` †; `mirror`: `voipgroup_topPanelBlue1` † |
| `voipgroup_overlayBlue2` † | Stop 2 of it | `#0976E3` | `pair` with `voipgroup_overlayBlue1` † |
| `voipgroup_overlayGreen1` † | That overlay's green gradient, stop 1 | `#12B522` | `pair` with `voipgroup_overlayGreen2` † |
| `voipgroup_overlayGreen2` † | Stop 2 of it | `#00D6C1` | `pair` with `voipgroup_overlayGreen1` † |
| `voipgroup_overlayAlertGradientMuted` † | The alert overlay's gradient while muted, stop 1 | `#236D92` | `pair` with `voipgroup_overlayAlertGradientMuted2` †; `pair:on/off` with `voipgroup_overlayAlertGradientUnmuted` † |
| `voipgroup_overlayAlertGradientMuted2` † | Stop 2 of it | `#2C4D6B` | `pair` with `voipgroup_overlayAlertGradientMuted` † |
| `voipgroup_overlayAlertGradientUnmuted` † | The same while unmuted, stop 1 | `#0C8A8C` | `pair:on/off` with `voipgroup_overlayAlertGradientMuted` † |
| `voipgroup_overlayAlertGradientUnmuted2` † | Stop 2 of it | `#284C75` | `pair` with `voipgroup_overlayAlertGradientUnmuted` † |
| `voipgroup_overlayAlertMutedByAdmin` † | The same while muted by an admin, stop 1 | `#67709E` | `pair` with `voipgroup_overlayAlertMutedByAdmin2` † |
| `voipgroup_overlayAlertMutedByAdmin2` † | Stop 2 of it | `#2F5078` | `pair` with `voipgroup_overlayAlertMutedByAdmin` † |
