> Part of the [android-theme-key](../SKILL.md) skill's Android key reference. Covers §14 Statistics charts, §15 Instant View, §16 Gifts/stars/Premium/polls, §17 One-off screens, §18 Named palette slots (`color_*`). Relation-tag vocabulary and markers are defined once in `SKILL.md`.

## 14. Statistics charts

The graphs in channel/group statistics. `statisticChartLine_*` are the series
colors; everything else is chart chrome. The series colors share their defaults
exactly with the `color_*` palette in §18 — treat the two sets as one family and
keep them in step.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `statisticChartLine_blue` † | A data series drawn in blue | `#327FE5` | `family`: series colors; `mirror`: `color_blue` |
| `statisticChartLine_lightblue` † | A series in light blue | `#58A8ED` | `family`; `mirror`: `color_lightblue` |
| `statisticChartLine_green` | A series in green | `#61C752` | `family`; `mirror`: `color_green` |
| `statisticChartLine_lightgreen` | A series in light green | `#8FCF39` | `family`; `mirror`: `color_lightgreen` |
| `statisticChartLine_red` | A series in red | `#E05356` | `family`; `mirror`: `color_red` |
| `statisticChartLine_orange` | A series in orange | `#F28C39` | `family`; `mirror`: `color_orange` |
| `statisticChartLine_golden` | A series in gold | `#EBA52D` | `family`; `mirror`: `color_yellow` |
| `statisticChartLine_purple` † | A series in purple | `#9F79E8` | `family`; `mirror`: `color_purple` |
| `statisticChartLine_indigo` | A series in indigo | `#7F79F3` | `family`: series colors |
| `statisticChartLine_cyan` † | A series in cyan | `#40D0CA` | `family`: series colors |
| `statisticChartLineEmpty` † | The line drawn for a series with no data | `#EEEEEE` | `family`: chart chrome |
| `statisticChartActiveLine` | The vertical marker under the touched point | `#33000000` | `pair` with `statisticChartHintLine` † |
| `statisticChartHintLine` † | The horizontal grid lines behind the chart | `#1A182D3B` | `pair` with `statisticChartActiveLine` |
| `statisticChartSignature` † | The axis labels | `#7F252529` | `pair` with `statisticChartSignatureAlpha` † |
| `statisticChartSignatureAlpha` † | The faded state of those labels during a transition | `#7F252529` | `pair` with `statisticChartSignature` † |
| `statisticChartActivePickerChart` † | The selected span in the range picker under the chart | `#D8BACCD9` | `pair:on/off` with `statisticChartInactivePickerChart` † |
| `statisticChartInactivePickerChart` † | The unselected span there | `#99E2EEF9` | `pair:on/off` with `statisticChartActivePickerChart` † |
| `statisticChartChevronColor` † | The drag handles on that range picker | `#D2D5D7` | `family`: range picker |
| `statisticChartBackZoomColor` † | The "zoom out" control after drilling into a chart | `#108BE3` | `family`: chart chrome |
| `statisticChartRipple` † | The press ripple on a chart | `#2C7E9DB7` | `mirror`: `listSelectorSDK21` |

---

## 15. Instant View

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `iv_background` † | The article page's background | `#FFFFFF` | `mirror`: `windowBackgroundWhite` |
| `iv_backgroundGray` † | The recessed background for blocks inside an article | `#F0F0F0` | `mirror`: `windowBackgroundGray` |
| `iv_navigationBackground` † | The article's own navigation bar | `#F0F0F0` | `mirror`: `actionBarDefault` |
| `iv_ab_progress` † | The page-load progress bar under that navigation bar | `#229AF0` | `mirror`: `dialogLineProgress` |

---

## 16. Gifts, stars, Premium, and polls

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `gift_ribbon` † | The corner ribbon on a gift card | `#46A4F2` | `pair:on/off` with `gift_ribbon_soldout` †; `family`: gifts, with `dialogGiftsBackground` † |
| `gift_ribbon_soldout` † | That ribbon once the gift is sold out | `#CC4747` | `pair:on/off` with `gift_ribbon` † |
| `reactionStarSelector` † | The press highlight on the paid (star) reaction in the reaction picker | `#40F0AB1F` | `mirror`: `listSelectorSDK21`; `family`: reactions |
| `pollCreateIcons` † | The icons in the poll-creation sheet's settings rows | `#909599` | `family`: poll creation |


### Telegram Premium

Premium's signature multi-hue gradient, plus the star and coin ornaments that ride
on it. Telegram ships these as a fixed rainbow; the templates map the gradient
stops onto the user's accent instead, the same way the group-call and story
gradients are handled. The numbered stops are gradient positions, not variants.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `premiumGradient0` † | Stop 0 of the Premium feature gradient | `#4ACD43` | `family`: `premiumGradient0`–`4` |
| `premiumGradient1` † | Stop 1 of it | `#55A5FF` | `family`: premium gradient |
| `premiumGradient2` † | Stop 2 of it | `#A767FF` | `family`: premium gradient |
| `premiumGradient3` † | Stop 3 of it | `#DB5C9D` | `family`: premium gradient |
| `premiumGradient4` † | Stop 4 of it | `#F38926` | `family`: premium gradient |
| `premiumGradientBackground1` † | Stop 1 of the wash behind a Premium promo screen | `#55A5FF` | `family`: `premiumGradientBackground1`–`4` |
| `premiumGradientBackground2` † | Stop 2 of it | `#A767FF` | `family`: premium background |
| `premiumGradientBackground3` † | Stop 3 of it | `#DB5C9D` | `family`: premium background |
| `premiumGradientBackground4` † | Stop 4 of it | `#F38926` | `family`: premium background |
| `premiumGradientBackgroundOverlay` † | The scrim laid over that wash so text stays readable | — | `fill→on-fill` with the `premiumGradientBackground*` family |
| `premiumGradientBottomSheet1` † | Stop 1 of the gradient on a Premium bottom sheet | `#5B9DE7` | `family`: `premiumGradientBottomSheet1`–`3`; `mirror`: `premiumGradientBackground1` |
| `premiumGradientBottomSheet2` † | Stop 2 of it | `#AB87DD` | `family`: premium sheet |
| `premiumGradientBottomSheet3` † | Stop 3 of it | `#E794BE` | `family`: premium sheet |
| `premiumStarGradient1` † | Start of the gradient on the Premium star badge | `#FFFFFF` | `pair` with `premiumStarGradient2` |
| `premiumStarGradient2` † | End of it | `#E3ECFA` | `pair` with `premiumStarGradient1` |
| `premiumStartSmallStarsColor` † | The small sparkles scattered around that badge (Telegram's own spelling — "Start", not "Star") | — | `pair` with `premiumStartSmallStarsColor2` |
| `premiumStartSmallStarsColor2` † | The second shade of those sparkles | — | `pair` with `premiumStartSmallStarsColor` |
| `premiumCoinGradient1` † | Start of the gradient on the Premium/Stars coin | — | `pair` with `premiumCoinGradient2`; `family`: with `reactionStarSelector` |
| `premiumCoinGradient2` † | End of it | — | `pair` with `premiumCoinGradient1` |
---

## 17. One-off screens

Small namespaces, one screen each.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `calls_callReceivedGreenIcon` | The received-call arrow in the call log | `#00C853` | `pair` with `calls_callReceivedRedIcon`; `mirror`: `chat_inDownCall` |
| `calls_callReceivedRedIcon` | The missed-call arrow there | `#FF4848` | `pair` with `calls_callReceivedGreenIcon` |
| `returnToCallBackground` | The green "tap to return to call" strip at the top of the screen | `#44A1E3` | `fill→on-fill`: `returnToCallText`; `pair:on/off` with `returnToCallMutedBackground` † |
| `returnToCallText` | The label on that strip | `#FFFFFF` | `fill→on-fill` with `returnToCallBackground` |
| `returnToCallMutedBackground` † | That strip while the call is muted | `#9DA7B1` | `pair:on/off` with `returnToCallBackground` |
| `undo_background` | The dark "Undo" snackbar after deleting something | `#EA272F38` | `fill→on-fill`: `undo_infoColor`, `undo_cancelColor` |
| `undo_infoColor` | The message text in that snackbar | `#FFFFFF` | `fill→on-fill` with `undo_background` |
| `undo_cancelColor` | The "UNDO" action in it | `#85CAFF` | `fill→on-fill` with `undo_background` |
| `contacts_inviteBackground` | The disc on the "Invite friends" row | `#55BE61` | `fill→on-fill`: `contacts_inviteText` |
| `contacts_inviteText` | The label on it | `#FFFFFF` | `fill→on-fill` with `contacts_inviteBackground` |
| `groupcreate_spanBackground` | The chip for a member you have picked while creating a group | `#F2F2F2` | `fill→on-fill`: `groupcreate_spanText`, `groupcreate_spanDelete` |
| `groupcreate_spanText` | The name inside that chip | `#222222` | `fill→on-fill` with `groupcreate_spanBackground` |
| `groupcreate_spanDelete` | The ✕ on that chip | `#FFFFFF` | `fill→on-fill` with `groupcreate_spanBackground` |
| `groupcreate_cursor` | The caret in the member-search field | `#52A3DB` | `mirror`: `chat_messagePanelCursor` |
| `groupcreate_hintText` | The placeholder in that field | `#A1AAB3` | `mirror`: `windowBackgroundWhiteHintText` |
| `groupcreate_sectionText` | A section header in the member list | `#7C8288` | `mirror`: `key_graySectionText` |
| `groupcreate_sectionShadow` | The shadow under that header | `#000000` | `mirror`: `windowBackgroundGrayShadow` |
| `login_progressOuter` | The moving arc of the sign-in spinner | `#62A0D0` | `pair` with `login_progressInner`; `mirror`: `contextProgressOuter1` |
| `login_progressInner` | Its track ring | `#E1EAF2` | `pair` with `login_progressOuter` |
| `passport_authorizeBackground` | The "Authorize" button on a Telegram Passport request | `#45ABEF` | `pair:rest/selected` with `passport_authorizeBackgroundSelected`; `fill→on-fill`: `passport_authorizeText` |
| `passport_authorizeBackgroundSelected` | That button while pressed | `#409DDB` | `pair:rest/selected` with `passport_authorizeBackground` |
| `passport_authorizeText` | The label on it | `#FFFFFF` | `fill→on-fill` with `passport_authorizeBackground` |
| `sessions_devicesImage` | The device illustration on the active-sessions screen | `#969696` | — |
| `changephoneinfo_image2` | The live illustration there | `#229AF0` | — |
| `telegram_color` † | Telegram's own brand blue, for surfaces that must not follow the user's accent | `#229AF0` | `family`: brand colors |
| `telegram_color_text` † | The text-safe darker step of that brand blue | `#298ACF` | `family`: brand colors |
| `telegram_color_dialogsLogo` † | The Telegram logo tint on the empty chat-list state | `#168BDB` | `family`: brand colors |
| `share_icon` † | An icon in the share sheet | `#6E7275` | `family`: share sheet; `mirror`: `chats_menuItemIcon` |
| `share_linkBackground` † | The field holding the copyable link in that sheet | `#0F000000` | `fill→on-fill`: `share_linkText` |
| `share_linkText` † | The link text inside it | `#222222` | `fill→on-fill` with `share_linkBackground` |

---

## 18. Named palette slots (`color_*`)

Eight fixed, semantic colors that the rest of the app reaches for when something
must read as "red" or "green" regardless of the user's accent. They are the
Android counterpart of the status roles in
[`../color-roles.md`](../color-roles.md), and they share their defaults with the
`statisticChartLine_*` series in §14.

| Key | Draws | Telegram default | Relations |
|---|---|---|---|
| `color_red` | The shared "red" slot | `#E05356` | `family`: palette; `mirror`: `statisticChartLine_red` |
| `color_orange` | The shared "orange" slot | `#F28C39` | `family`; `mirror`: `statisticChartLine_orange` |
| `color_yellow` | The shared "yellow" slot | `#EBA52D` | `family`; `mirror`: `statisticChartLine_golden` |
| `color_green` | The shared "green" slot | `#61C752` | `family`; `mirror`: `statisticChartLine_green` |
| `color_lightgreen` | The shared "light green" slot | `#8FCF39` | `family`; `mirror`: `statisticChartLine_lightgreen` |
| `color_blue` | The shared "blue" slot | `#327FE5` | `family`; `mirror`: `statisticChartLine_blue` † |
| `color_lightblue` | The shared "light blue" slot | `#58A8ED` | `family`; `mirror`: `statisticChartLine_lightblue` † |
| `color_purple` | The shared "purple" slot | `#9F79E8` | `family`; `mirror`: `statisticChartLine_purple` † |
| `color_cyan` † | The shared "cyan" slot | `#40D0CA` | `family`: palette; `mirror`: `statisticChartLine_cyan` |
