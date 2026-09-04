> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §7 `chat.message.*` in full — incoming bubble, outgoing bubble, freeform
> bubble, and the flat message-level keys. 147 of 417 keys, the largest
> surface. `PresentationThemeChatMessage`/`PresentationThemePartedColors`/
> `PresentationThemeBubbleColor`/`PresentationThemeBubbleColorComponents` in
> `PresentationTheme.swift`; exact key names from
> `PresentationThemeCodable.swift`. Relation-tag vocabulary is defined once
> in `SKILL.md`. Example values are `Blue Shadow` (light) → `Instant Blue`
> (dark).

## 7. The shared bubble shape: `bubble.withWp` / `bubble.withoutWp`

`chat.message.incoming.bubble`, `chat.message.outgoing.bubble` and
`chat.message.freeform` all share the exact same 10-key sub-shape, once for
`withWp` (the chat has a custom wallpaper set) and once for `withoutWp`
(plain-color background). In practice `withWp` and `withoutWp` carry
identical or near-identical values in almost every template row below — the
fork exists because Telegram's own stock themes sometimes give a bubble a
translucent "glass" look over a busy wallpaper photo and a flat opaque look
over a plain color, and the two need independent values to do that. Where a
row's `withWp` and `withoutWp` values differ, it's called out.

| Sub-key | Draws | Relations |
|---|---|---|
| `.bg` | First/only gradient stop of the bubble fill | `pair` with `.gradientBg` (Telegram's decoder: if the two differ, both are forced fully opaque — see `patterns-and-provenance.md`) |
| `.gradientBg` | Second gradient stop. Equal to `.bg` for a flat-fill bubble; different for a top-to-bottom gradient bubble | `pair` with `.bg` |
| `.highlightedBg` | The bubble's fill while pressed/long-pressed (the flash before a context menu opens) | `pair:rest/pressed` with `.bg` |
| `.stroke` | The bubble's border, or `clear` for a borderless bubble | `pair` with `.bg` |
| `.reactionInactiveBg` | Fill of a reaction pill on this bubble that you have **not** tapped | `pair:on/off` with `.reactionActiveBg` |
| `.reactionInactiveFg` | Count/label text on that untapped pill | `fill→on-fill` with `.reactionInactiveBg` |
| `.reactionActiveBg` | Fill of a reaction pill you **have** tapped (your own reaction) | `pair:on/off` with `.reactionInactiveBg` |
| `.reactionActiveFg` | Count/label text on that tapped pill | `fill→on-fill` with `.reactionActiveBg` |
| `.reactionInactiveMediaPlaceholder` | Shimmer placeholder behind an untapped reaction's custom-emoji icon while it's still loading | `pair:on/off` with `.reactionActiveMediaPlaceholder` |
| `.reactionActiveMediaPlaceholder` | Same shimmer for a tapped reaction's icon | `pair:on/off` with `.reactionInactiveMediaPlaceholder` |

Star (paid) reactions reuse these same four `reaction*` values one level up
— Telegram's `PresentationThemeBubbleColorComponents` has separate
`reactionStarsInactiveBackground`/`reactionStarsActiveBackground` fields,
but the decoder always sets them equal to the plain reaction colors; there
is no separate `.tgios-theme` key to give a paid-star reaction its own
color.

## 7a. Incoming bubble — `chat.message.incoming.*`

Someone else's message. `PresentationThemePartedColors`, the `incoming`
instance.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.message.incoming.bubble.withWp.bg` | Incoming bubble fill over a custom wallpaper | `#FFFFFF → #E51D1D1D` | `pair:in/out` with `outgoing.bubble.withWp.bg`; `mirror`: `.withoutWp.bg` |
| `chat.message.incoming.bubble.withWp.gradientBg` | Second gradient stop, same context | `#FFFFFF → #E51D1D1D` | `pair` with `.bg` — equal in both samples, i.e. a flat fill |
| `chat.message.incoming.bubble.withWp.highlightedBg` | Pressed-state fill | `#DADADE → #59FFFFFF` | `pair:rest/pressed` with `.bg` |
| `chat.message.incoming.bubble.withWp.stroke` | Bubble border | `#FFFFFF → clear` | resolves through `transparent_0` in every template — incoming bubbles are borderless by convention here |
| `chat.message.incoming.bubble.withWp.reactionInactiveBg` | Untapped reaction pill fill | `#19007AFF → #11FFFFFF` | `pair:on/off` with `.reactionActiveBg` |
| `chat.message.incoming.bubble.withWp.reactionInactiveFg` | Untapped pill's text | `#007AFF → #FFFFFF` | `fill→on-fill` with `.reactionInactiveBg` |
| `chat.message.incoming.bubble.withWp.reactionActiveBg` | Tapped (your own) reaction pill fill | `#007AFF → #007AFF` | `pair:on/off` with `.reactionInactiveBg`; fixed `accent_5` |
| `chat.message.incoming.bubble.withWp.reactionActiveFg` | Tapped pill's text | `clear → #FFFFFF` | resolves through `tt_background` — `clear` in the light sample is a quirk of that specific export, not the template's intent |
| `chat.message.incoming.bubble.withWp.reactionInactiveMediaPlaceholder` | Shimmer behind an untapped reaction's loading icon | `#33FFFFFF → #19FFFFFF` | `pair:on/off` with `.reactionActiveMediaPlaceholder` |
| `chat.message.incoming.bubble.withWp.reactionActiveMediaPlaceholder` | Shimmer behind a tapped reaction's loading icon | `#33FFFFFF → #19FFFFFF` | `pair:on/off` with `.reactionInactiveMediaPlaceholder` — identical value to the inactive one in every template |
| `chat.message.incoming.bubble.withoutWp.bg` | Incoming bubble fill over a plain-color background | `#F1F1F4 → #E51D1D1D` | `mirror`: `.withWp.bg` — only the light-mode value differs (a visible off-white card instead of matching the bar material) |
| `chat.message.incoming.bubble.withoutWp.gradientBg` | Second stop, same context | `#F1F1F4 → #E51D1D1D` | `pair` with `.bg` |
| `chat.message.incoming.bubble.withoutWp.highlightedBg` | Pressed-state fill | `#DADADE → #59FFFFFF` | `mirror`: `.withWp.highlightedBg` |
| `chat.message.incoming.bubble.withoutWp.stroke` | Bubble border | `#F1F1F4 → clear` | `mirror`: `.withWp.stroke`; fixed `transparent_0` |
| `chat.message.incoming.bubble.withoutWp.reactionInactiveBg` | Untapped reaction pill fill | `#19007AFF → #11FFFFFF` | `mirror`: `.withWp.reactionInactiveBg` |
| `chat.message.incoming.bubble.withoutWp.reactionInactiveFg` | Untapped pill text | `#007AFF → #FFFFFF` | `mirror`: `.withWp.reactionInactiveFg` |
| `chat.message.incoming.bubble.withoutWp.reactionActiveBg` | Tapped pill fill | `#007AFF → #007AFF` | `mirror`: `.withWp.reactionActiveBg` |
| `chat.message.incoming.bubble.withoutWp.reactionActiveFg` | Tapped pill text | `clear → #FFFFFF` | `mirror`: `.withWp.reactionActiveFg` |
| `chat.message.incoming.bubble.withoutWp.reactionInactiveMediaPlaceholder` | Untapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionInactiveMediaPlaceholder` |
| `chat.message.incoming.bubble.withoutWp.reactionActiveMediaPlaceholder` | Tapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionActiveMediaPlaceholder` |
| `chat.message.incoming.primaryText` | The message body text | `#000000 → #FFFFFF` | `pair:in/out` with `outgoing.primaryText`; `fill→on-fill` with the bubble fill |
| `chat.message.incoming.secondaryText` | Muted metadata text inside the bubble (e.g. a forwarded-from label, a reply-preview's non-name line) | `#99525252 → #7FFFFFFF` | `pair:in/out` with `outgoing.secondaryText` |
| `chat.message.incoming.linkText` | A hyperlink's text color | `#004BAD → #007AFF` | `pair:in/out` with `outgoing.linkText`; pairs with `.linkHighlight` |
| `chat.message.incoming.linkHighlight` | Highlight drawn behind a long-pressed link | `#4C0088FF → #7F007AFF` | `pair:rest/pressed` with `.linkText` |
| `chat.message.incoming.scam` | The "SCAM"/"FAKE" warning label text | `#FF3B30 → #EB5545` | fixed `red_5`; `pair:in/out` with `outgoing.scam` |
| `chat.message.incoming.textHighlight` | Background highlight behind matched search-result text inside the bubble | `#FFC738 → #F5C038` | fixed `yellow_5`; `pair:in/out` with `outgoing.textHighlight` (identical in both directions — this one isn't in/out-differentiated) |
| `chat.message.incoming.accentText` | Accent-tinted text inside the bubble that isn't a link (e.g. a bot command, a mention) | `#007AFF → #007AFF` | `pair:in/out` with `outgoing.accentText` |
| `chat.message.incoming.accentControl` | Accent-tinted control glyph (e.g. the reply-swipe arrow, an inline button's icon) | `#007AFF → #007AFF` | `mirror`: `.accentText`; also the fallback color for `.reactionInactiveFg`/`.reactionActiveBg` if a theme omits those bubble-level keys |
| `chat.message.incoming.mediaActiveControl` | Tint of a media control in its active state (a playing voice message's pause icon, a downloading file's progress) | `#007AFF → #007AFF` | `pair:on/off` with `.mediaInactiveControl` |
| `chat.message.incoming.mediaInactiveControl` | That control's inactive/track color (the un-filled part of a seek bar) | `#CACACA → #66007AFF` | `pair:on/off` with `.mediaActiveControl` |
| `chat.message.incoming.mediaControlInnerBg` | Fill behind a play/pause glyph on a media control disc | `#FFFFFF → #262628` | Falls back to `bubble.withWp.bg` if a theme file omits this key entirely — see `patterns-and-provenance.md` |
| `chat.message.incoming.pendingActivity` | Muted color for a "sending…" activity indicator inside the bubble | `#99525252 → #7FFFFFFF` | `mirror`: `.secondaryText` |
| `chat.message.incoming.fileTitle` | A file attachment's filename text | `#007AFF → #007AFF` | `pair:in/out` with `outgoing.fileTitle` |
| `chat.message.incoming.fileDescription` | A file attachment's subtitle (size, type) | `#999999 → #7FFFFFFF` | pairs with `.fileTitle` |
| `chat.message.incoming.fileDuration` | Duration text on a voice message or round video | `#99525252 → #7FFFFFFF` | `mirror`: `.fileDescription` |
| `chat.message.incoming.mediaPlaceholder` | Placeholder tile behind a photo/video still loading in the bubble | `#F2F2F2 → #2A2A2A` | resolves through `tr_gray_3` |
| `chat.message.incoming.polls.radioButton` | Ring of an unselected poll option | `#C8C7CC → #737373` | `pair:on/off` with `.radioProgress` |
| `chat.message.incoming.polls.radioProgress` | Fill of the selected/voted-for option's ring | `#007AFF → #007AFF` | `pair:on/off` with `.radioButton` |
| `chat.message.incoming.polls.highlight` | Background tint behind the option you voted for | `#1E007AFF → #7FFFFFFF` | pairs with `.radioProgress` |
| `chat.message.incoming.polls.separator` | Divider line between poll options | `#C8C7CC → #000000` | pairs with `.radioButton` |
| `chat.message.incoming.polls.bar` | Fill of a poll option's vote-percentage bar | `#007AFF → #007AFF` | `family`: `.barPositive`/`.barNegative` override this per-option for a quiz's correct/wrong answer |
| `chat.message.incoming.polls.barIconForeground` | Glyph drawn on the bar (e.g. a checkmark/✕ on a quiz answer) | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.bar`; fixed `gray_9` |
| `chat.message.incoming.polls.barPositive` | Bar fill for a quiz's correct answer | `#00A700 → #00A700` | fixed `green_5` in every template — not a style differentiator |
| `chat.message.incoming.polls.barNegative` | Bar fill for a quiz's wrong answer(s) | `#FE3824 → #FE3824` | fixed `red_5` |
| `chat.message.incoming.actionButtonsBg.withWp` | Fill of the Instant View-style action buttons row under a link preview, over a wallpaper | `#66A5A5A5 → #7F000000` | `pair:withWp/withoutWp` with `.actionButtonsBg.withoutWp` |
| `chat.message.incoming.actionButtonsBg.withoutWp` | Same, over a plain background | `#CCFFFFFF → #7F000000` | resolves through `tr_background_9` in Default — a near-opaque material rather than the translucent `withWp` overlay |
| `chat.message.incoming.actionButtonsStroke.withWp` | Border of that button row, over a wallpaper | `clear → #2DB2B2B2` | `pair:withWp/withoutWp` |
| `chat.message.incoming.actionButtonsStroke.withoutWp` | Same, over a plain background | `#007AFF → #2DB2B2B2` | `pair:withWp/withoutWp` |
| `chat.message.incoming.actionButtonsText.withWp` | Label/icon color on those buttons, over a wallpaper | `#FFFFFF → #FFFFFF` | fixed `gray_9` |
| `chat.message.incoming.actionButtonsText.withoutWp` | Same, over a plain background | `#007AFF → #FFFFFF` | Default's light-mode value is accent instead of `gray_9` — the button reads as a link rather than a filled control against the near-opaque `withoutWp` fill |
| `chat.message.incoming.textSelection` | Highlight color while selecting message text | `#33007AFF → #33007AFF` | resolves through `tr_accent_5`; `pair:in/out` with `outgoing.textSelection` |
| `chat.message.incoming.textSelectionKnob` | The drag handle at each end of a text selection | `#007AFF → #007AFF` | Fixed regardless of mode per style (`accent_4` light/`accent_3` dark in Default, flat `accent_5` in Soza) — see both style `CLAUDE.md` files |

## 7b. Outgoing bubble — `chat.message.outgoing.*`

Your own message — the surface both style `CLAUDE.md` files describe as
each style's "signature move" (Default: a fixed mid-brightness accent step;
Soza: a step that flips brightness by mode). Same 52-key shape as incoming.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.message.outgoing.bubble.withWp.bg` | Outgoing bubble fill | `#61BCF9 → #61BCF9` | `pair:in/out` with `incoming.bubble.withWp.bg`; both samples keep light and dark identical — a fixed brand-blue rather than a `tt_*`-derived tone |
| `chat.message.outgoing.bubble.withWp.gradientBg` | Second gradient stop | `#007AFF → #007AFF` | `pair` with `.bg` — the two samples give this a genuinely different stop, i.e. a real gradient bubble, not a flat fill |
| `chat.message.outgoing.bubble.withWp.highlightedBg` | Pressed-state fill | `#77B4F3 → #218BFF` | `pair:rest/pressed` with `.bg` |
| `chat.message.outgoing.bubble.withWp.stroke` | Bubble border | `clear → clear` | fixed `transparent_0` in every template — outgoing bubbles are always borderless here |
| `chat.message.outgoing.bubble.withWp.reactionInactiveBg` | Untapped reaction pill fill | `#19FFFFFF → #1EFFFFFF` | `pair:on/off` with `.reactionActiveBg` |
| `chat.message.outgoing.bubble.withWp.reactionInactiveFg` | Untapped pill text | `#FFFFFF → #FFFFFF` | fixed `gray_9` — must read against the accent-filled pill in both modes |
| `chat.message.outgoing.bubble.withWp.reactionActiveBg` | Tapped (your own) reaction pill fill | `#FFFFFF → #FFFFFF` | `pair:on/off` with `.reactionInactiveBg`; fixed `accent_5` in the template, i.e. inverted relative to incoming — the "active" pill on your own bubble reads as a light chip against the accent bubble |
| `chat.message.outgoing.bubble.withWp.reactionActiveFg` | Tapped pill text | `clear → 00000000` | resolves through `tt_background` |
| `chat.message.outgoing.bubble.withWp.reactionInactiveMediaPlaceholder` | Untapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `incoming.*.reactionInactiveMediaPlaceholder` |
| `chat.message.outgoing.bubble.withWp.reactionActiveMediaPlaceholder` | Tapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `incoming.*.reactionActiveMediaPlaceholder` |
| `chat.message.outgoing.bubble.withoutWp.bg` | Outgoing bubble fill, plain background | `#61BCF9 → #61BCF9` | `mirror`: `.withWp.bg` — identical value in every template; unlike incoming, outgoing doesn't fork by wallpaper |
| `chat.message.outgoing.bubble.withoutWp.gradientBg` | Second gradient stop | `#007AFF → #007AFF` | `mirror`: `.withWp.gradientBg` |
| `chat.message.outgoing.bubble.withoutWp.highlightedBg` | Pressed-state fill | `#77B4F3 → #218BFF` | `mirror`: `.withWp.highlightedBg` |
| `chat.message.outgoing.bubble.withoutWp.stroke` | Bubble border | `clear → clear` | fixed `transparent_0` |
| `chat.message.outgoing.bubble.withoutWp.reactionInactiveBg` | Untapped reaction pill fill | `#19FFFFFF → #1EFFFFFF` | `mirror`: `.withWp.reactionInactiveBg` |
| `chat.message.outgoing.bubble.withoutWp.reactionInactiveFg` | Untapped pill text | `#FFFFFF → #FFFFFF` | `mirror`: `.withWp.reactionInactiveFg` |
| `chat.message.outgoing.bubble.withoutWp.reactionActiveBg` | Tapped pill fill | `#FFFFFF → #FFFFFF` | `mirror`: `.withWp.reactionActiveBg` |
| `chat.message.outgoing.bubble.withoutWp.reactionActiveFg` | Tapped pill text | `clear → 00000000` | `mirror`: `.withWp.reactionActiveFg` |
| `chat.message.outgoing.bubble.withoutWp.reactionInactiveMediaPlaceholder` | Untapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionInactiveMediaPlaceholder` |
| `chat.message.outgoing.bubble.withoutWp.reactionActiveMediaPlaceholder` | Tapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionActiveMediaPlaceholder` |
| `chat.message.outgoing.primaryText` | Message body text | `#FFFFFF → #FFFFFF` | `pair:in/out` with `incoming.primaryText`; Default resolves this through `tt_background` (the "inverted" convention — see `templates/ios/default/CLAUDE.md`), Soza through flat `tt_onBackground` |
| `chat.message.outgoing.secondaryText` | Muted metadata text on the bubble | `#A5FFFFFF → #7FFFFFFF` | `pair:in/out` with `incoming.secondaryText`; this one is the Default exception to the inversion rule — always an actual muted gray (`gray_8`/`gray_1`), never inverted |
| `chat.message.outgoing.linkText` | Hyperlink text | `#FFFFFF → #FFFFFF` | `pair:in/out` with `incoming.linkText` |
| `chat.message.outgoing.linkHighlight` | Highlight behind a long-pressed link | `#4CFFFFFF → #7FFFFFFF` | `pair:rest/pressed` with `.linkText` |
| `chat.message.outgoing.scam` | "SCAM"/"FAKE" label text | `#FFFFFF → #FFFFFF` | `pair:in/out` with `incoming.scam` — notably not fixed red here, since red-on-accent-blue can clash; it inverts with the bubble like primary text |
| `chat.message.outgoing.textHighlight` | Search-match highlight inside the bubble | `#FFC738 → #F5C038` | fixed `yellow_5`, identical to `incoming.textHighlight` |
| `chat.message.outgoing.accentText` | Accent-tinted non-link text on the bubble | `#FFFFFF → #FFFFFF` | `pair:in/out` with `incoming.accentText` |
| `chat.message.outgoing.accentControl` | Accent-tinted control glyph on the bubble | `#FFFFFF → #FFFFFF` | `mirror`: `.accentText` |
| `chat.message.outgoing.mediaActiveControl` | Active-state media control tint | `#FFFFFF → #FFFFFF` | `pair:on/off` with `.mediaInactiveControl` |
| `chat.message.outgoing.mediaInactiveControl` | Inactive/track color for that control | `#A5FFFFFF → #7FFFFFFF` | `pair:on/off` with `.mediaActiveControl` |
| `chat.message.outgoing.mediaControlInnerBg` | Fill behind a play/pause glyph on a media control disc | `clear → #61BCF9` | falls back to `bubble.withWp.bg` if omitted, same as incoming |
| `chat.message.outgoing.pendingActivity` | Muted "sending…" indicator color | `#A5FFFFFF → #7FFFFFFF` | `mirror`: `.secondaryText` |
| `chat.message.outgoing.fileTitle` | File attachment filename | `#FFFFFF → #FFFFFF` | `pair:in/out` with `incoming.fileTitle` |
| `chat.message.outgoing.fileDescription` | File attachment subtitle | `#A5FFFFFF → #7FFFFFFF` | pairs with `.fileTitle` |
| `chat.message.outgoing.fileDuration` | Voice/video-message duration text | `#A5FFFFFF → #7FFFFFFF` | `mirror`: `.fileDescription` |
| `chat.message.outgoing.mediaPlaceholder` | Placeholder tile behind loading media | `#0073F2 → #33FFFFFF` | Unlike incoming's neutral `tr_gray_3`, this is accent-tinted (`accent_4`/`accent_3`) — the placeholder reads as "part of the bubble" rather than a generic loading tile |
| `chat.message.outgoing.polls.radioButton` | Unselected poll-option ring | `#A5FFFFFF → #FFFFFF` | `pair:on/off` with `.radioProgress` |
| `chat.message.outgoing.polls.radioProgress` | Selected option's ring fill | `#FFFFFF → #FFFFFF` | `pair:on/off` with `.radioButton` |
| `chat.message.outgoing.polls.highlight` | Background tint behind your vote | `#1EFFFFFF → #1EFFFFFF` | pairs with `.radioProgress` |
| `chat.message.outgoing.polls.separator` | Divider between options | `#A5FFFFFF → #7FFFFFFF` | pairs with `.radioButton` |
| `chat.message.outgoing.polls.bar` | Vote-percentage bar fill | `#FFFFFF → #FFFFFF` | `family`: overridden by `.barPositive`/`.barNegative` for a quiz |
| `chat.message.outgoing.polls.barIconForeground` | Glyph on the bar | `clear → clear` | fixed `transparent_0` — unlike incoming, outgoing draws no separate icon tint (the bar itself is already white-on-accent) |
| `chat.message.outgoing.polls.barPositive` | Correct-answer bar fill | `#FFFFFF → #FFFFFF` | resolves through `green_5` regardless of the literal white sample value — not a style differentiator |
| `chat.message.outgoing.polls.barNegative` | Wrong-answer bar fill | `#FFFFFF → #FFFFFF` | resolves through `red_5` |
| `chat.message.outgoing.actionButtonsBg.withWp` | Instant View action-button row fill, over a wallpaper | `#66A5A5A5 → #7F000000` | `mirror`: `incoming.actionButtonsBg.withWp` — identical in every template; this row doesn't fork by direction |
| `chat.message.outgoing.actionButtonsBg.withoutWp` | Same, plain background | `#CCFFFFFF → #7F000000` | `mirror`: `incoming.actionButtonsBg.withoutWp` |
| `chat.message.outgoing.actionButtonsStroke.withWp` | Border of that row, over a wallpaper | `clear → #2DB2B2B2` | `mirror`: `incoming.actionButtonsStroke.withWp` |
| `chat.message.outgoing.actionButtonsStroke.withoutWp` | Same, plain background | `#007AFF → #2DB2B2B2` | `mirror`: `incoming.actionButtonsStroke.withoutWp` |
| `chat.message.outgoing.actionButtonsText.withWp` | Label/icon color, over a wallpaper | `#FFFFFF → #FFFFFF` | `mirror`: `incoming.actionButtonsText.withWp` |
| `chat.message.outgoing.actionButtonsText.withoutWp` | Same, plain background | `#007AFF → #FFFFFF` | `mirror`: `incoming.actionButtonsText.withoutWp` |
| `chat.message.outgoing.textSelection` | Text-selection highlight | `#33FFFFFF → #33FFFFFF` | resolves through `tr_accent_5`; `pair:in/out` with `incoming.textSelection` |
| `chat.message.outgoing.textSelectionKnob` | Selection drag handle | `#FFFFFF → #FFFFFF` | **Not** a style differentiator — fixed `accent_3` in all four templates (both styles, both modes); see both style `CLAUDE.md` files |

## 7c. Freeform bubble — `chat.message.freeform.*`

The bubble shape used for message content that isn't in/out-tinted text —
stickers, gift/Premium bubbles, and "action" content cards (confirmed by
usage in `ChatMessageGiftBubbleContentNode.swift`,
`ChatMessageActionBubbleContentNode.swift` and
`ChatMessageAnimatedStickerItemNode.swift` in the Telegram-iOS source).
`PresentationThemeBubbleColor` only — no `primaryText`/`fileTitle`/etc.
sibling fields, because this bubble style doesn't carry its own text; it's
reused mainly for its reaction-pill colors and its fill on content that
draws its own visuals.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.message.freeform.withWp.bg` | Freeform bubble fill, over a wallpaper | `#E5E5EA → #1F1F1F` | `mirror`: `incoming.bubble.withoutWp.bg` — a neutral card, closer to incoming's than outgoing's |
| `chat.message.freeform.withWp.gradientBg` | Second gradient stop | `#E5E5EA → #1F1F1F` | `pair` with `.bg` — flat in every template |
| `chat.message.freeform.withWp.highlightedBg` | Pressed-state fill | `#DADADE → #2A2A2A` | `pair:rest/pressed` with `.bg` |
| `chat.message.freeform.withWp.stroke` | Border | `#E5E5EA → #1F1F1F` | equals `.bg` in every template — a same-color outline rather than a visible border |
| `chat.message.freeform.withWp.reactionInactiveBg` | Untapped reaction pill fill on a sticker/gift/action card | `#190088FF → #11FFFFFF` | `pair:on/off` with `.reactionActiveBg` |
| `chat.message.freeform.withWp.reactionInactiveFg` | Untapped pill text | `#0088FF → #FFFFFF` | `fill→on-fill` with `.reactionInactiveBg` |
| `chat.message.freeform.withWp.reactionActiveBg` | Tapped reaction pill fill | `#0088FF → #007AFF` | `pair:on/off` with `.reactionInactiveBg`; fixed `accent_5` |
| `chat.message.freeform.withWp.reactionActiveFg` | Tapped pill text | `clear → #FFFFFF` | resolves through `tt_background` |
| `chat.message.freeform.withWp.reactionInactiveMediaPlaceholder` | Untapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `incoming`/`outgoing` equivalents |
| `chat.message.freeform.withWp.reactionActiveMediaPlaceholder` | Tapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `incoming`/`outgoing` equivalents |
| `chat.message.freeform.withoutWp.bg` | Freeform bubble fill, plain background | `#E5E5EA → #1F1F1F` | `mirror`: `.withWp.bg` — identical in every template |
| `chat.message.freeform.withoutWp.gradientBg` | Second gradient stop | `#E5E5EA → #1F1F1F` | `pair` with `.bg` |
| `chat.message.freeform.withoutWp.highlightedBg` | Pressed-state fill | `#DADADE → #2A2A2A` | `mirror`: `.withWp.highlightedBg` |
| `chat.message.freeform.withoutWp.stroke` | Border | `#E5E5EA → #1F1F1F` | `mirror`: `.withWp.stroke` |
| `chat.message.freeform.withoutWp.reactionInactiveBg` | Untapped reaction pill fill (used for static-reaction/star-reaction fallback per `selectReactionFillStaticColor` in the source) | `#F1F0F5 → #11FFFFFF` | this is the one row where `withWp` and `withoutWp` genuinely diverge in the light sample — the plain-background variant is a solid near-white instead of a translucent accent wash |
| `chat.message.freeform.withoutWp.reactionInactiveFg` | Untapped pill text | `#0088FF → #FFFFFF` | `mirror`: `.withWp.reactionInactiveFg` |
| `chat.message.freeform.withoutWp.reactionActiveBg` | Tapped pill fill | `#0088FF → #007AFF` | `mirror`: `.withWp.reactionActiveBg` |
| `chat.message.freeform.withoutWp.reactionActiveFg` | Tapped pill text | `clear → #FFFFFF` | `mirror`: `.withWp.reactionActiveFg` |
| `chat.message.freeform.withoutWp.reactionInactiveMediaPlaceholder` | Untapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionInactiveMediaPlaceholder` |
| `chat.message.freeform.withoutWp.reactionActiveMediaPlaceholder` | Tapped reaction shimmer | `#33FFFFFF → #19FFFFFF` | `mirror`: `.withWp.reactionActiveMediaPlaceholder` |

## 7d. Message-level keys (not per-bubble)

Keys that sit directly under `chat.message` rather than inside
`incoming`/`outgoing`/`freeform` — mostly overlays that sit on top of any
bubble regardless of direction. `PresentationThemeChatMessage`'s flat
fields.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `chat.message.infoPrimaryText` | Body text of the "Message info" sheet (delivery/read details) | `#000000 → #FFFFFF` | `mirror`: `list.primaryText` — this sheet is styled like a list, not a bubble |
| `chat.message.infoLinkText` | Link text in that sheet | `#004BAD → #007AFF` | pairs with `.infoPrimaryText` |
| `chat.message.outgoingCheck` | Delivery-tick glyph (✓/✓✓) on your own message, drawn outside the bubble on a wallpaper | `#FFFFFF → #FFFFFF` | fixed `gray_9` — must read against any wallpaper |
| `chat.message.mediaDateAndStatusBg` | Small pill behind the timestamp+ticks overlaid directly on a photo/video (no visible bubble chrome there) | `#4C000000 → #4C000000` | `fill→on-fill`: `.mediaDateAndStatusText`; identical value in every template regardless of mode — it dims a fixed-brightness image, not a themed surface |
| `chat.message.mediaDateAndStatusText` | Timestamp+ticks text on that pill | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.mediaDateAndStatusBg`; fixed `gray_9` |
| `chat.message.shareButtonBg.withWp` | Fill of the floating "share/forward" circle button on a media message, over a wallpaper | `#66A5A5A5 → #7F000000` | `pair:withWp/withoutWp` |
| `chat.message.shareButtonBg.withoutWp` | Same, over a plain background | `#CCFFFFFF → #7F000000` | resolves through `tr_background_9` |
| `chat.message.shareButtonStroke.withWp` | Border of that button, over a wallpaper | `clear → #2DB2B2B2` | `pair:withWp/withoutWp` |
| `chat.message.shareButtonStroke.withoutWp` | Same, plain background | `#E5E5EA → #2DB2B2B2` | `pair:withWp/withoutWp` |
| `chat.message.shareButtonFg.withWp` | Icon on that button, over a wallpaper | `#FFFFFF → #FFFFFF` | fixed `gray_9` |
| `chat.message.shareButtonFg.withoutWp` | Same, plain background | `#007AFF → #FFFFFF` | Default's light value is accent, matching the same withoutWp-goes-accent pattern as `actionButtonsText.withoutWp` |
| `chat.message.mediaOverlayControl.bg` | Fill of a play/download control disc overlaid directly on media | `#72000000 → #99000000` | `fill→on-fill`: `.mediaOverlayControl.fg` |
| `chat.message.mediaOverlayControl.fg` | Glyph on that disc | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.mediaOverlayControl.bg`; fixed `gray_9` |
| `chat.message.selectionControl.bg` | Fill of the circular checkmark shown on a message during multi-select | `#007AFF → #007AFF` | `fill→on-fill`: `.selectionControl.fg` |
| `chat.message.selectionControl.stroke` | Its ring while unchecked | `#C7C7CC → #FFFFFF` | `pair:on/off` with `.selectionControl.bg` |
| `chat.message.selectionControl.fg` | The checkmark glyph itself | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.selectionControl.bg`; fixed `gray_9` |
| `chat.message.deliveryFailed.bg` | Fill of the red "!" delivery-failed marker on a message | `#FF3B30 → #EB5545` | `fill→on-fill`: `.deliveryFailed.fg`; `mirror`: `chatList.failedFill` |
| `chat.message.deliveryFailed.fg` | The "!" glyph | `#FFFFFF → #FFFFFF` | `fill→on-fill` with `.deliveryFailed.bg`; fixed `gray_9` |
| `chat.message.mediaHighlightOverlay` | Flash tint used to highlight a message jumped-to from a search result or a reply-tap | `#99FFFFFF → #99FFFFFF` | resolves through `tr_gray_5`; identical in every template |
| `chat.message.stickerPlaceholder.withWp` | Placeholder tile behind a loading sticker, over a wallpaper | `#4CA5A5A5 → #19FFFFFF` | `pair:withWp/withoutWp` |
| `chat.message.stickerPlaceholder.withoutWp` | Same, plain background | `#F7F7F7 → #19FFFFFF` | `pair:withWp/withoutWp` |
| `chat.message.stickerPlaceholderShimmer.withWp` | Moving shimmer highlight over that placeholder, over a wallpaper | `#33FFFFFF → #19FFFFFF` | `pair:withWp/withoutWp` |
| `chat.message.stickerPlaceholderShimmer.withoutWp` | Same, plain background | `#19000000 → #19FFFFFF` | `pair:withWp/withoutWp` |
