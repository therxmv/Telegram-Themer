> Part of the [android-theme-key](../SKILL.md) skill's Android key reference. Covers §1 Foundations/window chrome, §2 Action bar and tab bars, §3 Chat list and side menu, §4 Avatars. Relation-tag vocabulary and markers are defined once in `SKILL.md`, not repeated here.

## 1. Foundations — window chrome

The base layer of every settings screen, list, and form. `windowBackgroundWhite`
is the app's content surface and `windowBackgroundGray` the recessed one behind
grouped sections; almost everything else here is text or an icon drawn on one of
those two.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `windowBackgroundWhite` | The main content surface — settings lists, profile bodies, most screens | `#FFFFFF` | `fill→on-fill`: every `windowBackgroundWhite*Text`/`*Icon` below; `mirror`: `dialogBackground` |
| `windowBackgroundGray` | The recessed background behind grouped sections and between cards | `#F1F1F3` | `mirror`: `dialogBackgroundGray`; pairs with `windowBackgroundGrayShadow` |
| `windowBackgroundGrayShadow` | Shadow/divider line where a gray section meets a white one | `#000000` | `family`: section shadows with `groupcreate_sectionShadow` |
| `windowBackgroundWhiteBlackText` | Primary text on the white surface — setting titles, names | `#1A1D21` | `mirror`: `dialogTextBlack`; contrast partner of `windowBackgroundWhite`; `pair:on/off` with `windowBackgroundWhiteGrayText7` |
| `windowBackgroundWhiteGrayText` | Secondary/subtitle text under a setting row | `#808384` | `family`: `windowBackgroundWhiteGrayText2`–`8` |
| `windowBackgroundWhiteGrayText2` | Secondary text, second shade (section subtitles) | `#82868A` | `family`: gray-text ramp |
| `windowBackgroundWhiteGrayText3` | Secondary text, third shade | `#999999` | `family`: gray-text ramp; `mirror`: `dialogTextGray3` |
| `windowBackgroundWhiteGrayText4` | Section header text above a group of rows | `#808080` | `family`: gray-text ramp; `alias`-like partner `key_graySectionText` |
| `windowBackgroundWhiteGrayText5` | Muted list text, fifth shade | `#A3A3A3` | `family`: gray-text ramp |
| `windowBackgroundWhiteGrayText6` | Muted list text, sixth shade | `#757575` | `family`: gray-text ramp |
| `windowBackgroundWhiteGrayText7` | Disabled row text — the lightest of the ramp | `#C6C6C6` | `family`: gray-text ramp; `pair:on/off` with `windowBackgroundWhiteBlackText` |
| `windowBackgroundWhiteGrayText8` | Muted list text, eighth shade | `#6D6D72` | `family`: gray-text ramp |
| `windowBackgroundWhiteGrayIcon` | Row icons drawn in neutral gray on the white surface | `#1A1D21` | `fill→on-fill` with `windowBackgroundWhite`; `mirror`: `dialogIcon` |
| `windowBackgroundWhiteHintText` | Placeholder/hint text in an input field | `#A8A8A8` | pairs with `windowBackgroundWhiteInputField`; `mirror`: `dialogTextHint` |
| `windowBackgroundWhiteBlueText` | Accent-colored row text (tappable values) | `#4092CD` | `family`: `windowBackgroundWhiteBlueText2`–`7` |
| `windowBackgroundWhiteBlueText2` | Accent text, second shade | `#3A95D5` | `family`: blue-text ramp |
| `windowBackgroundWhiteBlueText3` | Accent text, third shade | `#2678B6` | `family`: blue-text ramp |
| `windowBackgroundWhiteBlueText4` | Accent text, fourth shade — links inside descriptions | `#1C93E3` | `family`: blue-text ramp; `mirror`: `dialogTextBlue4` |
| `windowBackgroundWhiteBlueText5` | Accent text, fifth shade | `#4C8ECA` | `family`: blue-text ramp |
| `windowBackgroundWhiteBlueText6` | Accent text, sixth shade | `#3A8CCF` | `family`: blue-text ramp |
| `windowBackgroundWhiteBlueText7` | Accent text, seventh (darkest) shade | `#377AAE` | `family`: blue-text ramp |
| `windowBackgroundWhiteBlueHeader` | Accent-colored section header ("Accounts", "Settings") | `#298ACF` | `mirror`: `chats_menuName`; visible in the Settings screenshot |
| `windowBackgroundWhiteBlueButton` | Accent text of a full-width borderless button row | `#1E88D3` | `family`: with `windowBackgroundWhiteBlueText*` |
| `windowBackgroundWhiteBlueIcon` | Accent-tinted row icon | `#379DE5` | `fill→on-fill` with `windowBackgroundWhite` |
| `windowBackgroundWhiteValueText` | The value shown at the right end of a setting row ("English") | `#298ACF` | pairs with `windowBackgroundWhiteBlackText` on the same row |
| `windowBackgroundWhiteLinkText` | Hyperlink text on the white surface | `#298ACF` | pairs with `windowBackgroundWhiteLinkSelection`; `mirror`: `dialogTextLink`; `pair:rest/selected` with `windowBackgroundWhiteLinkSelection` |
| `windowBackgroundWhiteLinkSelection` | Highlight behind a long-pressed link | `#3362A9E3` | `pair:rest/selected` with `windowBackgroundWhiteLinkText`; `mirror`: `dialogLinkSelection` |
| `windowBackgroundWhiteGreenText` | Success/positive text on the white surface | `#26972C` | `family`: with `windowBackgroundWhiteGreenText2` |
| `windowBackgroundWhiteGreenText2` | Success text, second shade | `#37A818` | `family`: green text |
| `windowBackgroundWhiteInputField` | Underline of an unfocused text field | `#DBDBDB` | `pair:on/off` with `windowBackgroundWhiteInputFieldActivated` |
| `windowBackgroundWhiteInputFieldActivated` | Underline of the focused text field | `#229AF0` | `pair:on/off` with `windowBackgroundWhiteInputField`; `mirror`: `dialogInputFieldActivated` |
| `windowBackgroundChecked` | Background of a row in its checked/selected state | `#229AF0` | `pair:on/off` with `windowBackgroundUnchecked`; `fill→on-fill`: `windowBackgroundCheckText` |
| `windowBackgroundUnchecked` | Background of the same row unchecked | `#96A2AD` | `pair:on/off` with `windowBackgroundChecked` |
| `windowBackgroundCheckText` | Text drawn on the checked background | `#FFFFFF` | `fill→on-fill` with `windowBackgroundChecked` |
| `divider` | The hairline separating list rows | `#D9D9D9` | `mirror`: `dialogGrayLine` |
| `graySection` | Background strip of a list section header ("Recent", "Shared Music") | `#F5F5F5` | `fill→on-fill`: `key_graySectionText` |
| `key_graySectionText` | Text inside that header strip — note the retained `key_` prefix, which is how Telegram itself spells this one | `#82878A` | `fill→on-fill` with `graySection` |
| `listSelectorSDK21` | The ripple/press highlight on any list row | `#0F000000` | `mirror`: `dialogButtonSelector`, `actionBarDefaultSelector` |
| `emptyListPlaceholder` | "No posts yet…" style text on an empty screen | `#73787B` | `mirror`: `dialogEmptyText` † |
| `fastScrollActive` | The fast-scroll bubble while being dragged | `#229AF0` | `pair:on/off` with `fastScrollInactive`; `fill→on-fill`: `fastScrollText` |
| `fastScrollInactive` | The fast-scroll handle at rest | `#C9CDD1` | `pair:on/off` with `fastScrollActive` |
| `fastScrollText` | Letter/date shown inside the fast-scroll bubble | `#FFFFFF` | `fill→on-fill` with `fastScrollActive` |
| `progressCircle` | The generic indeterminate spinner | `#1C93E3` | — |
| `contextProgressInner1` | Track ring of the small inline spinner, variant 1 (action bar) | `#BFDFF6` | `family`: `contextProgressInner1`–`4`; `pair` with `contextProgressOuter1` |
| `contextProgressInner2` | Track ring, variant 2 (on a white sheet) | `#BFDFF6` | `family`; pairs with `contextProgressOuter2` |
| `contextProgressInner3` | Track ring, variant 3 (on media/dark) | `#B3B3B3` | `family`; pairs with `contextProgressOuter3` |
| `contextProgressInner4` | Track ring, variant 4 | `#CACDD0` | `family`; pairs with `contextProgressOuter4` |
| `contextProgressOuter1` | Moving arc of that spinner, variant 1 | `#2B96E2` | `family`: `contextProgressOuter1`–`4` |
| `contextProgressOuter2` | Moving arc, variant 2 | `#FFFFFF` | `family` |
| `contextProgressOuter3` | Moving arc, variant 3 | `#FFFFFF` | `family` |
| `contextProgressOuter4` | Moving arc, variant 4 | `#2F3438` | `family` |
| `text_RedRegular` † | Destructive text in current Telegram ("Leave", "Delete") | `#CC2929` | `pair` with `text_RedBold`; supersedes `windowBackgroundWhiteRedText*` |
| `text_RedBold` † | The bold weight of the same destructive text | `#CC4747` | `pair` with `text_RedRegular` |
| `fill_RedNormal` † | Destructive *fill* — a red button or badge body | `#EB5E5E` | `pair` with `fill_RedDark`; `fill→on-fill` with white text; `pair:on/off` with `fill_RedDark` † |
| `fill_RedDark` † | The pressed/darker step of that red fill | — | `pair:on/off` with `fill_RedNormal` |
| `table_background` † | Cell background of a table inside a message or Instant View article | `#F7F7F7` | `pair` with `table_border` |
| `table_border` † | Grid lines of that table | `#E0E0E0` | `pair` with `table_background` |
| `wallpaperFileOffset` | **Not a color.** The byte offset inside the `.attheme` file where the embedded wallpaper image begins; `-1`/absent means the theme ships no wallpaper | — | related to `chat_wallpaper` |

---

## 2. Action bar and tab bars

The top bar of every screen. `actionBarDefault` is the bar itself; the `Archived`
variants are the same bar while you are inside the Archived Chats list, and the
`ActionMode` variants are the bar in multi-select mode (the one that appears when
you long-press a chat). Change a base key and its `Archived` mirror together
unless you specifically want the archive to look different.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `actionBarDefault` | The top bar background on the main screens | `#FFFFFF` | `fill→on-fill`: `actionBarDefaultTitle`, `actionBarDefaultIcon`; `mirror`: `actionBarDefaultArchived` |
| `actionBarDefaultTitle` | Chat/screen title text in the bar | `#1A1D21` | `fill→on-fill` with `actionBarDefault`; `mirror`: `actionBarDefaultArchivedTitle` |
| `actionBarDefaultSubtitle` | The line under the title ("7 members", "online") | `#79817E` | pairs with `actionBarDefaultTitle` |
| `actionBarDefaultIcon` | Back arrow and bar icons | `#1A1D21` | `fill→on-fill` with `actionBarDefault`; `mirror`: `actionBarDefaultArchivedIcon` |
| `actionBarDefaultSelector` | Press ripple on a bar icon | `#121A1D21` | `mirror`: `actionBarDefaultArchivedSelector`, `actionBarWhiteSelector`, `actionBarTabSelector` |
| `actionBarDefaultSearch` | Typed text in the bar's search field | `#1A1D21` | pairs with `actionBarDefaultSearchPlaceholder`; `mirror`: `actionBarDefaultArchivedSearch` |
| `actionBarDefaultSearchPlaceholder` | "Search Chats" placeholder in that field | `#79817E` | `pair` with `actionBarDefaultSearch`; `mirror`: `actionBarDefaultSearchArchivedPlaceholder` |
| `actionBarDefaultSearchArchivedPlaceholder` | Same placeholder inside the archive | `#838C96` | `mirror`: `actionBarDefaultSearchPlaceholder` |
| `actionBarDefaultSubmenuBackground` | Background of the ⋮ dropdown menu | `#FFFFFF` | `fill→on-fill`: `actionBarDefaultSubmenuItem`, `actionBarDefaultSubmenuItemIcon` |
| `actionBarDefaultSubmenuItem` | Menu item label ("Night Mode", "New Group") | `#1A1D21` | `fill→on-fill` with `actionBarDefaultSubmenuBackground` |
| `actionBarDefaultSubmenuItemIcon` | Menu item icon | `#1A1D21` | `fill→on-fill` with `actionBarDefaultSubmenuBackground` |
| `actionBarDefaultSubmenuSeparator` | Divider between menu item groups | `#F5F5F5` | pairs with `actionBarDefaultSubmenuBackground` |
| `actionBarDefaultArchived` | The bar background inside Archived Chats | `#FFFFFF` | `mirror`: `actionBarDefault` |
| `actionBarDefaultArchivedTitle` | Title text there | `#1A1D21` | `mirror`: `actionBarDefaultTitle` |
| `actionBarDefaultArchivedIcon` | Bar icons there | `#1A1D21` | `mirror`: `actionBarDefaultIcon` |
| `actionBarDefaultArchivedSearch` | Search text there | `#1A1D21` | `mirror`: `actionBarDefaultSearch` |
| `actionBarDefaultArchivedSelector` | Press ripple there | `#121A1D21` | `mirror`: `actionBarDefaultSelector` |
| `actionBarActionModeDefault` | The bar background in multi-select mode | `#FFFFFF` | `fill→on-fill`: `actionBarActionModeDefaultIcon` |
| `actionBarActionModeDefaultIcon` | Icons in multi-select mode | `#1A1D21` | `fill→on-fill` with `actionBarActionModeDefault` |
| `actionBarActionModeDefaultSelector` | Press ripple in multi-select mode | `#E2E2E2` | `mirror`: `actionBarDefaultSelector` |
| `actionBarActionModeDefaultTop` | The status-bar strip above the multi-select bar | `#10000000` | pairs with `actionBarActionModeDefault` |
| `actionBarActionModeReaction` † | Background of the reaction chip shown in the selection bar | `#F0F0F0` | `fill→on-fill`: `actionBarActionModeReactionText` |
| `actionBarActionModeReactionText` † | Count text on that chip | `#82868A` | `fill→on-fill` with `actionBarActionModeReaction` |
| `actionBarActionModeReactionDot` † | The unread dot on that chip | `#C0C0C0` | `family`: reaction chip |
| `actionBarBrowser` | Bar background of the in-app browser | `#FFFFFF` | `mirror`: `actionBarDefault` |
| `actionBarWhiteSelector` | Press ripple for bars that are forced white | `#121A1D21` | `mirror`: `actionBarDefaultSelector` |
| `actionBarTabActiveText` | Label of the selected tab in a top tab strip | `#298ACF` | `pair:on/off` with `actionBarTabUnactiveText`; pairs with `actionBarTabLine` |
| `actionBarTabUnactiveText` | Label of an unselected tab | `#777C7F` | `pair:on/off` with `actionBarTabActiveText` |
| `actionBarTabLine` | The underline under the selected tab | `#298ACF` | pairs with `actionBarTabActiveText` |
| `actionBarTabSelector` | Press ripple on a tab | `#121A1D21` | `mirror`: `actionBarDefaultSelector` |

### Bottom tab bar ("glass")

The floating Chats / Contacts / Settings / Profile pill at the bottom of the
screenshots, drawn by `ui/Components/glass/GlassTabView.java`. These five keys
carried a stray `--` prefix that made Telegram ignore them; they were renamed to
their real names in the pruning pass. Telegram also reads `glass_defaultIcon` and
`glass_defaultText`, which the templates still don't cover.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `glass_tabSelected` † | Icon/indicator of the active bottom tab | `#1A91E6` | `pair:on/off` with `glass_tabUnselected`; pairs with `glass_tabSelectedText` |
| `glass_tabSelectedText` † | Label of the active bottom tab | `#0D7FCF` | pairs with `glass_tabSelected` |
| `glass_tabUnselected` † | Icon and label of an inactive bottom tab | `#1A1D21` | `pair:on/off` with `glass_tabSelected` |
| `glass_targetMainTabs` † | The translucent bar body behind the tabs | `#FFFFFF` | `fill→on-fill` with the two tab-state keys |
| `glass_targetMainTopPanel` † | The matching translucent panel at the top of the screen | `#FFFFFF` | `mirror`: `glass_targetMainTabs` |
| `glass_defaultIcon` † | An icon on the glass bar that is neither a selected nor an unselected tab | `#991B2227` | `family`: glass bar; `pair` with `glass_defaultText` |
| `glass_defaultText` † | The matching default label on that bar | `#991B2227` | `pair` with `glass_defaultIcon` |

---

## 3. Chat list

The main screen. Each row is: avatar, name, message preview, timestamp, and a
status marker on the right. Three suffix conventions run through this whole
section and are the relations that matter most:

- **`*Archived`** — the same element while you are inside the Archived Chats list.
- **`*_threeLines`** — the same element when the user has turned on three-line
  chat previews, which needs a different contrast balance.
- **`chats_action*`** — the swipe-action buttons revealed by dragging a row.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chats_name` | Chat title in a row | `#1A1D21` | `mirror`: `chats_nameArchived` |
| `chats_nameArchived` | Chat title inside the archive | `#1A1D21` | `mirror`: `chats_name` |
| `chats_message` | The message-preview line under the name | `#75787A` | `mirror`: `chats_messageArchived`, `chats_message_threeLines` |
| `chats_messageArchived` | Preview line inside the archive | `#919191` | `mirror`: `chats_message` |
| `chats_message_threeLines` | Preview line in three-line mode | `#8E9091` | `mirror`: `chats_message` |
| `chats_nameMessage` | The sender's name prefix in a group preview ("Yevhen:") | `#298ACF` | `mirror`: `chats_nameMessageArchived`, `chats_nameMessage_threeLines` |
| `chats_nameMessageArchived` | That sender prefix inside the archive | `#8B8D8F` | `mirror`: `chats_nameMessage` |
| `chats_nameMessage_threeLines` | That sender prefix in three-line mode | `#424449` | `mirror`: `chats_nameMessage` |
| `chats_nameMessageArchived_threeLines` | Both at once: archive **and** three-line mode | `#5E5E5E` | `mirror`: the three keys above — the four form one `family` |
| `chats_attachMessage` | The preview line when the message is an attachment ("📎 Графік навчання") | `#298ACF` | `mirror`: `chats_nameMessage` — same accent role, different trigger |
| `chats_actionMessage` | The preview line for a service/action message | `#298ACF` | `mirror`: `chats_attachMessage` |
| `chats_draft` | The red "Draft:" prefix on a row | `#DD4B39` | contrast partner of `chats_message` |
| `chats_date` | The timestamp at the right end of a row | `#848688` | `pair` with `chats_date_bold` † |
| `chats_date_bold` † | The same timestamp in its bold weight | `#919395` | `pair` with `chats_date` |
| `chats_muteIcon` | The crossed-out bell on a muted chat | `#BDC1C4` | `family`: row status icons |
| `chats_pinnedIcon` | The pin marker on a pinned chat | `#919294` | `family`: row status icons |
| `chats_pinnedOverlay` | The faint tint over a pinned row's background | `#08000000` | `mirror`: `chats_tabletSelectedOverlay` |
| `chats_tabletSelectedOverlay` | The tint marking the currently-open chat in the tablet two-pane layout | `#0F000000` | `mirror`: `chats_pinnedOverlay` |
| `chats_secretIcon` | The green lock on a secret chat row | `#19B126` | `pair` with `chats_secretName` |
| `chats_secretName` | The chat name of a secret chat (green) | `#00A60E` | `pair` with `chats_secretIcon` |
| `chats_onlineCircle` | The green online dot on an avatar | `#4BCB1C` | `family`: presence markers |
| `chats_verifiedBackground` | The verified badge disc next to a name | `#33A8E6` | `fill→on-fill`: `chats_verifiedCheck`; `mirror`: `profile_verifiedBackground` |
| `chats_verifiedCheck` | The check mark inside that badge | `#FFFFFF` | `fill→on-fill` with `chats_verifiedBackground` |
| `chats_sentCheck` | The single ✓ on a sent outgoing message | `#46AA36` | `family`: delivery ticks with `chats_sentReadCheck`, `chats_sentClock` |
| `chats_sentReadCheck` | The double ✓✓ once read | `#46AA36` | `family`: delivery ticks |
| `chats_sentClock` | The clock icon while still sending | `#75BD5E` | `family`: delivery ticks |
| `chats_sentError` | The red error disc when sending failed | `#D55252` | `fill→on-fill`: `chats_sentErrorIcon` |
| `chats_sentErrorIcon` | The "!" inside that disc | `#FFFFFF` | `fill→on-fill` with `chats_sentError` |
| `chats_unreadCounter` | The unread-count badge | `#229AF0` | `pair:on/off` with `chats_unreadCounterMuted`; `fill→on-fill`: `chats_unreadCounterText` |
| `chats_unreadCounterMuted` | The same badge on a muted chat (gray) | `#BEC3C7` | `pair:on/off` with `chats_unreadCounter` |
| `chats_unreadCounterText` | The number inside the badge | `#FFFFFF` | `fill→on-fill` with both counter fills |
| `chats_mentionIcon` | The "@" badge shown when you are mentioned | `#FFFFFF` | `family`: with `chats_unreadCounter` |
| `topics_unreadCounter` | Unread badge in the forum-topics list | `#4ECC5E` | `mirror`: `chats_unreadCounter` |
| `topics_unreadCounterMuted` | Muted variant of that badge | `#8B8D8F` | `mirror`: `chats_unreadCounterMuted` |
| `chats_tabUnreadActiveBackground` | Unread-count pill on the *selected* chat-folder tab ("Groups 5") | `#66ADE1` | `pair:on/off` with `chats_tabUnreadUnactiveBackground` |
| `chats_tabUnreadUnactiveBackground` | The same pill on an unselected folder tab | `#C5C9CC` | `pair:on/off` with `chats_tabUnreadActiveBackground` |
| `chats_actionBackground` | The floating compose button (and swipe-action buttons) | `#65A9E0` | `pair:on/off` with `chats_actionPressedBackground`; `fill→on-fill`: `chats_actionIcon` |
| `chats_actionPressedBackground` | That button while pressed | `#569DD6` | `pair:on/off` with `chats_actionBackground` |
| `chats_actionIcon` | The icon on that button | `#FFFFFF` | `fill→on-fill` with `chats_actionBackground` |
| `chats_archiveBackground` | The Archive swipe-action fill | `#229AF0` | `fill→on-fill`: `chats_archiveIcon`, `chats_archiveText` |
| `chats_archiveIcon` | The archive-box icon on it | `#FFFFFF` | `fill→on-fill` with `chats_archiveBackground` |
| `chats_archiveText` | The "Archive" label on it | `#FFFFFF` | `fill→on-fill` with `chats_archiveBackground` |
| `chats_archivePinBackground` | The archive row's own background when pinned to the top of the list | `#9FAAB3` | `mirror`: `chats_archiveBackground` |
| `chats_archivePullDownBackground` † | The "Release for archive" banner while pulling the list down — before the threshold | `#C6C9CC` | `pair:on/off` with `chats_archivePullDownBackgroundActive` † |
| `chats_archivePullDownBackgroundActive` † | That banner once the pull passes the threshold | `#229AF0` | `pair:on/off` with `chats_archivePullDownBackground` † |

### Side menu (navigation drawer)

The `*Cats` suffix is the drawer header's fallback state: when no wallpaper is
set, Telegram draws its default patterned header, and these keys apply instead of
their unsuffixed siblings.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `chats_menuBackground` | The drawer body behind the menu items | `#FFFFFF` | `fill→on-fill`: `chats_menuItemText`, `chats_menuItemIcon` |
| `chats_menuItemText` | A drawer item's label | `#444444` | `fill→on-fill` with `chats_menuBackground`; `pair:on/off` with `chats_menuItemCheck` |
| `chats_menuItemIcon` | A drawer item's icon | `#889198` | `fill→on-fill` with `chats_menuBackground` |
| `chats_menuItemCheck` | The check mark on the currently-active drawer item | `#598FBA` | `pair:on/off` with `chats_menuItemText` |
| `chats_menuTopBackground` † | The drawer header block (avatar, name, phone) | — | `mirror`: `chats_menuTopBackgroundCats` |
| `chats_menuTopBackgroundCats` | That header in its default patterned state | `#598FBA` | `mirror`: `chats_menuTopBackground` † |
| `chats_menuTopShadow` | Shadow under the drawer header | — | `mirror`: `chats_menuTopShadowCats` |
| `chats_menuTopShadowCats` | That shadow in the patterned state | — | `mirror`: `chats_menuTopShadow` |
| `chats_menuName` | The user's name in the drawer header | `#FFFFFF` | `fill→on-fill` with `chats_menuTopBackground` † |
| `chats_menuPhone` | The phone number under it | `#FFFFFF` | `mirror`: `chats_menuPhoneCats` |
| `chats_menuPhoneCats` | That phone number in the patterned state | `#C2E5FF` | `mirror`: `chats_menuPhone` |

---

## 4. Avatars

Telegram assigns every user one of seven placeholder colors from their ID, so
these keys come in seven-member families: `Blue`, `Cyan`, `Green`, `Orange`,
`Pink`, `Red`, `Violet`. **A family only reads correctly if all seven members
move together** — leave one behind and one-seventh of your contacts get an
off-palette avatar.

Two families were collapsed in later Telegram versions: `avatar_actionBarIcon*`,
`avatar_actionBarSelector*`, `avatar_backgroundActionBar*` and
`avatar_subtitleInProfile*` now keep only their `Blue` member live, and the other
six were dropped in the pruning. Only the `Blue` member of each remains.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `avatar_backgroundBlue` | Placeholder avatar disc, blue — top of its gradient | `#5CAFFA` | `family`: 7 colors; `pair` with `avatar_background2Blue` |
| `avatar_backgroundCyan` | Placeholder avatar disc, cyan | `#5BCBE3` | `family`; `pair` with `avatar_background2Cyan` |
| `avatar_backgroundGreen` | Placeholder avatar disc, green | `#9AD164` | `family`; `pair` with `avatar_background2Green` |
| `avatar_backgroundOrange` | Placeholder avatar disc, orange | `#FEBB5B` | `family`; `pair` with `avatar_background2Orange` |
| `avatar_backgroundPink` | Placeholder avatar disc, pink | `#FF8AAC` | `family`; `pair` with `avatar_background2Pink` |
| `avatar_backgroundRed` | Placeholder avatar disc, red | `#FF845E` | `family`; `pair` with `avatar_background2Red` |
| `avatar_backgroundViolet` | Placeholder avatar disc, violet | `#B694F9` | `family`; `pair` with `avatar_background2Violet` |
| `avatar_background2Blue` | Bottom of the blue avatar gradient | `#408ACF` | `pair` with `avatar_backgroundBlue`; `family`: the seven `background2` colors |
| `avatar_background2Cyan` | Bottom of the cyan gradient | `#359AD4` | `pair`/`family` as above |
| `avatar_background2Green` | Bottom of the green gradient | `#46BA43` | `pair`/`family` as above |
| `avatar_background2Orange` | Bottom of the orange gradient | `#F68136` | `pair`/`family` as above |
| `avatar_background2Pink` | Bottom of the pink gradient | `#D95574` | `pair`/`family` as above |
| `avatar_background2Red` | Bottom of the red gradient | `#D45246` | `pair`/`family` as above |
| `avatar_background2Violet` | Bottom of the violet gradient | `#6C61DF` | `pair`/`family` as above |
| `avatar_backgroundSaved` | The Saved Messages bookmark avatar — top of gradient | `#69BDF9` | `pair` with `avatar_background2Saved` |
| `avatar_background2Saved` | Bottom of the Saved Messages gradient | `#409FE1` | `pair` with `avatar_backgroundSaved` |
| `avatar_backgroundArchived` | The Archived Chats folder avatar | `#B8C2CC` | `pair` with `avatar_backgroundArchivedHidden`; `pair:on/off` with `avatar_backgroundArchivedHidden` |
| `avatar_backgroundArchivedHidden` | That avatar while the archive row is collapsed/hidden | `#229AF0` | `pair:on/off` with `avatar_backgroundArchived` |
| `avatar_backgroundInProfileBlue` | The avatar backdrop on the profile screen | `#FFFFFF` | `family`: the collapsed `*Blue`-only set |
| `avatar_text` | The initials drawn on a placeholder avatar ("H", "M", "Y") | `#FFFFFF` | `fill→on-fill` with every `avatar_background*` — the one key that must stay readable against all seven |
| `avatar_nameInMessageBlue` | A group member's name above their message bubble, blue | `#368AD1` | `family`: 7 colors; `mirror`: `avatar_backgroundBlue` (same per-user color, different surface) |
| `avatar_nameInMessageCyan` | The same name label, cyan | `#309EBA` | `family` |
| `avatar_nameInMessageGreen` | The same name label, green | `#40A920` | `family` |
| `avatar_nameInMessageOrange` | The same name label, orange | `#D67722` | `family` |
| `avatar_nameInMessagePink` | The same name label, pink | `#C7508B` | `family` |
| `avatar_nameInMessageRed` | The same name label, red | `#CC5049` | `family` |
| `avatar_nameInMessageViolet` | The same name label, violet | `#955CDB` | `family` |
| `avatar_backgroundActionBarBlue` | The profile screen's action bar while showing a blue-avatar user | `#F5F5F5` | `family`: 7; only `Blue` is live |
| `avatar_actionBarIconBlue` | Icons in that profile action bar | `#1A1D21` | `fill→on-fill` with `avatar_backgroundActionBarBlue`; `family`: 7 |
| `avatar_actionBarSelectorBlue` | Press ripple in that profile action bar | `#121A1D21` | `mirror`: `actionBarDefaultSelector`; `family`: 7 |
| `avatar_subtitleInProfileBlue` | The "online"/"last seen" line under the name on a profile | `#1A1D21` | `family`: 7; `pair` with `profile_title` |
