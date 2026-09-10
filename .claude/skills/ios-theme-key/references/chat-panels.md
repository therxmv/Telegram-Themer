> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §8 service messages, §9 the text composer, §10 the sticker/GIF/emoji
> panel, §11 the reply-keyboard button panel, §12 jump-to-bottom, §13 two
> chat-wide switches. 75 of 417 keys — everything on the chat screen that
> isn't a message bubble. Relation-tag vocabulary is defined once in
> `SKILL.md`. Example values are `Blue Shadow` (light) → `Instant Blue`
> (dark).

## 8. Service messages — `chat.serviceMessage.*`

The centered, pill-shaped system messages ("Dwayne joined the group", date
dividers, the unread-messages divider). `PresentationThemeServiceMessage`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.serviceMessage.components.withDefaultWp.bg` | Service-message pill fill, when the chat's wallpaper is literally plain white (`color(0xffffff)`) — Telegram's definition of "default", not merely "no custom wallpaper set" | `#CCFFFFFF → #1F1F1F` | `pair:withDefaultWp/withCustomWp` with `.withCustomWp.bg` |
| `chat.serviceMessage.components.withDefaultWp.primaryText` | Text inside that pill | `#8D8E93 → #FFFFFF` | `fill→on-fill` with `.withDefaultWp.bg` |
| `chat.serviceMessage.components.withDefaultWp.linkHighlight` | Highlight behind a long-pressed link inside a service message (e.g. tapping a linked group-rules message) | `#3F748391 → #1EFFFFFF` | pairs with `.withDefaultWp.primaryText` |
| `chat.serviceMessage.components.withDefaultWp.scam` | "SCAM"/"FAKE" text inside a service message | `#FF3B30 → #EB5545` | fixed `red_5` |
| `chat.serviceMessage.components.withDefaultWp.dateFillStatic` | Fill of the floating date-divider pill ("Today", "March 3") while the list isn't actively scrolling | `#CCFFFFFF → #33000000` | `pair:on/off` with `.dateFillFloat` |
| `chat.serviceMessage.components.withDefaultWp.dateFillFloat` | Fill of that same pill while it's floating over content during an active scroll | `#CCFFFFFF → #33000000` | `pair:on/off` with `.dateFillStatic` — identical value in every template; the fork exists in source for cases (patterned wallpapers) that don't apply to a plain white background |
| `chat.serviceMessage.components.withCustomWp.bg` | Service-message pill fill over any wallpaper that isn't plain white — a photo, gradient, or Telegram's built-in pattern | `#66A5A5A5 → #1F1F1F` | `pair:withDefaultWp/withCustomWp` with `.withDefaultWp.bg` |
| `chat.serviceMessage.components.withCustomWp.primaryText` | Text inside that pill | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.withCustomWp.bg`; fixed `gray_9` — must stay legible over an arbitrary photo |
| `chat.serviceMessage.components.withCustomWp.linkHighlight` | Link-press highlight | `#3F748391 → #1EFFFFFF` | `mirror`: `.withDefaultWp.linkHighlight` |
| `chat.serviceMessage.components.withCustomWp.scam` | "SCAM"/"FAKE" text | `#FF3B30 → #EB5545` | fixed `red_5` |
| `chat.serviceMessage.components.withCustomWp.dateFillStatic` | Date-divider pill fill, static | `#33000000 → #33000000` | `pair:on/off` with `.dateFillFloat` |
| `chat.serviceMessage.components.withCustomWp.dateFillFloat` | Date-divider pill fill, floating during scroll | `#44A5A5A5 → #33000000` | `pair:on/off` with `.dateFillStatic` — this is the one row where static and floating genuinely diverge in the light sample |
| `chat.serviceMessage.unreadBarBg` | Fill of the "Unread Messages" divider bar | `#00FFFFFF → #001B1B1B` | fixed `transparent_0` in every template — Telegram draws this bar's text directly on the wallpaper with no pill behind it |
| `chat.serviceMessage.unreadBarStroke` | Border of that bar | `#00FFFFFF → #001B1B1B` | fixed `transparent_0`, same reasoning as `.unreadBarBg` |
| `chat.serviceMessage.unreadBarText` | "Unread Messages" label text | `#8D8E93 → #FFFFFF` | since the bar itself is transparent, this text's contrast depends entirely on the wallpaper — kept a plain gray tier in every template |
| `chat.serviceMessage.dateText.withWp` | Section-header date label ("March 3") when it's not inside a pill, over a wallpaper | `#FFFFFF → #FFFFFF` | fixed `gray_9` |
| `chat.serviceMessage.dateText.withoutWp` | Same, over a plain background | `#8D8E93 → #FFFFFF` | Default's light value is a plain gray tier here, not `gray_9` — no wallpaper means no legibility risk |

## 9. The text composer — `chat.inputPanel.*`

The bar at the bottom of a chat: the "Message" text field, attach/mic
buttons, and the recording UI. `PresentationThemeChatInputPanel`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.inputPanel.panelBg` | The composer bar's own translucent material | `#E5F2F2F2 → #E51D1D1D` | `mirror`: `root.navBar.background`/`root.tabBar.background` — all three resolve through `tr_background_9` |
| `chat.inputPanel.panelSeparator` | Hairline above the composer | `#BEC2C6 → #8C545458` | pairs with `.panelBg` |
| `chat.inputPanel.panelControlAccent` | Accent tint of an interactive composer control (e.g. the "Bot" command icon) | `#007AFF → #007AFF` | `mirror`: `root.navBar.button` |
| `chat.inputPanel.panelControl_v2` | Neutral tint of a composer control that isn't accent-highlighted (attach clip, mic when idle) | `#000000 → #FFFFFF` | `pair:on/off` with `.panelControlDisabled` |
| `chat.inputPanel.panelControlDisabled` | That control's disabled state (e.g. attach button while a slow-mode limit is active) | `#7F727B87 → #7F808080` | `pair:on/off` with `.panelControl_v2` |
| `chat.inputPanel.panelControlDestructive` | Destructive-tinted composer control (e.g. a "leave conversation" prompt shown inline) | `#FF3B30 → #FF3B30` | fixed `red_5` |
| `chat.inputPanel.inputBg` | The rounded text-field pill itself | `#CCFFFFFF → #F2232323` | `fill→on-fill`: `.inputText_v2`/`.inputPlaceholder_v2` |
| `chat.inputPanel.inputStroke` | Border of that pill | `#19000000 → #19FFFFFF` | pairs with `.inputBg` |
| `chat.inputPanel.inputPlaceholder_v2` | "Message" placeholder text | `#66000000 → #7AFFFFFF` | pairs with `.inputText_v2` |
| `chat.inputPanel.inputText_v2` | Typed message text | `#000000 → #FFFFFF` | `fill→on-fill` with `.inputBg` |
| `chat.inputPanel.inputControl_v2` | A control glyph inside the field itself (e.g. the inline emoji-picker toggle) | `#7F000000 → #7FFFFFFF` | `family` with `.inputPlaceholder_v2` |
| `chat.inputPanel.actionControlBg` | Fill of the send button once text is entered | `#007AFF → #007AFF` | `fill→on-fill`: `.actionControlFg` |
| `chat.inputPanel.actionControlFg` | The send-arrow glyph on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.actionControlBg`; fixed `gray_9` |
| `chat.inputPanel.primaryText` | Text in a composer state that replaces the field (e.g. an inline "Slow mode: wait 30s" label) | `#000000 → #FFFFFF` | `mirror`: `.inputText_v2` |
| `chat.inputPanel.secondaryText` | Muted text in that same context, or the composer's "N is typing…" indicator | `#99202020 → #7FFFFFFF` | pairs with `.primaryText` |
| `chat.inputPanel.mediaRecordDot` | The pulsing red recording dot shown while holding the mic/video button | `#ED2521 → #EB5545` | fixed `red_5` |
| `chat.inputPanel.mediaRecordControl.button` | Fill of the mic/video-message record button | `#007AFF → #007AFF` | `fill→on-fill`: `.mediaRecordControl.activeIcon` |
| `chat.inputPanel.mediaRecordControl.micLevel` | The expanding ring around that button that pulses with input volume while recording | `#33007AFF → #33007AFF` | resolves through `tr_accent_5`; pairs with `.mediaRecordControl.button` |
| `chat.inputPanel.mediaRecordControl.activeIcon` | The mic/camera glyph on the button | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.mediaRecordControl.button`; fixed `gray_9` |

## 10. The sticker / GIF / emoji panel — `chat.inputMediaPanel.*`

The "EntityKeyboard" — the panel that replaces the system keyboard when you
tap the emoji-face icon, with its own tab strip and search field.
`PresentationThemeInputMediaPanel`. The `panelContentVibrant*` /
`panelContentOpaque*` split exists because this panel's chrome is drawn with
a `UIVisualEffectView` vibrancy effect over the keyboard blur by default —
`Vibrant*` keys tint content through that effect, `Opaque*` keys are the
flat fallback used where vibrancy isn't applied (e.g. Reduce Transparency).

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.inputMediaPanel.panelSeparator` | Hairline between the panel and the composer above it | `#BEC2C6 → #8C545458` | `mirror`: `chat.inputPanel.panelSeparator` |
| `chat.inputMediaPanel.panelIcon` | An unselected tab icon in the panel's top strip (Recent, Stickers, GIFs…) | `#858E99 → #808080` | `pair:on/off` with `.panelHighlightedIcon` |
| `chat.inputMediaPanel.panelHighlightedIconBg` | Background pill behind the currently-selected tab icon | `#33858E99 → #3F808080` | `fill→on-fill`: `.panelHighlightedIcon` |
| `chat.inputMediaPanel.panelHighlightedIcon` | The selected tab's icon color | `#4D5561 → #ACACAC` | `pair:on/off` with `.panelIcon`; `fill→on-fill` with `.panelHighlightedIconBg` |
| `chat.inputMediaPanel.panelContentVibrantOverlay` | Vibrancy-tinted overlay for section-header text/subtitles inside the panel (e.g. a sticker pack's name) | `#A5999999 → #808080` | `pair:withWp/withoutWp`-like split with `.panelContentControlVibrantOverlay` — this one is content, that one is a control |
| `chat.inputMediaPanel.panelContentControlVibrantOverlay` | Vibrancy-tinted overlay for a control inside the panel content (e.g. a pack-settings gear) | `#A5D8D8D8 → #535353` | pairs with `.panelContentVibrantOverlay` |
| `chat.inputMediaPanel.panelContentControlVibrantSelection` | Vibrancy-tinted press/selection feedback on that control | `#19D8D8D8 → #19FFFFFF` | `pair:rest/pressed` with `.panelContentControlVibrantOverlay` |
| `chat.inputMediaPanel.panelContentControlOpaqueOverlay` | Flat (non-vibrancy) fallback of `.panelContentControlVibrantOverlay` | `#33000000 → #19FFFFFF` | `mirror`: `.panelContentControlVibrantOverlay` |
| `chat.inputMediaPanel.panelContentControlOpaqueSelection` | Flat fallback of `.panelContentControlVibrantSelection` | `#0F000000 → #19FFFFFF` | `mirror`: `.panelContentControlVibrantSelection` |
| `chat.inputMediaPanel.panelContentVibrantSearchOverlay` | Vibrancy-tinted overlay inside the panel's own search field | `#8C999999 → #808080` | `mirror`: `.panelContentVibrantOverlay`, search-field context |
| `chat.inputMediaPanel.panelContentVibrantSearchOverlaySelected` | Same, while a search category chip is selected | `#99666666 → #808080` | `pair:on/off` with `.panelContentVibrantSearchOverlay` |
| `chat.inputMediaPanel.panelContentVibrantSearchOverlayHighlight` | Press feedback on a search-field control | `#05333333 → #3F808080` | `pair:rest/pressed` with `.panelContentVibrantSearchOverlay` |
| `chat.inputMediaPanel.panelContentOpaqueSearchOverlay` | Flat fallback of `.panelContentVibrantSearchOverlay` | `#8E8E93 → #808080` | `mirror`: `.panelContentVibrantSearchOverlay` |
| `chat.inputMediaPanel.panelContentOpaqueSearchOverlaySelected` | Flat fallback of `.panelContentVibrantSearchOverlaySelected` | `#66000000 → #808080` | `mirror`: `.panelContentVibrantSearchOverlaySelected` |
| `chat.inputMediaPanel.panelContentOpaqueSearchOverlayHighlight` | Flat fallback of `.panelContentVibrantSearchOverlayHighlight` | `#19000000 → #3F808080` | `mirror`: `.panelContentVibrantSearchOverlayHighlight` |
| `chat.inputMediaPanel.stickersBg` | Background of the Stickers tab's content area | `#E8EBF0 → #A9232323` | `mirror`: `.gifsBg` |
| `chat.inputMediaPanel.stickersSectionText` | A sticker pack's section-header label | `#9099A2 → #7B7B7B` | `mirror`: `list.sectionHeaderText` |
| `chat.inputMediaPanel.stickersSearchBg` | Fill of the search field inside the Stickers tab | `#D9DBE1 → #1C1C1D` | pairs with `.stickersSearchPrimary` |
| `chat.inputMediaPanel.stickersSearchPlaceholder` | Placeholder text in it | `#8E8E93 → #8D8E93` | pairs with `.stickersSearchPrimary` |
| `chat.inputMediaPanel.stickersSearchPrimary` | Typed text in it | `#000000 → #FFFFFF` | `fill→on-fill` with `.stickersSearchBg` |
| `chat.inputMediaPanel.stickersSearchControl` | A control glyph in that field (magnifier, clear button) | `#8E8E93 → #8D8E93` | `family` with `.stickersSearchPlaceholder` |
| `chat.inputMediaPanel.gifsBg` | Background of the GIFs tab's content area | `#FFFFFF → #A9232323` | `mirror`: `.stickersBg` |
| `chat.inputMediaPanel.bg` | The panel's own overall translucent material, behind everything above | `#B2FFFFFF → #A9232323` | resolves through `tr_background_9`, same role as `chat.inputPanel.panelBg` |

## 11. Reply-keyboard button panel — `chat.inputButtonPanel.*`

The row of custom reply-keyboard buttons a bot can show above the composer
(distinct from the emoji/sticker panel). `PresentationThemeInputButtonPanel`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.inputButtonPanel.panelBg` | The panel's translucent material | `#CCDDDFD7 → #D8141414` | resolves through `tr_background_9` |
| `chat.inputButtonPanel.panelSeparator` | Hairline above the panel | `#B2B2B2 → #4C545458` | pairs with `.panelBg` |
| `chat.inputButtonPanel.buttonBg` | Fill of one reply-keyboard button | `#FFFFFF → #D8E9E9E9` | `pair:rest/pressed` with `.buttonHighlightedBg` |
| `chat.inputButtonPanel.buttonHighlight` | A subtle highlight drawn on the button's own fill (a top-edge sheen), independent of the pressed state | `#33FFFFFF → #0CFFFFFF` | pairs with `.buttonBg` |
| `chat.inputButtonPanel.buttonStroke` | Button border | `#33353535 → #D8000000` | pairs with `.buttonBg` |
| `chat.inputButtonPanel.buttonHighlightedBg` | Button fill while pressed | `#A8B3C0 → #B25A5A5A` | `pair:rest/pressed` with `.buttonBg` |
| `chat.inputButtonPanel.buttonHighlightedStroke` | Button border while pressed | `#C3C7C9 → #0C0C0C` | `pair:rest/pressed` with `.buttonStroke` |
| `chat.inputButtonPanel.buttonText` | Label text on a button | `#000000 → #FFFFFF` | `fill→on-fill` with `.buttonBg` |

## 12. Jump-to-bottom control — `chat.historyNav.*`

The floating circular button that appears when scrolled up in a chat, with
an optional unread-count badge. `PresentationThemeChatHistoryNavigation`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.historyNav.bg` | The button's own fill | `#F7F7F7 → #1C1C1D` | `mirror`: `root.navBar.opaqueBackground` |
| `chat.historyNav.stroke` | Its border | `#C8C7CC → #8C545458` | pairs with `.bg` |
| `chat.historyNav.fg` | The down-arrow glyph | `#88888D → #FFFFFF` | `fill→on-fill` with `.bg` |
| `chat.historyNav.badgeBg` | Fill of the unread-count badge on the button, if there are unread messages below | `#007AFF → #007AFF` | `fill→on-fill`: `.badgeText` |
| `chat.historyNav.badgeStroke` | Ring around that badge | `#007AFF → #007AFF` | pairs with `.badgeBg` — identical value, i.e. no visible separation ring here (unlike `root.tabBar.badgeStroke`) |
| `chat.historyNav.badgeText` | The number inside the badge | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.badgeBg`; fixed `gray_9` |

## 13. Two chat-wide switches

Not colors — flags that change how the chat screen behaves, sitting
directly under `chat.*` alongside the namespaces above.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.defaultWallpaper` | The chat background used when no wallpaper is explicitly set for a conversation — an `AARRGGBB`-format solid fill (Telegram's `TelegramWallpaper.color` case) rather than a role name pointer in the raw export, though the templates resolve it to `tt_background` like everything else | `#FFFFFFFF → #FF000000` | Everything under `chat.serviceMessage.components.withDefaultWp` only applies when this resolves to literal white |
| `chat.animateMessageColors` | **Literal boolean.** Whether the outgoing bubble's gradient stops animate (a slow shimmer between `bg` and `gradientBg`) instead of sitting static | `false → false` (Default) / `true → true` (Soza) | Only meaningful when a bubble actually has two distinct gradient stops |
