> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §6 the main Chats screen — `chatList.*`, all 45 keys.
> `PresentationThemeChatList` in `PresentationTheme.swift`. Relation-tag
> vocabulary is defined once in `SKILL.md`. Example values are `Blue Shadow`
> (light) → `Instant Blue` (dark).

## 6. Chat list

Each row is: avatar, title, message preview, timestamp, and a status marker
on the right. This is the iOS counterpart to Android's `chats_*` namespace —
same screen, same information, no `*Archived`/`*_threeLines` suffix families
though, since iOS doesn't theme the archive row or three-line previews
separately from the regular list.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chatList.bg` | The list's own background, behind the rows | `#FFFFFF → #000000` | `mirror`: `chatList.itemBg` |
| `chatList.itemSeparator` | Hairline between rows | `#FFFFFF → #000000` | Same value as `chatList.bg` in both sample themes — iOS draws chat-list separators as full-bleed rather than inset, so this often equals the background |
| `chatList.itemBg` | A regular row's own surface | `#FFFFFF → #000000` | `mirror`: `chatList.bg` |
| `chatList.pinnedItemBg` | A pinned row's surface | `#FFFFFF → #000000` | `mirror`: `chatList.itemBg` — identical in both samples; pinned rows are marked by the pin icon, not a background tint |
| `chatList.itemHighlightedBg` | Press feedback on a tapped/swiped row | `#E5E5EA → #121212` | `pair:rest/pressed` with `chatList.itemBg`; Default opaque `gray_5`/`gray_8`, Soza translucent `tr_gray_5`/`tr_gray_3` |
| `chatList.pinnedItemHighlightedBg` | Press feedback on a pinned row | `#E5E5EA → #2B2B2C` | `mirror`: `chatList.itemHighlightedBg` |
| `chatList.itemSelectedBg` | Background of the row for the chat currently open (iPad split view, or multi-select) | `#E9F0FA → #191919` | resolves through `tr_accent_7` in every template — a faint accent wash, not a gray one |
| `chatList.title` | Chat/contact name | `#000000 → #FFFFFF` | `pair:on/off` with `chatList.secretTitle` |
| `chatList.secretTitle` | That name when the chat is a Secret Chat | `#00B12C → #00B12C` | `pair:on/off` with `chatList.title`; `family` with `chatList.secretIcon` — fixed `green_5` in every template |
| `chatList.dateText` | Timestamp at the right end of a row | `#8E8E93 → #8D8E93` | pairs with `chatList.messageText` |
| `chatList.authorName` | The sender-name prefix in a group's preview line ("James: ") | `#000000 → #FFFFFF` | `mirror`: `chatList.title` — same tier, different context |
| `chatList.messageText` | The message-preview line under the title | `#8E8E93 → #8D8E93` | `pair:on/off` with `chatList.messageHighlightedText` |
| `chatList.messageHighlightedText` | That preview line's bold/emphasized run (e.g. the matched substring in a search result) | `#000000 → #FFFFFF` | `pair:on/off` with `chatList.messageText` |
| `chatList.messageDraftText` | The red "Draft:" prefix on a row with an unsent draft | `#DD4B39 → #DD4B39` | fixed `red_5`; contrast partner of `chatList.messageText` |
| `chatList.checkmark` | Delivery-tick color on the preview line for a chat you sent the last message in | `#007AFF → #007AFF` | `family`: appears once per row, doubled visually for "read" (Telegram draws two ticks with the same color, not a separate key) |
| `chatList.pendingIndicator` | The clock/spinner glyph while a message is still sending | `#8E8E93 → #FFFFFF` | fixed `gray_9` in dark mode — stays visible against the row regardless of mode; `gray_5` in light |
| `chatList.failedFill` | Background disc of the "!" delivery-failed marker | `#FF3B30 → #EB5545` | `fill→on-fill`: `chatList.failedFg` |
| `chatList.failedFg` | The "!" glyph itself | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `chatList.failedFill`; fixed `gray_9` |
| `chatList.muteIcon` | The crossed-out bell on a muted chat's row | `#A7A7AD → #8D8E93` | `family`: row status icons |
| `chatList.unreadBadgeActiveBg` | Fill of the unread-count pill on an unmuted chat | `#007AFF → #007AFF` | `pair:on/off` with `chatList.unreadBadgeInactiveBg`; `fill→on-fill`: `chatList.unreadBadgeActiveText` |
| `chatList.unreadBadgeActiveText` | The number inside that pill | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `chatList.unreadBadgeActiveBg`; resolves through `tt_background`, not a fixed `gray_9`, since the badge fill itself is mode-adaptive `accent_4`/`accent_3` in Default |
| `chatList.unreadBadgeInactiveBg` | Fill of the unread-count pill on a muted chat (gray, not accent) | `#B6B6BB → #666666` | `pair:on/off` with `chatList.unreadBadgeActiveBg` |
| `chatList.unreadBadgeInactiveText` | Number inside that muted pill | `#FFFFFF → #000000` | `fill→on-fill` with `chatList.unreadBadgeInactiveBg`; resolves through `tt_background` rather than `gray_9` — see the must-stay-light pitfall in `templates/ios/CLAUDE.md` (this pill's fill is a plain gray tier, not a saturated one) |
| `chatList.reactionBadgeActiveBg` | Fill of the small badge shown when someone reacts to your message in this chat | `#FF2D55 → #FF2D55` | fixed `red_5` in every template — Telegram's own pink-red, not the user's accent |
| `chatList.pinnedBadge` | The pin glyph on a pinned row | `#B6B6BB → #767677` | `mirror`: `chatList.muteIcon` |
| `chatList.pinnedSearchBar` | Fill behind the search field while the list is scrolled to a pinned section (or a similar pinned-context search state) | `#E5E5E5 → #272728` | `mirror`: `chatList.regularSearchBar` |
| `chatList.regularSearchBar` | Fill behind the search field in the regular chat list | `#E9E9E9 → #272728` | `mirror`: `chatList.pinnedSearchBar` |
| `chatList.sectionHeaderBg` | Background strip of a list section header (e.g. "Archived Chats" pull-down region) | `#FFFFFF → #000000` | `mirror`: `chatList.bg` |
| `chatList.sectionHeaderText` | Text in that header strip | `#6D6D72 → #8D8E93` | `mirror`: `list.sectionHeaderText` |
| `chatList.verifiedIconBg` | Backing disc of the verified badge next to a name | `#007AFF → #007AFF` | `fill→on-fill`: `chatList.verifiedIconFg` |
| `chatList.verifiedIconFg` | The check mark inside that badge | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `chatList.verifiedIconBg`; fixed `gray_9` |
| `chatList.secretIcon` | The lock glyph next to a Secret Chat's name | `#00B12C → #00B12C` | `family`/`pair` with `chatList.secretTitle` — fixed `green_5` |
| `chatList.pinnedArchiveAvatar.background.top` | Top gradient stop of the Archived Chats folder's own avatar tile, while it's pinned to the top of the list | `#72D5FD → #72D5FD` | `pair` with `chatList.pinnedArchiveAvatar.background.bottom`; `mirror`: `chatList.unpinnedArchiveAvatar.*` |
| `chatList.pinnedArchiveAvatar.background.bottom` | Bottom stop of that gradient | `#2A9EF1 → #2A9EF1` | `pair` with `.background.top` |
| `chatList.pinnedArchiveAvatar.foreground` | The archive-box glyph drawn on that avatar | `#FFFFFF → #FFFFFF` | `fill→on-fill` with the gradient; fixed `gray_9` |
| `chatList.unpinnedArchiveAvatar.background.top` | Top gradient stop of the same avatar while collapsed/unpinned (neutral gray instead of blue) | `#DEDEE5 → #666666` | `pair` with `.background.bottom`; `mirror`: `chatList.pinnedArchiveAvatar.background.top` |
| `chatList.unpinnedArchiveAvatar.background.bottom` | Bottom stop of that gray gradient | `#C5C6CC → #666666` | `pair` with `.background.top` |
| `chatList.unpinnedArchiveAvatar.foreground` | The archive-box glyph on the unpinned state | `#FFFFFF → #000000` | resolves through `tt_background`, not `gray_9` — the fill it sits on is a plain gray tier here, not a saturated one; see the `gray_9` pitfall note in `templates/ios/CLAUDE.md` |
| `chatList.onlineDot` | The green "online" dot on a user's avatar | `#4CC91F → #4CC91F` | fixed `green_5` in every template |
| `chatList.storyUnseen.top` | Top stop of the colored ring drawn around an avatar with an unseen Story | `#34C76F → #34C76F` | `pair` with `chatList.storyUnseen.bottom`; `family` with `storyUnseenPrivate`/`storySeen` |
| `chatList.storyUnseen.bottom` | Bottom stop of that ring's gradient | `#3DA1FD → #3DA1FD` | `pair` with `.storyUnseen.top` |
| `chatList.storyUnseenPrivate.top` | Top stop of the ring for an unseen Story visible only to close friends | `#7CD636 → #7CD636` | `pair` with `.storyUnseenPrivate.bottom`; `mirror`: `storyUnseen.top` — a distinct green so a close-friends-only story is visually distinguishable |
| `chatList.storyUnseenPrivate.bottom` | Bottom stop of that gradient | `#26B470 → #26B470` | `pair` with `.storyUnseenPrivate.top` |
| `chatList.storySeen.top` | Top stop of the ring once every Story on it has been viewed (dims to gray) | `#D8D8E1 → #48484A` | `pair` with `.storySeen.bottom`; `pair:on/off` with `storyUnseen.top` |
| `chatList.storySeen.bottom` | Bottom stop of that dimmed ring | `#D8D8E1 → #48484A` | `pair` with `.storySeen.top` — identical to `.top` in every template, i.e. a flat gray ring rather than a gradient once seen |
