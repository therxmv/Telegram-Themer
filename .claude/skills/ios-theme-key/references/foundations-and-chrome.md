> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §1 Intro/onboarding, §2 Passcode lock, §3 Root chrome (status bar, tab bar,
> nav bar, search bar, keyboard), §4 `list.*` (Settings-style screens), §5
> `chart.*` (Premium statistics). Relation-tag vocabulary is defined once in
> `SKILL.md`, not repeated here. Example values are `Blue Shadow` (light) →
> `Instant Blue` (dark), the two real `.tgios-theme` exports this reference
> is grounded in — see `patterns-and-provenance.md`.

## 1. Intro / onboarding

The swiping "Fast", "Secure", "Powerful" screens shown before login, and the
phone-number entry screen after. `PresentationThemeIntro` in
`PresentationTheme.swift`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `intro.statusBar` | **Literal**, not a color: `black` or `white`, the status-bar style over the intro screens | `black → white` | `alias`: `root.statusBar` (same concept, different screen) |
| `intro.primaryText` | The big headline text on each intro page ("Local Stickers", "Powerful") | `#000000 → #FFFFFF` | `fill→on-fill` with the page background |
| `intro.accentText` | The subtitle line under the headline, and the page-dot indicator's active state color family | `#007AFF → #007AFF` | `family` with `intro.dot` |
| `intro.disabledText` | Muted/disabled text on the intro flow (e.g. a not-yet-available option) | `#D0D0D0 → #525252` | `mirror`: `root.navBar.disabledButton` |
| `intro.startButton` | Fill of the "Start Messaging" button on the last intro page | `#2CA5E0 → #007AFF` | `fill→on-fill`: white label text |
| `intro.dot` | The inactive page-indicator dots under the intro carousel | `#D9D9D9 → #5E5E5E` | `family` with `intro.accentText` (active dot) |

## 2. Passcode lock

The full-screen numeric passcode/Face ID unlock screen.
`PresentationThemePasscode`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `passcode.bg.top` | Top stop of the passcode screen's background gradient | `#46739E → #000000` | `pair` with `passcode.bg.bottom` |
| `passcode.bg.bottom` | Bottom stop of that gradient | `#2A5982 → #000000` | `pair` with `passcode.bg.top` |
| `passcode.button` | Fill of the passcode keypad's digit buttons. `clear` in both sample themes — Telegram draws an outlined/translucent button rather than a solid fill when this is transparent | `clear → #1C1C1D` | Default style keeps this `transparent_0` in both modes; Soza commits it to `accent_5` — see `templates/ios/soza/CLAUDE.md` |

## 3. Root chrome

The `PresentationThemeRootController` tree: the always-present shell around
every screen — status bar, bottom tab bar, top nav bar, search bar, and the
system keyboard's light/dark appearance.

### Status bar and keyboard

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `root.statusBar` | **Literal**: `black` or `white` — the color of the system status-bar glyphs (clock, battery, signal) everywhere outside the intro flow | `black → white` | `alias`: `intro.statusBar` |
| `root.keyboard` | **Literal**: `light` or `dark` — `UIKeyboardAppearance` for every text field in the app, including the composer. Doesn't have to match `dark`; a theme can force a dark keyboard on a light theme or vice versa | `light → dark` | independent of every color key |

### Tab bar

The bottom Chats / Contacts / Calls / Settings bar, always visible.
`PresentationThemeRootTabBar`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `root.tabBar.background` | The translucent bar material itself | `#E5F2F2F2 → #E51D1D1D` | resolves through `tr_background_9` in both templates — see `templates/ios/CLAUDE.md` |
| `root.tabBar.separator` | Hairline above the tab bar | `#B2B2B2 → #8C545458` | pairs with `root.tabBar.background` |
| `root.tabBar.icon` | Icon of an unselected tab | `#959595 → #FFFFFF` | `pair:on/off` with `root.tabBar.selectedIcon` |
| `root.tabBar.selectedIcon` | Icon of the active tab | `#007AFF → #007AFF` | `pair:on/off` with `root.tabBar.icon`; pairs with `root.tabBar.selectedText` |
| `root.tabBar.text` | Label of an unselected tab | `#CC000000 → #FFFFFF` | `pair:on/off` with `root.tabBar.selectedText` |
| `root.tabBar.selectedText` | Label of the active tab | `#007AFF → #007AFF` | `pair:on/off` with `root.tabBar.text` |
| `root.tabBar.badgeBackground` | Fill of the unread-count badge on a tab (e.g. Chats) | `#FF3B30 → #EB5545` | `fill→on-fill`: `root.tabBar.badgeText`; `family`: `root.navBar.badgeFill` |
| `root.tabBar.badgeStroke` | Ring drawn around that badge, separating it from the icon behind it | `#FF3B30 → #1C1C1D` | Default keeps this equal to the badge fill in light mode but equal to `tt_background` in dark (a visible ring against a dark badge on a dark bar) |
| `root.tabBar.badgeText` | The number inside the badge | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `root.tabBar.badgeBackground`; always `gray_9` — must stay light on a saturated red fill in both modes |

### Navigation bar

The top bar on every pushed screen — title, back button, bar-button items —
plus its multi-select "segmented control" state.
`PresentationThemeRootNavigationBar`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `root.navBar.button` | A bar-button item's tint (Cancel, Edit, a plain-text action) | `#007AFF → #007AFF` | `pair:on/off` with `root.navBar.disabledButton` |
| `root.navBar.disabledButton` | The same button while disabled | `#D0D0D0 → #525252` | `pair:on/off` with `root.navBar.button` |
| `root.navBar.primaryText` | The screen/chat title in the bar | `#000000 → #FFFFFF` | `fill→on-fill` with `root.navBar.background`/`opaqueBackground` |
| `root.navBar.secondaryText` | The subtitle line under the title ("last seen recently", "3 members") | `#787878 → #7FFFFFFF` | pairs with `root.navBar.primaryText` |
| `root.navBar.control` | Chevrons and other neutral control glyphs in the bar that aren't a text bar-button item | `#7E8791 → #767676` | Default keeps this a gray tier; Soza pushes it to `accent_5` — see `templates/ios/soza/CLAUDE.md` |
| `root.navBar.accentText` | An accent-tinted label in the bar distinct from the default button tint (used when a screen wants its own accent rather than the global one) | `#007AFF → #007AFF` | `mirror`: `root.navBar.button` |
| `root.navBar.background` | The bar's translucent material while scrolled — the normal state | `#E5F2F2F2 → #E51D1D1D` | `mirror`: `root.tabBar.background`; both resolve through `tr_background_9` |
| `root.navBar.opaqueBackground` | The bar once fully opaque — at the top of a scroll view, or wherever Telegram forces a solid bar instead of a blurred one | `#F8F8F8 → #1A1A1A` | `mirror`: `root.navBar.background`, one step more solid |
| `root.navBar.separator` | Hairline under the bar | `#C8C7CC → #8C545458` | pairs with `root.navBar.background` |
| `root.navBar.badgeFill` | Fill of a numeric badge shown on a nav-bar icon (e.g. unread count on a folder's bar) | `#FF3B30 → #EB5545` | `family`: `root.tabBar.badgeBackground` |
| `root.navBar.badgeStroke` | Ring around that badge | `#FF3B30 → #1C1C1D` | `mirror`: `root.tabBar.badgeStroke` |
| `root.navBar.badgeText` | Number inside that badge | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `root.navBar.badgeFill` |
| `root.navBar.segmentedBg` | Track/background of a `UISegmentedControl` in the bar (e.g. switching between chat-folder groups) | `#0F000000 → #1CFFFFFF` | `fill→on-fill`: `root.navBar.segmentedText` |
| `root.navBar.segmentedFg` | The selected segment's pill | `#F7F7F7 → #5BFFFFFF` | `pair:on/off` with `root.navBar.segmentedBg` |
| `root.navBar.segmentedText` | Label text of a segment | `#000000 → #FFFFFF` | `fill→on-fill` with `root.navBar.segmentedFg` |
| `root.navBar.segmentedDivider` | Divider line between two unselected segments | `#D6D6DC → #505155` | pairs with `root.navBar.segmentedBg` |

### Search bar

The pull-down search field at the top of the Chats list and other
searchable lists. `PresentationThemeNavigationSearchBar`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `root.searchBar.background` | The bar's own background strip (distinct from the nav bar behind it) | `#FFFFFF → #1C1C1D` | `mirror`: `root.navBar.opaqueBackground` |
| `root.searchBar.accent` | Accent color for the active/focused state of the field | `#007AFF → #007AFF` | pairs with `root.searchBar.inputText` |
| `root.searchBar.inputFill` | The rounded search-field pill itself | `#0F000000 → #19FFFFFF` | `fill→on-fill`: `root.searchBar.inputText`/`inputPlaceholderText` |
| `root.searchBar.inputText` | Typed query text | `#000000 → #FFFFFF` | `fill→on-fill` with `root.searchBar.inputFill` |
| `root.searchBar.inputPlaceholderText` | "Search" placeholder | `#8E8E93 → #8F8F8F` | pairs with `root.searchBar.inputText` |
| `root.searchBar.inputIcon` | The magnifying-glass glyph inside the field | `#8E8E93 → #8F8F8F` | `family` with `root.searchBar.inputClearButton` |
| `root.searchBar.inputClearButton` | The "✕" clear-field button | `#7B7B81 → #8F8F8F` | `family` with `root.searchBar.inputIcon` |
| `root.searchBar.separator` | Hairline under the search bar | `#C8C7CC → #8C545458` | `mirror`: `root.navBar.separator` |

## 4. `list.*` — Settings-style screens

Every grouped/plain table-view screen in the app (Settings, a chat's Info
page, most sheets) shares this one namespace. `PresentationThemeList` is by
far the largest single struct in the schema after chat messages.
`blocksBg`/`plainBg` are the two base surfaces almost everything else here
is text or a control drawn on top of.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `list.blocksBg` | Recessed background behind a "blocks"-style grouped list (rounded-corner sections with gaps between them) | `#EFEFF4 → #000000` | `mirror`: `list.itemBlocksBg` (the section cards on top of it) |
| `list.plainBg` | Background of a "plain"-style list (edge-to-edge rows, e.g. a picker) | `#FFFFFF → #000000` | `mirror`: `list.blocksBg` |
| `list.primaryText` | Row title text | `#000000 → #FFFFFF` | `fill→on-fill` with `list.itemBlocksBg`/`plainBg` |
| `list.secondaryText` | Row subtitle/value text | `#8E8E93 → #98989E` | pairs with `list.primaryText` |
| `list.disabledText` | Text in a disabled/non-interactive row | `#8E8E93 → #8F8F8F` | `pair:on/off` with `list.primaryText` |
| `list.accent` | Accent-colored row text/icon (a tappable value, a selected option's checkmark row) | `#007AFF → #007AFF` | Swift property `itemAccentColor`; drives every accent-tinted row across the Settings-style screens |
| `list.highlighted` | Success/positive row text (e.g. a confirmation state) | `#00B12C → #28B772` | `mirror`: `list.freeTextSuccess` |
| `list.destructive` | Destructive row text ("Delete Account", "Log Out", "Block User") | `#FF3B30 → #EB5545` | `mirror`: `actionSheet.destructiveActionText` |
| `list.placeholderText` | Placeholder text of a plain (non-bordered) input field in a list row | `#C8C8CE → #4D4D4D` | pairs with `list.freeInputField`/`itemInputField` |
| `list.itemBlocksBg` | A section card's own surface, sitting on top of `list.blocksBg` | `#FFFFFF → #1C1C1D` | `fill→on-fill`: `list.primaryText`; `mirror`: `list.plainBg` |
| `list.itemHighlightedBg` | Press feedback on a tapped row | `#E5E5EA → #313135` | Default: opaque `gray_5`/`gray_8`; Soza: translucent `tr_gray_5`/`tr_gray_3` — see both style `CLAUDE.md` files |
| `list.blocksSeparator` | Hairline between rows inside a blocks-style section | `#C8C7CC → #8C545458` | `mirror`: `list.plainSeparator` |
| `list.plainSeparator` | Hairline between rows in a plain-style list | `#C8C7CC → #8C545458` | `mirror`: `list.blocksSeparator` |
| `list.disclosureArrow` | The "›" chevron on a row that pushes a subscreen | `#BAB9BE → #47FFFFFF` | pairs with `list.primaryText` |
| `list.sectionHeaderText` | The all-caps label above a section ("NOTIFICATIONS", "PRIVACY") | `#6D6D72 → #8D8E93` | `mirror`: `list.freeText` |
| `list.freeText` | Footnote text below a section, not inside a row (a section's explanatory caption) | `#6D6D72 → #8D8E93` | `mirror`: `list.sectionHeaderText` |
| `list.freeTextError` | That footnote in its error state (e.g. "Passwords don't match") | `#CF3030 → #CF3030` | `pair:on/off` with `list.freeTextSuccess`; fixed `red_5` in every template regardless of mode |
| `list.freeTextSuccess` | That footnote in its success state | `#26972C → #30CF30` | `pair:on/off` with `list.freeTextError`; fixed `green_5` |
| `list.freeMonoIcon` | A monochrome icon standing alone outside a row (e.g. next to a footnote) | `#7E7E87 → #8D8E93` | `mirror`: `list.secondaryText` |
| `list.switch.frame` | Track of an off `UISwitch` | `#E9E9EA → #39393D` | `pair:on/off` with `list.switch.content` |
| `list.switch.handle` | The white knob, in both states | `#FFFFFF → #121212` | `fill→on-fill` sitting on both `frame` and `content` |
| `list.switch.content` | Track of an on `UISwitch` | `#35C759 → #67CE67` | `pair:on/off` with `list.switch.frame`; always accent-tinted, not a fixed green — despite the default hex looking green, the templates resolve this through `accent_5` |
| `list.switch.positive` | An alternate "on" track color for switches that mean something affirmative outside the accent (used sparingly) | `#00C900 → #08A723` | `family`: fixed `green_5` |
| `list.switch.negative` | An alternate "on" track color for a switch whose "on" state is itself a warning (e.g. enabling a destructive setting) | `#FF3B30 → #EB5545` | `family`: fixed `red_5` |
| `list.disclosureActions.neutral1.bg` | Fill of the first neutral swipe-action button revealed by swiping a row (context-dependent label, e.g. "Mute") | `#4892F2 → #666666` | `family`: the 7 `disclosureActions.*` pairs move together |
| `list.disclosureActions.neutral1.fg` | Icon/label on that button | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `neutral1.bg` |
| `list.disclosureActions.neutral2.bg` | Fill of the second neutral swipe-action button | `#F09A37 → #CD7800` | `family` |
| `list.disclosureActions.neutral2.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `neutral2.bg` |
| `list.disclosureActions.destructive.bg` | Fill of the red "Delete"-style swipe action | `#FF3824 → #C70C0C` | `family`; `mirror`: `list.destructive` |
| `list.disclosureActions.destructive.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `destructive.bg` |
| `list.disclosureActions.constructive.bg` | Fill of a positive/confirming swipe action | `#00C900 → #08A723` | `family`; fixed `green_5` |
| `list.disclosureActions.constructive.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `constructive.bg` |
| `list.disclosureActions.accent.bg` | Fill of an accent-tinted swipe action | `#007AFF → #666666` | `family`; Default keeps this `accent_5` in both modes, Soza's dark value falls back to neutral gray |
| `list.disclosureActions.accent.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `accent.bg` |
| `list.disclosureActions.warning.bg` | Fill of an orange warning swipe action | `#FF9500 → #CD7800` | `family`; fixed `orange_5` |
| `list.disclosureActions.warning.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `warning.bg` |
| `list.disclosureActions.inactive.bg` | Fill of a grayed-out/disabled swipe action | `#BCBCC3 → #666666` | `family` |
| `list.disclosureActions.inactive.fg` | Icon/label on it | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `inactive.bg` |
| `list.check.bg` | Fill of a checkmark control in a list (multi-select row, a chosen option) | `#007AFF → #007AFF` | `fill→on-fill`: `list.check.fg` |
| `list.check.stroke` | Outline ring of that checkmark control while unchecked | `#C7C7CC → #4CFFFFFF` | `pair:on/off` with `list.check.bg` |
| `list.check.fg` | The checkmark glyph itself | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `list.check.bg` |
| `list.controlSecondary` | A secondary/muted control tint used sparingly outside the switch/check families | `#DEDEDE → #7FFFFFFF` | `mirror`: `list.disabledText` |
| `list.freeInputField.bg` | Background of a standalone (not-in-a-row) text field, e.g. a search field embedded in a sheet | `#D6D6DC → #272728` | `pair` with `list.freeInputField.stroke` |
| `list.freeInputField.stroke` | Its border | `#D6D6DC → #272728` | `pair` with `list.freeInputField.bg` — identical value in every template, it's an outline that reads as filled |
| `list.freeInputField.placeholder` | Placeholder text in it | `#96979D → #98989E` | pairs with `list.freeInputField.primary` |
| `list.freeInputField.primary` | Typed text in it | `#000000 → #FFFFFF` | `fill→on-fill` with `list.freeInputField.bg` |
| `list.freeInputField.control` | A control glyph inside it (e.g. a clear button) | `#96979D → #98989E` | `family` with `list.freeInputField.placeholder` |
| `list.mediaPlaceholder` | Placeholder tile shown while a list-embedded media item (e.g. a shared-photo grid cell) loads | `#EFEFF4 → #323233` | `mirror`: `list.blocksBg` |
| `list.scrollIndicator` | The thin scrollbar thumb on any list | `#4C000000 → #7FFFFFFF` | resolves through `tr_gray_5` |
| `list.pageIndicatorInactive` | Inactive dot of a `UIPageControl` inside a list context (e.g. a multi-photo cell) | `#E3E3E7 → #4CFFFFFF` | `pair:on/off` with the active dot (accent, not a template key — resolved at render time) |
| `list.inputClearButton` | The "✕" button on a row-embedded text field | `#CCCCCC → #8B9197` | `mirror`: `root.searchBar.inputClearButton` |
| `list.itemBarChart.color1` | First/primary bar color in a small inline bar chart embedded in a list row (e.g. storage-usage breakdown) | `#007AFF → #007AFF` | `family`: `color1`–`color3` |
| `list.itemBarChart.color2` | Second bar color | `#C8C7CC → #929196` | `family` |
| `list.itemBarChart.color3` | Third/remainder bar color | `#F2F1F7 → #333333` | `family` |
| `list.itemInputField.bg` | Background of a bordered text field embedded inside a list row (distinct from the borderless `freeInputField`) | `#F2F2F7 → #0F0F0F` | `pair` with `list.itemInputField.stroke` |
| `list.itemInputField.stroke` | Its border | `#F2F2F7 → #0F0F0F` | `pair` with `list.itemInputField.bg` |
| `list.itemInputField.placeholder` | Placeholder text in it | `#B6B6BB → #8F8F8F` | pairs with `list.itemInputField.primary` |
| `list.itemInputField.primary` | Typed text in it | `#000000 → #FFFFFF` | `fill→on-fill` with `list.itemInputField.bg` |
| `list.itemInputField.control` | A control glyph inside it | `#B6B6BB → #8F8F8F` | `family` with `list.itemInputField.placeholder` |

## 5. `chart.*` — Premium statistics charts

The line/bar charts on a channel or group's Statistics screen (a Telegram
Premium feature). `PresentationThemeChart`.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chart.labels` | Axis label text (dates, values) | `#7F252529 → #8E8E93` | pairs with `chart.helperLines` |
| `chart.helperLines` | Light gridlines behind the chart | `#4C182D3B → #59D8D8D8` | `pair` with `chart.strongLines` |
| `chart.strongLines` | Emphasized gridlines (e.g. the zero line) | `#4C182D3B → #59D8D8D8` | `pair` with `chart.helperLines` — identical value in every template |
| `chart.barStrongLines` | Emphasized gridlines specific to bar-chart variants | `#33252529 → #72D8D8D8` | `mirror`: `chart.strongLines` |
| `chart.detailsText` | Text inside the floating tooltip shown when scrubbing the chart | `#6D6D72 → #FFFFFF` | `fill→on-fill` with `chart.detailsView` |
| `chart.detailsArrow` | The small directional caret on that tooltip | `#C5C7CD → #D8D8D8` | pairs with `chart.detailsView` |
| `chart.detailsView` | The tooltip's own background | `#F5F5FB → #000000` | `fill→on-fill`: `chart.detailsText` |
| `chart.rangeViewFrame` | Border of the draggable time-range selector below the chart | `#CAD4DE → #6D6D72` | pairs with `chart.rangeViewMarker` |
| `chart.rangeViewMarker` | The two drag handles at the ends of that selector | `#FFFFFF → #FFFFFF` | fixed `gray_9` — stays light on a colored/dark range track in both modes |
