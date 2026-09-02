> Part of the [theme-keys](../SKILL.md) skill's Android key reference. Covers §6 Form controls, §7 Dialogs and bottom sheets, §8 Profile screen. Relation-tag vocabulary and markers are defined once in `SKILL.md`.

## 6. Form controls

Every control here is an on/off pair (or triple with a disabled state). The
`dialog*` mirrors in §7 are the same controls redrawn inside a bottom sheet.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `checkbox` | The round checkbox's fill when checked | `#5EC245` | `pair:on/off` with `checkboxDisabled`; `fill→on-fill`: `checkboxCheck` |
| `checkboxCheck` | The tick inside it | `#FFFFFF` | `fill→on-fill` with `checkbox` |
| `checkboxDisabled` | The same checkbox when it cannot be toggled | `#B0B9C2` | `pair:on/off` with `checkbox` |
| `checkboxSquareBackground` | The square checkbox's fill when checked | `#229AF0` | `pair:on/off` with `checkboxSquareUnchecked`; `mirror`: `dialogCheckboxSquareBackground`; `pair:on/off` with `checkboxSquareDisabled` |
| `checkboxSquareCheck` | The tick inside it | `#FFFFFF` | `fill→on-fill` with `checkboxSquareBackground` |
| `checkboxSquareUnchecked` | Its outline when unchecked | `#737373` | `pair:on/off` with `checkboxSquareBackground` |
| `checkboxSquareDisabled` | Its outline when disabled | `#B0B0B0` | `pair:on/off` with `checkboxSquareBackground` |
| `radioBackground` | An unselected radio button's ring | `#B3B3B3` | `pair:on/off` with `radioBackgroundChecked`; `mirror`: `dialogRadioBackground` |
| `radioBackgroundChecked` | The selected radio button | `#229AF0` | `pair:on/off` with `radioBackground` |
| `switchTrack` | The toggle's track when off | `#A6ADB3` | `pair:on/off` with `switchTrackChecked` |
| `switchTrackChecked` | The toggle's track when on | `#229AF0` | `pair:on/off` with `switchTrack` |
| `switch2Track` | The second toggle style's track when off — used where "off" is the destructive state, hence its red default | `#F57E7E` | `pair:on/off` with `switch2TrackChecked` |
| `switch2TrackChecked` | That track when on | `#229AF0` | `pair:on/off` with `switch2Track` |
| `switchTrackBlue` | The accent toggle style's track when off | `#78828A` | `family`: `switchTrackBlue*`, six keys that must move as one; `pair:on/off` with `switchTrackBlueChecked` |
| `switchTrackBlueChecked` | That track when on | `#1079C4` | `pair:on/off` with `switchTrackBlue` |
| `switchTrackBlueThumb` | Its thumb when off | `#FFFFFF` | `pair:on/off` with `switchTrackBlueThumbChecked` |
| `switchTrackBlueThumbChecked` | Its thumb when on | `#FFFFFF` | `pair:on/off` with `switchTrackBlueThumb` |
| `switchTrackBlueSelector` | The press ripple around it when off | `#17404A53` | `pair:on/off` with `switchTrackBlueSelectorChecked` |
| `switchTrackBlueSelectorChecked` | That ripple when on | `#21024781` | `pair:on/off` with `switchTrackBlueSelector` |
| `picker_enabledButton` | An active button in a date/number picker | `#19A7E8` | `pair:on/off` with `picker_disabledButton` |
| `picker_disabledButton` | A greyed-out picker button | `#999999` | `pair:on/off` with `picker_enabledButton` |
| `picker_badge` | The count badge on a picker's confirm button | `#29B6F7` | `fill→on-fill`: `picker_badgeText` |
| `picker_badgeText` | The number in that badge | `#FFFFFF` | `fill→on-fill` with `picker_badge` |
| `buttonNeutral` † | A neutral (neither primary nor destructive) button's fill | `#E4E4E4` | `fill→on-fill`: `buttonNeutralText` †; `family`: with `fill_RedNormal` † |
| `buttonNeutralText` † | The label on it | `#1A1D21` | `fill→on-fill` with `buttonNeutral` † |

---

## 7. Dialogs and bottom sheets

Everything modal: alerts, the attach sheet's frame, share sheets, pickers. This
whole namespace is a **`mirror` of the `windowBackgroundWhite*` set** — same
elements, sheet context. When you change one side, check the other, or sheets and
screens drift apart.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `dialogBackground` | The sheet/alert surface | `#FFFFFF` | `mirror`: `windowBackgroundWhite`; `fill→on-fill`: `dialogTextBlack` |
| `dialogBackgroundGray` | The recessed background inside a sheet | `#F0F0F0` | `mirror`: `windowBackgroundGray` |
| `dialogTextBlack` | Primary text in a sheet | `#1A1D21` | `mirror`: `windowBackgroundWhiteBlackText` |
| `dialogTextGray` | Secondary text, base shade | `#348BC1` | `family`: `dialogTextGray2`–`4`; `mirror`: `windowBackgroundWhiteGrayText` |
| `dialogTextGray2` | Secondary text, second shade | `#757575` | `family`: dialog gray ramp |
| `dialogTextGray3` | Secondary text, third shade | `#999999` | `family`: dialog gray ramp |
| `dialogTextGray4` | Secondary text, lightest shade | `#B3B3B3` | `family`: dialog gray ramp |
| `dialogTextHint` | Placeholder text in a sheet's input | `#979797` | `mirror`: `windowBackgroundWhiteHintText` |
| `dialogTextLink` | A hyperlink in a sheet | `#2678B6` | `pair` with `dialogLinkSelection`; `mirror`: `windowBackgroundWhiteLinkText`; `pair:rest/selected` with `dialogLinkSelection` |
| `dialogLinkSelection` | Highlight behind a pressed link there | `#3362A9E3` | `pair:rest/selected` with `dialogTextLink` |
| `dialogTextBlue` | Accent text in a sheet, base shade | `#2F8CC9` | `family`: `dialogTextBlue2`–`4`; `mirror`: `windowBackgroundWhiteBlueText` |
| `dialogTextBlue2` | Accent text, second shade | `#3A95D5` | `family`: dialog blue ramp |
| `dialogTextBlue4` | Accent text, fourth shade | `#19A7E8` | `family`: dialog blue ramp |
| `dialogIcon` | A neutral icon in a sheet | `#1A1D21` | `mirror`: `windowBackgroundWhiteGrayIcon` |
| `dialogButton` | An alert's action button label ("Cancel", "OK") | `#298ACF` | `pair` with `dialogButtonSelector` |
| `dialogButtonSelector` | The press ripple on that button | `#0F000000` | `mirror`: `listSelectorSDK21` |
| `dialogGrayLine` | A divider inside a sheet | `#D2D2D2` | `mirror`: `divider` |
| `dialogShadowLine` | The shadow at a sheet's top edge | `#12000000` | `mirror`: `windowBackgroundGrayShadow` |
| `dialogCardShadow` † | The shadow under a card element inside a sheet | `#17000000` | `mirror`: `dialogShadowLine` |
| `dialogScrollGlow` | The overscroll glow inside a sheet | `#F5F6F7` | `family`: sheet chrome |
| `dialogTopBackground` | The colored header block at the top of a sheet | `#298ACF` | `mirror`: `chats_menuTopBackground` † |
| `dialogInputField` | An unfocused input's underline in a sheet | `#DBDBDB` | `pair:on/off` with `dialogInputFieldActivated` |
| `dialogInputFieldActivated` | The focused input's underline | `#229AF0` | `pair:on/off` with `dialogInputField`; `mirror`: `windowBackgroundWhiteInputFieldActivated` |
| `dialogSearchBackground` | The search field inside a sheet | `#F2F4F5` | `fill→on-fill`: `dialogSearchText`, `dialogSearchIcon` |
| `dialogSearchText` | Typed text in it | `#222222` | `pair` with `dialogSearchHint` |
| `dialogSearchHint` | Its placeholder | `#98A0A7` | `pair` with `dialogSearchText` |
| `dialogSearchIcon` | The magnifier in it | `#A1A8AF` | `fill→on-fill` with `dialogSearchBackground` |
| `dialogCheckboxSquareBackground` | A checked square checkbox in a sheet | `#229AF0` | `mirror`: `checkboxSquareBackground` |
| `dialogCheckboxSquareCheck` | Its tick | `#FFFFFF` | `mirror`: `checkboxSquareCheck` |
| `dialogCheckboxSquareUnchecked` | Its unchecked outline | `#737373` | `mirror`: `checkboxSquareUnchecked` |
| `dialogCheckboxSquareDisabled` | Its disabled outline | `#B0B0B0` | `mirror`: `checkboxSquareDisabled` |
| `dialogRoundCheckBox` | A checked round checkbox in a sheet | `#229AF0` | `fill→on-fill`: `dialogRoundCheckBoxCheck`; `mirror`: `checkbox` |
| `dialogRoundCheckBoxCheck` | Its tick | `#FFFFFF` | `fill→on-fill` with `dialogRoundCheckBox` |
| `dialogRadioBackground` | An unselected radio in a sheet | `#B3B3B3` | `pair:on/off` with `dialogRadioBackgroundChecked`; `mirror`: `radioBackground` |
| `dialogRadioBackgroundChecked` | The selected radio there | `#229AF0` | `pair:on/off` with `dialogRadioBackground` |
| `dialogFloatingButton` | A floating action button on a sheet | `#229AF0` | `pair:on/off` with `dialogFloatingButtonPressed`; `fill→on-fill`: `dialogFloatingIcon` |
| `dialogFloatingButtonPressed` | Its pressed state | `#0F000000` | `pair:on/off` with `dialogFloatingButton` |
| `dialogFloatingIcon` | The glyph on it | `#FFFFFF` | `fill→on-fill` with `dialogFloatingButton` |
| `dialogLineProgress` | The filled part of a determinate progress bar in a sheet | `#527DA3` | `pair:on/off` with `dialogLineProgressBackground` |
| `dialogLineProgressBackground` | Its unfilled track | `#DBDBDB` | `pair:on/off` with `dialogLineProgress` |
| `dialog_inlineProgress` | The spinner shown inline while a sheet loads content | `#6B7378` | `pair` with `dialog_inlineProgressBackground` |
| `dialog_inlineProgressBackground` | The scrim behind that spinner | `#F6F0F2F5` | `pair` with `dialog_inlineProgress` |
| `dialog_liveLocationProgress` | The countdown ring on a live-location share sheet | `#359FE5` | `mirror`: `location_liveLocationProgress` |
| `dialogReactionMentionBackground` | The badge marking an unread reaction/mention | `#EB5E5E` | `mirror`: `chats_mentionIcon` |
| `dialogSwipeRemove` † | The red fill revealed by swiping a row away in a sheet | `#E56555` | `mirror`: `fill_RedNormal` † |
| `dialogEmptyImage` † | The illustration on an empty sheet | `#9FA4A8` | `pair` with `dialogEmptyText` † |
| `dialogEmptyText` † | The text under it | `#8C9094` | `pair` with `dialogEmptyImage` †; `mirror`: `emptyListPlaceholder` |
| `dialogGiftsBackground` † | The background of the gifts tab inside a sheet | `#F5F6F7` | `pair` with `dialogGiftsTabText` †; `family`: gifts (§16) |
| `dialogGiftsTabText` † | Its tab labels | `#56595C` | `pair` with `dialogGiftsBackground` † |
| `key_sheet_scrollUp` | The small drag handle at the top of a bottom sheet — keeps Telegram's `key_` prefix | `#E1E4E8` | `pair` with `key_sheet_other` |
| `key_sheet_other` | The ⋮ / secondary control in a sheet's header — also `key_`-prefixed | `#C9CDD3` | `pair` with `key_sheet_scrollUp` |

---

## 8. Profile screen

The Message / Unmute / Video Chat / Add Story / Leave button row and the tab
strip under it.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `profile_title` | The user or group name at the top | `#222222` | `pair` with `profile_status`; `mirror`: `actionBarDefaultTitle` |
| `profile_status` | The "online" / "7 members" line under it | `#222222` | `mirror`: `avatar_subtitleInProfileBlue` |
| `profile_actionBackground` | A round action button's card ("Message", "Leave") | `#FFFFFF` | `pair:on/off` with `profile_actionPressedBackground`; `fill→on-fill`: `profile_actionIcon` |
| `profile_actionPressedBackground` | That card while pressed | `#121A1D21` | `pair:on/off` with `profile_actionBackground` |
| `profile_actionIcon` | The glyph on it | `#81868A` | `fill→on-fill` with `profile_actionBackground` |
| `profile_creatorIcon` | The crown/star marking the group's owner in the member list | `#3A95D5` | `mirror`: `chat_tagCreator` † |
| `profile_verifiedBackground` | The verified badge disc on a profile | `#229AF0` | `fill→on-fill`: `profile_verifiedCheck`; `mirror`: `chats_verifiedBackground` |
| `profile_verifiedCheck` | The check inside it | `#FFFFFF` | `fill→on-fill` with `profile_verifiedBackground` |
| `profile_tabText` † | An unselected tab label ("Media", "Files", "Links") | `#878C90` | `pair:on/off` with `profile_tabSelectedText` † |
| `profile_tabSelectedText` † | The selected tab's label | `#3A95D5` | `pair:on/off` with `profile_tabText` † |
| `profile_tabSelectedLine` † | The underline under the selected tab | `#4FA6E9` | `mirror`: `actionBarTabLine` |
| `profile_tabSelector` † | The press ripple on a tab | `#0F000000` | `mirror`: `actionBarTabSelector` |
