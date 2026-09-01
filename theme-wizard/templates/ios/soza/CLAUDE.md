# Soza style — iOS templates

This folder holds the **"Soza"** style template pair:
[`soza_dark_template.json`](soza_dark_template.json) and
[`soza_light_template.json`](soza_light_template.json) — the iOS
counterpart to [`../../android/soza`](../../android/soza).

Read [`../CLAUDE.md`](../CLAUDE.md) first — it explains the dot-path key
convention, the role/literal split, and the `gray_9`
"stays-light-regardless-of-mode" convention used throughout this file.
Read [`../../../theme-generation-flow.md`](../../../theme-generation-flow.md)
and [`../../../color-roles.md`](../../../color-roles.md) before that if you
haven't already.

## What "Soza" is for

Same relationship to Default as on Android: pick it when the accent color
should visibly dominate more of the UI, and neutral chrome should recede
further rather than compete with it. Every claim below was checked
directly against `templates/android/soza/*.json`, not inferred from the
iOS samples — see [`../CLAUDE.md`](../CLAUDE.md) for why Android is the
reference and iOS is the port. A few claims in an earlier version of this
file turned out to be guesses that didn't hold up against the actual
Android data (noted inline below) — this section reflects the corrected
version.

- **The outgoing bubble's brightness flips by mode — pale in light mode,
  deep in dark mode — rather than just sitting a fixed step off Default.**
  This is the style's signature move: `chat_outBubble` on Android is
  `accent_9` (the *lightest* ramp step) in light mode and `accent_2` (a
  *dark* step) in dark mode — not a uniform "one step darker than Default"
  in both modes. `chat_outBubbleGradient` is `tt_background` in *both*
  modes rather than a second accent step, which is why this file's
  `gradientBg` resolves to `tt_background` too (not a second pale/deep
  accent value) — it keeps the internal gradient nearly flat instead of
  layering on a second visible tone. `highlightedBg` (`accent_8`/`accent_3`)
  has no Android equivalent to port; it's this project's own "pressed"
  feedback. A saturated accent like a strong purple or red will make the
  light-mode bubble read as a near-white wash — that's intentional, not a
  contrast bug.
  - **Because the fill's brightness flips, on-bubble content has to
    flip too** — but it's not a flat "everything becomes
    `tt_onBackground`/`accent_5`" rule, it's three distinct tiers ported
    from three distinct Android key families:
    - **Primary/message text** (`primaryText`, `linkText`, `scam`) uses
      `tt_onBackground`, matching `chat_messageTextOut` exactly (flat
      `tt_onBackground` in both modes on Android).
    - **Secondary/metadata text** (`secondaryText`, `fileDescription`,
      `fileDuration`, `mediaInactiveControl`, and the poll
      `radioButton`/`highlight`/`separator` keys) uses `gray_5` (light) /
      `gray_8` (dark) — an opaque gray pair, not a translucent one —
      matching `chat_outTimeText`/`chat_outFileInfoText` precisely.
    - **Name/emphasis text and interactive controls**
      (`accentText`, `accentControl`, `mediaActiveControl`, `fileTitle`,
      poll `radioProgress`/`bar`) commit to a flat `accent_5` regardless
      of mode, matching `chat_outContactNameText`, `chat_outFileNameText`,
      `chat_outLoader`, and the rest of that family — Soza doesn't make
      these mode-adaptive, it just always tints them.
  - **The text-selection knob and poll correct/wrong-answer colors are
    *not* style differentiators.** `chat_outTextSelectionCursor` is
    `accent_3` in all four Android files (Default and Soza, light and
    dark) and `chat_outPollCorrectAnswer`/`WrongAnswer` are
    `green_5`/`red_5` in all four — so `textSelectionKnob` and
    `polls.barPositive`/`barNegative` here are identical to Default's,
    not Soza-flavored.
- **The incoming bubble is *not* tinted toward the accent.** An earlier
  version of this file claimed it picks up a faint accent wash — that
  was a guess, and it was wrong. Android's `chat_inBubble` in Soza is
  `tt_background` in *both* modes: literally the same color as the base
  chat background, blending away entirely. `bg`/`gradientBg` under
  `chat.message.incoming.bubble.*` resolve to `tt_background` here for
  exactly that reason. The real structural fork isn't "Default's incoming
  bubble is neutral, Soza's is tinted" — it's that *both* incoming bubbles
  are neutral (Default: a quiet gray card; Soza: fully invisible against
  the background), while the *outgoing* bubble is the one surface that
  gets Soza's signature treatment. That contrast is deliberate: in Soza,
  the outgoing bubble is the only prominent color in the conversation.
- **The reaction pill is *not* a style differentiator either** — another
  corrected guess. `chat_outReactionButtonBackground`/
  `chat_inReactionButtonBackground` are identical across Default and Soza
  (`accent_9` light / `accent_3` dark) in every file, and
  `chat_outReactionButtonText` is `tt_onBackground` in all four. So the
  inactive reaction pill here is the same in Default and Soza; only
  `reactionActiveBg`/`reactionActiveFg` (`accent_5` + `tt_background`) are
  this project's own extension, since Android has no key for the
  "you've reacted" state to port from — see [`../CLAUDE.md`](../CLAUDE.md).
- **Accent reaches further into chrome that Default leaves neutral.**
  `root.navBar.control` (the nav bar's control/chevron tint) and
  `passcode.button` both resolve to `accent_5` here, where Default keeps
  them neutral (`gray_*`) or transparent. When adding a new key, lean
  toward giving interactive/selectable chrome an accent role here rather
  than a neutral one — that's the split that defines Soza, matching how
  Android Soza reaches for `accent_*` on secondary chrome (reply lines,
  seekbars, loaders) that Default leaves gray.
- **Press/selection feedback is a translucent overlay, not an opaque
  fill.** `list.itemHighlightedBg`, `chatList.itemHighlightedBg`, and the
  other "highlighted item" surfaces resolve to `tr_gray_5` (light) /
  `tr_gray_3` (dark) here — verified against `actionBarDefaultSelector`/
  `listSelector`/`dialogButtonSelector`, which switch from Default's
  opaque gray to exactly this translucent pair in every Soza file. See
  [`../default/CLAUDE.md`](../default/CLAUDE.md) for Default's opaque
  side of this split.
- **Readable neutral text does *not* get pushed more muted — only icons
  do, and even then not by much.** An earlier version of this file
  claimed Soza mutes *all* neutral content one tier further than Default
  (`GRAY_SOFT` → `GRAY_MUTED`, i.e. `gray_5`/`gray_8` → `gray_8`/`gray_3`)
  and applied that to ~30 fields, including things like
  `chatList.dateText`, `chatList.messageText`, and every list/search
  placeholder and section-header text. That claim was never actually
  checked against Android — and it doesn't hold. `chats_message`/
  `chats_date`/`windowBackgroundWhiteGrayText`/
  `windowBackgroundWhiteHintText`/`player_time` are all `gray_5` in Soza
  **light mode, identical to Default**, not `gray_8`. The bug this
  produced was concrete, not just theoretical: `gray_9`
  (`chatList.unreadBadgeInactiveText`) sitting on a `gray_8`
  (`chatList.unreadBadgeInactiveBg`) background — two adjacent, nearly
  identical light grays — made unread counts on muted chats almost
  unreadable in light mode. That specific pairing is fixed (the text key
  now resolves through `tt_background`, matching both the real iOS
  samples' flat `ffffff`/`000000` for that key and Android's
  `chats_unreadCounterText`), and the same audit turned up two more
  instances of the identical mistake (`list.disclosureActions.inactive.fg`,
  `chatList.unpinnedArchiveAvatar.foreground`) with the same fix.
  - The corrected rule, from `chats_message`/`chats_date` (text: `gray_5`
    in both modes, matching Default's light-mode value, not muted further)
    vs. `chats_muteIcon`/`chats_pinnedIcon` (icons: `gray_9` light /
    `gray_3` dark — genuinely more muted than Default's `gray_8`/`gray_5`
    or `gray_8`/`gray_3`): **readable text keys stay exactly as legible as
    Default's** (`GRAY_SOZA_TEXT` = flat `gray_5` in both modes), **icon/
    decorative keys push further toward invisible** (`GRAY_SOZA_ICON` =
    `gray_9` light / `gray_3` dark). Don't reuse the flat `GRAY_MUTED`
    tier for a *text* key in Soza on the assumption that "more muted" is
    the general Soza move — check whether the Android analog is a `*Text`
    key (stays legible) or an icon/glyph key (mutes further) first.
  - This is also why `gray_9`/`G9FIXED` (the "stays light regardless of
    mode" role from [`../CLAUDE.md`](../CLAUDE.md)) must only ever be
    paired with a genuinely saturated accent/status background, never
    with a plain gray tier — a gray tier can land as light as `gray_8`
    in light mode, one step from `gray_9` itself.
- **`chat.animateMessageColors` is `true`** — Soza opts into the
  animated-gradient bubble treatment; Default doesn't.
- **Materials still use `tr_background_9`**, same as Default — the
  near-opaque translucent bar/panel look isn't a style differentiator, it's
  a platform constant.

## Editing checklist

1. Find the key's equivalent in *both* `soza_dark_template.json` and
   `soza_light_template.json` — they're key-for-key parallel; don't let
   them drift apart. Then check the same key in
   [`../default`](../default) — the two styles are also key-for-key
   parallel with each other.
2. When in doubt, lean toward the more saturated/tinted choice over the
   neutral one for anything interactive or selectable — that's what
   distinguishes Soza from Default. For content that must stay neutral,
   lean toward the more muted gray tier rather than Default's standard
   one, per the pattern above.
3. If the key draws content on a genuinely fixed-brightness fill (an
   accent button, a status-color badge, the reaction pill's active state),
   use `gray_9` — see the must-stay-light convention in
   [`../CLAUDE.md`](../CLAUDE.md). The outgoing bubble is *not* this case:
   its fill flips brightness by mode, so content on it uses the three
   mode-adaptive tiers (primary/secondary/emphasis) described above
   instead of a fixed tone.
4. Sanity-check by reasoning through
   [`../../../theme-generation-flow.md`](../../../theme-generation-flow.md)
   with a couple of very different accent colors — a role-based value
   should look coherent across the whole accent spectrum, not just one
   color you happened to be staring at.

## Coverage

Both templates cover the same 417 leaf keys found in
[`../../../samples/ios`](../../../samples/ios) (every leaf in both sample
files except `name`, which isn't a themeable element) — the same set
[`../default`](../default) covers. If a key some other tool expects is
missing, add it the same way: find the nearest analogous key already here
and follow this file's stated conventions for *how* Soza diverges from
Default, rather than leaving it unset.
