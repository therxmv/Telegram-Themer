> Part of the [ios-theme-key](../SKILL.md) skill's iOS key reference. Covers
> §17 the two whole-theme metadata keys, §18 cross-cutting shape patterns
> (decode a key this reference somehow missed), §19 decoder fallback
> behavior worth knowing before you assume a key is unused, §20 open
> questions, and how this reference was compiled.

## 17. Whole-theme metadata — not under any UI namespace

Two keys sit at the very top of a `.tgios-theme` file, siblings of `intro`,
`root`, `chat`, etc. rather than inside any of them.

| Key | Draws | Example (light → dark) | Relations |
|---|---|---|---|
| `dark` | **Literal boolean.** Whether Telegram treats this theme as dark overall (`overallDarkAppearance`) — drives things like which system UI chrome variant iOS itself renders, independent of any individual color below | `false → true` | `alias`: `basedOn`, both describe the whole theme rather than one element |
| `basedOn` | **Literal string**, one of `day` / `night` (this project's templates only ever use these two; Telegram's own builtins add `dayClassic`/`nightAccent`). Decodes to a `PresentationBuiltinThemeReference` that seeds any color a theme file happens to omit, by falling back to that stock theme's own value for the missing key | `day → night` | Falls back silently — an incomplete theme file doesn't error, it just inherits `basedOn`'s value for whatever's missing. See §19 |

## 18. Cross-cutting shape patterns

If you meet a key this map somehow doesn't list, decode it from these. They
hold across every namespace above.

| Pattern | Means | Example |
|---|---|---|
| `*.withWp` / `*.withoutWp` | The same element with a custom wallpaper set vs a plain-color background. Almost always identical values — see §19 for when Telegram actually needs them to differ. | `chat.message.incoming.bubble.withWp.bg` / `.withoutWp.bg` |
| `*.bg` + `*.gradientBg` | The two stops of a fill. Equal = flat color; different = a top-to-bottom gradient. | `chat.message.outgoing.bubble.withWp.bg` / `.gradientBg` |
| `*.highlightedBg` | The pressed/touch-down state of a `bg`. | `chat.message.incoming.bubble.withWp.highlightedBg` |
| `*Bg` + `*Fg` / `*Text` | A fill and what's drawn on it. Always a contrast pair. | `chatList.unreadBadgeActiveBg` / `.unreadBadgeActiveText` |
| `incoming.*` / `outgoing.*` / `freeform.*` | The three bubble "directions" under `chat.message`. `incoming`/`outgoing` share the full `PresentationThemePartedColors` shape (52 keys each); `freeform` shares only the 20-key bubble sub-shape, no text/file/poll fields — see `chat-bubbles.md` §7c. | — |
| `withDefaultWp` / `withCustomWp` | One level up from `withWp`/`withoutWp` — used only by `chat.serviceMessage.components`, and keyed off whether the wallpaper is *literally* plain white, not just "no wallpaper chosen". | `chat.serviceMessage.components.withDefaultWp.bg` |
| `panelContentVibrant*` / `panelContentOpaque*` | A vibrancy-effect-tinted color and its flat fallback, both inside `chat.inputMediaPanel`. | `panelContentVibrantOverlay` / `panelContentOpaqueOverlay` |
| trailing `_v2` | A handful of `chat.inputPanel` keys (`panelControl_v2`, `inputPlaceholder_v2`, `inputText_v2`, `inputControl_v2`) carry a `_v2` suffix in the real export format — an artifact of a past composer redesign, not a convention to imitate on a new key. | `chat.inputPanel.inputText_v2` |
| `expanded.*` | The Notification Center / long-press-expanded form of a notification, distinct from the compact banner. | `notification.expanded.navBar.background` |
| `bgType` | **Literal**, not a color: `light`/`dark`, telling Telegram which flavor of system chrome to draw around an otherwise-custom-colored surface. Appears on `actionSheet` and `notification.expanded`. | `actionSheet.bgType` |
| trailing `1`, `2`, `3` | Either gradient stops (`chatList.pinnedArchiveAvatar.background.top`/`.bottom` use `top`/`bottom` instead, but `list.itemBarChart.color1`–`3` and `list.disclosureActions.*` numbered neutrals use plain digits) or an enumerated family member. Change the whole set together. | `list.itemBarChart.color1`–`color3` |
| `Active` / `Inactive` | A reaction pill's tapped-by-you vs not-tapped-by-you state (not a tab-selection state, unlike Android's `*Active`/`*Unactive`). | `bubble.withWp.reactionActiveBg` / `.reactionInactiveBg` |

## 19. Decoder fallback behavior worth knowing

`PresentationThemeCodable.swift` doesn't just map JSON keys onto struct
fields 1:1 — it has specific fallback logic that changes how you should
read a handful of keys. All 417 keys in this project's templates are always
present, so none of this fallback logic actually fires when *this
project's* files are read — but it explains real values you might see in a
`.tgios-theme` found in the wild, and it's the source of a couple of the
relations noted in the tables above.

- **`bg`/`gradientBg` mismatch forces full opacity.** When decoding a
  bubble's fill, if `gradientBg` doesn't equal `bg`, the decoder strips the
  alpha channel from *both* (`withAlphaComponent(1.0)`) before building the
  gradient. A translucent two-stop bubble gradient isn't representable —
  only a translucent *flat* fill is.
- **`mediaControlInnerBg` falls back to the bubble's own fill.** If an
  incoming/outgoing bubble's `mediaControlInnerBg` key is missing, Telegram
  reuses that same bubble's `bubble.withWp.bg` rather than leaving it
  undefined.
- **The four reaction colors fall back to `accentControl`.** If
  `reactionInactiveBg`/`reactionInactiveFg`/`reactionActiveBg` are missing
  from a bubble, Telegram derives them from that same `parted-colors`
  block's `accentControl` (`reactionInactiveBg` = `accentControl` at 10%
  alpha, `reactionInactiveFg`/`reactionActiveBg` = `accentControl` itself).
  `reactionActiveFg` alone falls back to plain `clear` rather than to
  `accentControl`.
- **Star (paid) reactions have no key of their own.** `reactionStars*`
  exists in the Swift struct but is always set equal to the plain
  `reaction*` colors at decode time — see `chat-bubbles.md` §7.
- **`gradientBg` itself has a fallback key**, `"\(codingPath).bg"` — i.e. if
  a `gradientBg` is missing but `bg` is present, `gradientBg` quietly
  becomes equal to `bg` (a flat fill) rather than erroring.
- **`basedOn` seeds every other missing key**, not just colors covered
  above — decoding constructs the referenced stock theme
  (`makeDefaultPresentationTheme(reference:...)`) first and uses it as the
  fallback source for anything the file doesn't explicitly set. This is
  also why a hand-written theme file that only overrides a handful of keys
  still produces a fully-colored app: everything else quietly inherits from
  `basedOn`.

## 20. Open questions

**Not fully verified: the exact trigger for the `panelContentVibrant*` /
`panelContentOpaque*` split.** Confirmed via source
(`GroupHeaderLayer.swift` in `TelegramUI/Components/EntityKeyboard`) that
`panelContentVibrantOverlayColor` tints section-header text inside the
sticker/emoji panel, consistent with a `UIVisualEffectView` vibrancy
effect. Not independently confirmed exactly which runtime condition (Reduce
Transparency, or something else) selects the `Opaque` variant instead — the
description in `chat-panels.md` §10 states the mechanism as understood from
naming + one confirmed call site, not from having traced the selection
logic itself.

**The `freeform` bubble's exact set of callers wasn't enumerated
exhaustively.** Confirmed via a `gh api search/code` sweep for
`"message.freeform"` across `TelegramMessenger/Telegram-iOS` that it's used
by gift bubbles, "action" content bubbles, animated stickers, and the
reactions-footer component — enough to describe its role confidently
(chat-bubbles.md §7c) — but the sweep wasn't exhaustive over every content
type that might reuse it.

## How this was compiled

- **Key set**: `theme-wizard/templates/ios/default/ios_default_light.json`,
  all 417 keys, cross-checked against `ios_default_dark.json` and both
  `ios_soza_*.json` files (all four are key-for-key identical, as their own
  `CLAUDE.md` files require) and against two real `.tgios-theme` exports —
  "Blue Shadow" (light) and "Instant Blue" (dark) — parsed from their native
  indented format down to the same dot-path shape as the templates. The
  four template files' key set and the two real exports' key set (minus the
  one non-themeable `name` field) are **exactly** the same 417 keys in both
  directions — there is no Android-style `†` gap to track here.
- **Structural grounding**:
  [`TelegramMessenger/Telegram-iOS`](https://github.com/TelegramMessenger/Telegram-iOS),
  [`submodules/TelegramPresentationData/Sources/PresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/PresentationTheme.swift)
  (struct definitions — every dot-path segment corresponds to one Swift
  property, confirmed for every struct referenced in this reference) and
  [`PresentationThemeCodable.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/PresentationThemeCodable.swift)
  (the `CodingKeys` enums that produce the exact `.tgios-theme` string keys,
  plus the fallback logic in §19).
- **Stock-default cross-check**:
  [`DefaultDayPresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/DefaultDayPresentationTheme.swift)
  /
  [`DefaultDarkPresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/DefaultDarkPresentationTheme.swift)
  — the constructors for Telegram's own built-in Day/Night themes.
  Spot-checked against both sample exports; most neutral values in "Blue
  Shadow" match `DefaultDayPresentationTheme.swift` literally (e.g.
  `list.blocksBg` = `#EFEFF4` in both), which is strong independent evidence
  that "Blue Shadow" is a lightly recolored stock Day Classic theme (its
  accent is `#007AFF` against stock's `defaultDayAccentColor = #0088FF`, and
  its passcode gradient and start button are customized) rather than an
  unrelated theme that happens to agree by chance.
- **Usage grounding for ambiguous keys**: targeted `gh api search/code`
  sweeps (or the equivalent `https://github.com/search?q=repo%3ATelegramMessenger%2FTelegram-iOS+%22<key>%22&type=code`)
  against `TelegramMessenger/Telegram-iOS` for keys whose meaning wasn't
  obvious from name + struct alone — `message.freeform` (§7c above),
  `panelContentVibrantOverlayColor`
  ([`GroupHeaderLayer.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramUI/Components/EntityKeyboard/Sources/GroupHeaderLayer.swift),
  chat-panels.md §10). See the skill's [`SKILL.md`](../SKILL.md) "When a key
  isn't in this reference" for this same fallback path.
- **Visual grounding**: the four attached iOS screenshots — main Chats list
  with story rings and a folder tab bar; a group chat mid-voice-message
  recording with a poll and an animated sticker; a chat with a photo grid,
  an "Incoming Call" row, and the emoji/GIF/sticker panel open; a Secret
  Chat with the self-destruct timer, a blurred self-destructing photo, and
  a "took a screenshot" service message.
- **Checks run**: all four template files parse as JSON with the same 417
  keys, each once; that key set equals each real export's key set exactly
  (minus `name`); every role named in the templates is one
  [`color-roles.md`](../../../theme-wizard/color-roles.md) actually defines;
  every `pair`/`mirror`/`family` relation named in these tables resolves to
  a key that exists somewhere in this reference.

Descriptions of elements not visible in the four screenshots and not
individually confirmed via source are inferred from Telegram's naming
conventions, the Swift struct they belong to, and (for the `default` style)
the explicit Android-key equivalents already stated in
`theme-wizard/templates/ios/default/CLAUDE.md`. They are most confident for
keys with a stated Android analog or a confirmed source call site, and least
confident for one-off keys with neither — the `Vibrant`/`Opaque` panel-content
split noted in §20 is the clearest example.
