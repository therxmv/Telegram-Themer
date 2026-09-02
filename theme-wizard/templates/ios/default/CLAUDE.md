# Default style — iOS templates

This folder holds the **"Default"** style template pair:
[`ios_default_dark.json`](ios_default_dark.json) and
[`ios_default_light.json`](ios_default_light.json) — 417 keys each, the
iOS counterpart to [`../../android/default`](../../android/default).

Read [`../CLAUDE.md`](../CLAUDE.md) first — it explains the dot-path key
convention, the role/literal split, and the `gray_9`
"stays-light-regardless-of-mode" convention used throughout this file.
Read [`../../../theme-generation-flow.md`](../../../theme-generation-flow.md)
and [`../../../color-roles.md`](../../../color-roles.md) before that if you
haven't already.

## What "Default" is for

Same baseline, safe-default philosophy as Android's Default — every claim
below matches `templates/android/default/*.json` key-for-key where an
Android equivalent exists.

- **The outgoing bubble is a mid-brightness accent step, and it changes
  with mode.** `bg`/`gradientBg`/`highlightedBg` under
  `chat.message.outgoing.bubble.*` are `accent_3`/`accent_4`/`accent_4` in
  light mode and `accent_5`/`accent_3`/`accent_6` in dark — matching
  Android's `chat_outBubble`/`chat_outBubbleGradient` exactly (the
  `highlightedBg` step has no Android equivalent to port; it's this
  project's own "pressed" feedback, one step toward brighter from
  whichever end `bg` lands on).
- **Content drawn on the outgoing bubble is `tt_background`** — the same
  "inverted" convention Android's Default uses (`chat_messageTextOut`,
  `chat_outTimeText`, `chat_outFileNameText`, `chat_outContactNameText` are
  all `tt_background` in both light and dark on Android, the surface color
  used as content rather than `tt_onBackground`). This only works because
  Default's bubble steps (`accent_3`/`accent_5`) are always mid-brightness,
  never washed out to near-white or near-black — if you're changing the
  bubble fill, double-check this inversion still reads. One exception,
  also ported: file-info/secondary-detail text (`secondaryText`,
  `fileDescription`, `fileDuration`, `mediaInactiveControl`, the poll
  `radioButton`/`highlight`/`separator` keys) is an actual muted gray
  (`gray_8` light / `gray_1` dark), not inverted.
- **The incoming bubble is a plain neutral card**: `bg`/`gradientBg` under
  `chat.message.incoming.bubble.*` resolve to `gray_9` (light) / `gray_1`
  (dark) — the most extreme, quietest steps on the ramp, matching
  Android's `chat_inBubble` exactly.
- **Two things are fixed regardless of mode, matching Android verbatim**:
  the outgoing text-selection knob is `accent_3` in both modes
  (`textSelectionKnob` under the outgoing bubble); the incoming bubble's
  selection knob is `accent_4` light / `accent_3` dark specifically — the
  two bubble directions don't share one flat value. Poll correct/wrong
  answers are `green_5`/`red_5` on both incoming and outgoing.
- **Press/selection feedback is an opaque gray fill, not a translucent
  overlay.** `list.itemHighlightedBg`, `chatList.itemHighlightedBg`, and
  the other "highlighted item" surfaces resolve to `gray_5` (light) /
  `gray_8` (dark) — matching Android's `actionBarDefaultSelector`/
  `listSelector`/`dialogButtonSelector`. Soza uses a translucent
  `tr_gray_5`/`tr_gray_3` for the same surfaces instead; see
  [`../soza/CLAUDE.md`](../soza/CLAUDE.md).
- **Chrome stays neutral.** Nav bar controls, tab bar icons/labels
  (unselected state), and general secondary text all resolve through the
  gray tiers rather than the accent — `root.navBar.control` in particular
  is `gray_5` light / `gray_8` dark here; Soza pushes it to `accent_5`.
- **Materials use `tr_background_9`.** Every near-opaque translucent bar
  or panel (`root.tabBar.background`, `root.navBar.background`,
  `chat.inputPanel.panelBg`, `actionSheet.itemBg`,
  `contextMenu.background`, ...) points at the same role, so they all move
  together if that role's opacity ever gets tuned.
- **`chat.animateMessageColors` is `false`** — Default doesn't opt into
  the animated-gradient bubble treatment; Soza does.

The `gray_9` "stays-light-regardless-of-mode" convention described in
[`../CLAUDE.md`](../CLAUDE.md) still applies here — but only to content
sitting on a genuinely *fixed-brightness* fill (status-color badges, the
reaction pill's active state, filled accent buttons), not to the outgoing
bubble, whose Default fill is mode-dependent as described above.

## Editing checklist

1. Find the key's equivalent in *both* `ios_default_dark.json` and
   `ios_default_light.json` — they're key-for-key parallel; don't let
   them drift apart. Then check the same key in
   [`../soza`](../soza) — the two styles are also key-for-key parallel
   with each other.
2. Point the value at an existing role name from
   [`../../../color-roles.md`](../../../color-roles.md), unless the key is
   one of the 8 non-color literals listed in
   [`../CLAUDE.md`](../CLAUDE.md) (`dark`, `basedOn`, `root.keyboard`,
   `intro.statusBar`/`root.statusBar`, `actionSheet.bgType`,
   `notification.expanded.bgType`, `chat.animateMessageColors`) — those
   stay literal JSON values, never role names.
3. If the key draws content on a genuinely fixed-brightness fill (an
   accent button, a status-color badge, the reaction pill's active state),
   use `gray_9` — see the must-stay-light convention in
   [`../CLAUDE.md`](../CLAUDE.md). The outgoing bubble is *not* this case
   in Default: it's mode-dependent (`accent_3`/`accent_5`), so content on
   it uses `tt_background` instead — see "What Default is for" above
   before changing any `chat.message.outgoing.*` key.
4. If the key is a near-opaque translucent bar/panel background, prefer
   `tr_background_9` over inventing a new literal or reaching for a
   `tr_gray_*`/`tr_accent_*` role that doesn't match its opacity.
5. Sanity-check by reasoning through
   [`../../../theme-generation-flow.md`](../../../theme-generation-flow.md)
   with a couple of very different accent colors — a role-based value
   should look coherent across the whole accent spectrum, not just one
   color you happened to be staring at.

## Coverage

Both templates cover the same 417 leaf keys as
[`../soza`](../soza) — see [`../CLAUDE.md`](../CLAUDE.md) for what counts
as a leaf key. If a key some other tool expects is missing, add it the
same way the Android docs describe: find the nearest analogous key already
here and follow this file's stated conventions for *how* Default diverges
from Soza, rather than leaving it unset.
