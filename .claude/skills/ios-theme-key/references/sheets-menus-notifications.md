> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §14 action sheets, §15 the long-press context menu, §16 in-app and
> expanded notification banners. 36 of 417 keys. Relation-tag vocabulary is
> defined once in `SKILL.md`. Example values are `Blue Shadow` (light) →
> `Instant Blue` (dark).

## 14. Action sheets — `actionSheet.*`

The bottom sheet with a stack of action rows (share sheet, "Delete for
everyone?", the attach-menu picker). `PresentationThemeActionSheet`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `actionSheet.dim` | The dimming scrim behind the sheet, over the rest of the screen | `#66000000 → #7F000000` | resolves through `tr_gray_3` |
| `actionSheet.bgType` | **Literal**: `light` or `dark` — tells Telegram whether to draw the sheet's system-provided chrome (e.g. the blur style) as light or dark, independent of the sheet's actual colors below | `light → dark` | `alias`: `notification.expanded.bgType` |
| `actionSheet.opaqueItemBg` | Fill of an action group once fully opaque (not over a blur) | `#FFFFFF → #1C1C1D` | `mirror`: `.itemBg` |
| `actionSheet.itemBg` | Fill of an action group in its normal translucent state | `#CCFFFFFF → #CC1C1C1D` | resolves through `tr_background_9`; `mirror`: `.opaqueItemBg` |
| `actionSheet.opaqueItemHighlightedBg` | Press feedback on a row, opaque variant | `#E5E5E5 → #000000` | `pair:rest/pressed` with `.opaqueItemBg` |
| `actionSheet.itemHighlightedBg` | Press feedback on a row, translucent variant | `#B2E5E5E5 → #7F000000` | `pair:rest/pressed` with `.itemBg` |
| `actionSheet.opaqueItemSeparator` | Hairline between rows | `#E5E5E5 → #8C545458` | pairs with `.opaqueItemBg` |
| `actionSheet.standardActionText` | Label of a normal (non-destructive) action row | `#007AFF → #007AFF` | `pair:on/off` with `.destructiveActionText`/`.disabledActionText` |
| `actionSheet.destructiveActionText` | Label of a destructive action row ("Delete", "Report") | `#FF3B30 → #EB5545` | fixed `red_5`; `mirror`: `list.destructive` |
| `actionSheet.disabledActionText` | Label of a disabled action row | `#B3B3B3 → #4D4D4D` | `pair:on/off` with `.standardActionText` |
| `actionSheet.primaryText` | Title text of the sheet itself (when it has one, e.g. a confirmation prompt's headline) | `#000000 → #FFFFFF` | pairs with `.secondaryText` |
| `actionSheet.secondaryText` | Subtitle/explanatory text under that title | `#8E8E93 → #5E5E5E` | pairs with `.primaryText` |
| `actionSheet.controlAccent` | Accent tint for a control embedded in the sheet (e.g. a switch inside a confirmation dialog) | `#007AFF → #007AFF` | `mirror`: `list.accent` |
| `actionSheet.inputBg` | Background of a text field embedded in the sheet, translucent variant | `#99FFFFFF → #0F0F0F` | `mirror`: `.inputHollowBg` |
| `actionSheet.inputHollowBg` | Same field, opaque/"hollow" variant | `#FFFFFF → #0F0F0F` | `mirror`: `.inputBg` |
| `actionSheet.inputBorder` | Border of that field | `#E4E4E6 → #0F0F0F` | pairs with `.inputBg` |
| `actionSheet.inputPlaceholder` | Placeholder text in it | `#8E8D92 → #8F8F8F` | pairs with `.inputText` |
| `actionSheet.inputText` | Typed text in it | `#000000 → #FFFFFF` | `fill→on-fill` with `.inputBg` |
| `actionSheet.inputClearButton` | Its clear-field "✕" button | `#9E9EA1 → #8F8F8F` | `family` with `.inputPlaceholder` |
| `actionSheet.checkContent` | Checkmark glyph on a checkbox-style row inside the sheet | `#FFFFFF → #FFFFFF` | fixed `gray_9` |

## 15. Context menu — `contextMenu.*`

The floating menu that appears on a long-press (a message, a chat-list row,
a link) with an optional preview above it.
`PresentationThemeContextMenu`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `contextMenu.dim` | Dimming scrim behind the menu and its preview | `#33000A26 → #99000000` | `mirror`: `actionSheet.dim` |
| `contextMenu.background` | The menu's own translucent material | `#C6F9F9F9 → #C6252525` | resolves through `tr_background_9` |
| `contextMenu.itemSeparator` | Hairline between menu items | `#333C3C43 → #26FFFFFF` | pairs with `.background` |
| `contextMenu.sectionSeparator` | Thicker divider between menu sections (e.g. between the action list and a destructive action grouped separately) | `#338A8A8A → #33000000` | `mirror`: `.itemSeparator`, heavier weight |
| `contextMenu.itemBg` | An item's own fill at rest | `#00000000 → #00000000` | fixed `transparent_0` — items are transparent until highlighted |
| `contextMenu.itemHighlightedBg` | Press/hover feedback on a menu item | `#333C3C43 → #26FFFFFF` | `pair:rest/pressed` with `.itemBg` |
| `contextMenu.primary` | Standard item label | `#000000 → #FFFFFF` | `pair:on/off` with `.destructive` |
| `contextMenu.secondary` | Muted item label (a disabled or informational item) | `#7F000000 → #7FFFFFFF` | resolves through `tr_gray_5` |
| `contextMenu.destructive` | Destructive item label ("Delete Message") | `#FF3B30 → #EB5545` | fixed `red_5`; `mirror`: `actionSheet.destructiveActionText` |

## 16. Notifications — `notification.*`

The compact banner that drops from the top of the screen for an incoming
message, and its expanded/Notification Center form.
`PresentationThemeInAppNotification`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `notification.bg` | Fill of the compact in-app banner | `#FFFFFF → #1C1C1D` | `fill→on-fill`: `.primaryText` |
| `notification.primaryText` | Text in that banner (sender + message preview) | `#000000 → #FFFFFF` | `fill→on-fill` with `.bg` |
| `notification.expanded.bgType` | **Literal**: `light` or `dark` — the expanded notification's system chrome style | `light → dark` | `alias`: `actionSheet.bgType` |
| `notification.expanded.navBar.background` | Nav bar background inside the expanded notification view | `#FFFFFF → #1C1C1D` | `mirror`: `root.navBar.opaqueBackground` |
| `notification.expanded.navBar.primaryText` | Title text in that bar | `#000000 → #FFFFFF` | `fill→on-fill` with `.navBar.background` |
| `notification.expanded.navBar.control` | Control glyph tint in that bar (close button, etc.) | `#7E8791 → #FFFFFF` | `mirror`: `root.navBar.control` |
| `notification.expanded.navBar.separator` | Hairline under that bar | `#C8C7CC → #000000` | pairs with `.navBar.background` |
