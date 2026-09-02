# Soza style — iOS templates

This folder holds the **"Soza"** style template pair:
[`ios_soza_dark.json`](ios_soza_dark.json) and
[`ios_soza_light.json`](ios_soza_light.json) — 417 keys each, the iOS
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
further rather than compete with it. Every claim below matches
`templates/android/soza/*.json` key-for-key where an Android equivalent
exists.

- **The outgoing bubble's brightness flips by mode — pale in light mode,
  deep in dark mode — rather than just sitting a fixed step off Default.**
  This is the style's signature move: `bg` under
  `chat.message.outgoing.bubble.*` is `accent_9` (the *lightest* ramp step)
  in light mode and `accent_2` (a *dark* step) in dark mode. `gradientBg`
  is `tt_background` in *both* modes rather than a second accent step,
  which keeps the internal gradient nearly flat instead of layering on a
  second visible tone. `highlightedBg` (`accent_8` light / `accent_3` dark)
  has no Android equivalent to port; it's this project's own "pressed"
  feedback. A saturated accent like a strong purple or red will make the
  light-mode bubble read as a near-white wash — that's intentional, not a
  contrast bug.
  - **Because the fill's brightness flips, on-bubble content has to flip
    too** — but it's not a flat "everything becomes
    `tt_onBackground`/`accent_5`" rule, it's three distinct tiers:
    - **Primary/message text** (`primaryText`, `linkText`, `scam`) uses
      `tt_onBackground` in both modes.
    - **Secondary/metadata text** (`secondaryText`, `fileDescription`,
      `fileDuration`, `mediaInactiveControl`, the poll
      `radioButton`/`highlight`/`separator` keys) uses `gray_5` (light) /
      `gray_8` (dark) — an opaque gray pair, not a translucent one.
    - **Name/emphasis text and interactive controls** (`accentText`,
      `accentControl`, `mediaActiveControl`, `fileTitle`, poll
      `radioProgress`/`bar`) commit to a flat `accent_5` regardless of
      mode — Soza doesn't make these mode-adaptive, it just always tints
      them.
  - **The text-selection knob and poll correct/wrong-answer colors are
    *not* style differentiators.** The outgoing text-selection knob is
    `accent_3` in all four files (Default and Soza, light and dark) and
    poll correct/wrong answers are `green_5`/`red_5` in all four — so
    `textSelectionKnob` and `polls.barPositive`/`barNegative` here are
    identical to Default's, not Soza-flavored.
- **The incoming bubble is not tinted toward the accent — it's fully
  neutral, blending into the background.** `bg`/`gradientBg` under
  `chat.message.incoming.bubble.*` resolve to `tt_background` here:
  literally the same color as the base chat background. The structural
  fork isn't "Default's incoming bubble is neutral, Soza's is tinted" —
  it's that *both* incoming bubbles are neutral (Default: a quiet gray
  card; Soza: fully invisible against the background), while the
  *outgoing* bubble is the one surface that gets Soza's signature
  treatment. That contrast is deliberate: in Soza, the outgoing bubble is
  the only prominent color in the conversation.
- **The reaction pill is not a style differentiator either.** The inactive
  reaction pill background/text are identical across Default and Soza
  (`accent_9` light / `accent_3` dark background, `tt_onBackground` text)
  in every file. Only `reactionActiveBg`/`reactionActiveFg` (`accent_5` +
  `tt_background`) are this project's own extension, since Android has no
  key for the "you've reacted" state to port from.
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
  `tr_gray_3` (dark) here. See [`../default/CLAUDE.md`](../default/CLAUDE.md)
  for Default's opaque side of this split.
- **Readable neutral text does not get pushed more muted — only icons do.**
  `chatList.dateText`/`chatList.messageText` and their Android analogs
  (`chats_date`/`chats_message`) stay flat `gray_5` in both modes here
  (identical to Default's *light-mode* value, just no longer mode-adaptive)
  — not muted further. Icon keys (`chatList.muteIcon` / `chats_muteIcon`)
  do push further: `gray_9` light / `gray_3` dark, genuinely more muted
  than Default's `gray_8` light / `gray_5` dark. Don't reuse the muted icon
  tier for a *text* key in Soza on the assumption that "more muted" is the
  general Soza move — check whether the key is a `*Text` key (stays
  legible) or an icon/glyph key (mutes further) first.
  - This is also why `gray_9` (the "stays light regardless of mode" role
    from [`../CLAUDE.md`](../CLAUDE.md)) must only ever be paired with a
    genuinely saturated accent/status background, never with a plain gray
    tier — a gray tier can land as light as `gray_8` in light mode, one
    step from `gray_9` itself, which is why keys like
    `chatList.unreadBadgeInactiveText` and
    `chatList.unpinnedArchiveAvatar.foreground` resolve through
    `tt_background` instead of `gray_9`, even though they sit on a
    filled/avatar-shaped surface.
- **`chat.animateMessageColors` is `true`** — Soza opts into the
  animated-gradient bubble treatment; Default doesn't.
- **Materials still use `tr_background_9`**, same as Default — the
  near-opaque translucent bar/panel look isn't a style differentiator, it's
  a platform constant.

## Editing checklist

1. Find the key's equivalent in *both* `ios_soza_dark.json` and
   `ios_soza_light.json` — they're key-for-key parallel; don't let
   them drift apart. Then check the same key in
   [`../default`](../default) — the two styles are also key-for-key
   parallel with each other.
2. When in doubt, lean toward the more saturated/tinted choice over the
   neutral one for anything interactive or selectable — that's what
   distinguishes Soza from Default. For content that must stay neutral,
   check whether it's a readable-text key (stays exactly as legible as
   Default) or an icon/glyph key (pushes one tier more muted) before
   picking a gray step — see the pattern above.
3. If the key draws content on a genuinely fixed-brightness fill (an
   accent button, a status-color badge, the reaction pill's active state),
   use `gray_9` — see the must-stay-light convention in
   [`../CLAUDE.md`](../CLAUDE.md), and its failure mode above. The
   outgoing bubble is *not* this case: its fill flips brightness by mode,
   so content on it uses the three mode-adaptive tiers (primary/secondary/
   emphasis) described above instead of a fixed tone.
4. Sanity-check by reasoning through
   [`../../../theme-generation-flow.md`](../../../theme-generation-flow.md)
   with a couple of very different accent colors — a role-based value
   should look coherent across the whole accent spectrum, not just one
   color you happened to be staring at.

## Coverage

Both templates cover the same 417 leaf keys as
[`../default`](../default) — see [`../CLAUDE.md`](../CLAUDE.md) for what
counts as a leaf key. If a key some other tool expects is missing, add it
the same way: find the nearest analogous key already here and follow this
file's stated conventions for *how* Soza diverges from Default, rather than
leaving it unset.
