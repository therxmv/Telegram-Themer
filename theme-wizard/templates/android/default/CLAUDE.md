# Default style — Android templates

This folder holds the **"Default"** style template pair:
[`android_default_dark.json`](android_default_dark.json) and
[`android_default_light.json`](android_default_light.json) — 841 keys each.

Read [../../../theme-generation-flow.md](../../../theme-generation-flow.md)
and [../../../color-roles.md](../../../color-roles.md) first — this file
assumes you already know what a "role" (`accent_5`, `gray_8`, `tt_background`,
...) is and how a template turns into a finished theme.

This whole `theme-wizard` tree is a standalone proof-of-concept for the
theme generation system itself — the templates, the role list, and the
generation logic they describe are all design material, not a fixed spec.
Changing or improving any of it is welcome; treat the description below as
"here's the reasoning behind the current state," not "here's what must stay
true."

## What "Default" is for

This is the baseline, safe-default look — a starting point before reaching
for something louder like "Soza." Compared to the Soza style in
[`../soza`](../soza), Default is the more conservative, higher-contrast,
lower-saturation option. Measured across the 841 keys in each file:

| | Default (dark / light) | Soza (dark / light) |
|---|---|---|
| `accent_*` | ~286 / 287 | ~303 / 302 |
| `tt_background` / `tt_onBackground` | ~246 / 247 | ~183 / 186 |
| `gray_*` | ~206 / 207 | ~225 / 222 |
| `tr_*` / `transparent_0` | ~46 / 47 | ~63 / 65 |
| status colors | ~52 / 53 | ~65 / 65 |

Default leans on the neutral `tt_background`/`tt_onBackground` pair and the
gray ramp far more than Soza does, and reaches for the accent and
translucent (`tr_*`) roles less.

- **Accent color is used sparingly**, mostly on the elements that must carry
  it semantically (outgoing bubble fill, tab indicators, links, unread
  badges, primary buttons). A lot of secondary chrome — selectors, submenu
  icons, action-mode backgrounds — falls back to a neutral `gray_*` role or
  to `tt_background`/`tt_onBackground` rather than a tinted accent shade.
- **Outgoing-bubble internals read as "inverted."** Things drawn on top of
  the accent-colored outgoing bubble (`chat_outLoader`, `chat_outVoiceSeekbar`,
  `chat_outReplyLine`, `chat_outTimeText`, `chat_outFileNameText`,
  `chat_outContactNameText`, `chat_messageTextOut`, ...) are mapped to
  `tt_background` here, not to an accent or gray shade the way Soza does it.
  If you're editing bubble-related keys, keep that convention: content on
  the outgoing bubble should reference `tt_background`, not `accent_*`.
  - One exception: file-info/secondary-detail text (`chat_outFileInfoText`)
    is an actual muted gray (`gray_8` light / `gray_1` dark), not inverted.
  - The bubble fill itself is mode-dependent, not fixed: `chat_outBubble` is
    `accent_3` in light mode and `accent_5` in dark; `chat_outBubbleGradient`
    is `accent_4` light / `accent_3` dark. `tt_background` for the inverted
    content reads fine against either, because both bubble steps stay
    mid-brightness — if you push the bubble fill toward near-white or
    near-black, double-check contrast on the internals too.
- **Selectors and press states are an opaque gray fill**, not a translucent
  overlay: `actionBarDefaultSelector`, `listSelector`,
  `dialogButtonSelector`, and similar "highlighted item" keys all resolve to
  `gray_5` (light) / `gray_8` (dark). Soza uses a translucent `tr_gray_5`/
  `tr_gray_3` for the same surfaces instead.
- **Two literal colors, not role references**: `chat_BlurAlpha`
  (`#BE000000` in both light and dark) and `chat_BlurAlphaSlow`
  (`#99000000` in both) — a fixed blur-overlay alpha and its slower
  sibling, neither of which should scale with the accent. Every other key
  in this template must reference a role name from
  [`color-roles.md`](../../../color-roles.md).

## Editing checklist

1. Pick the key you want to change (or add) and find its equivalent in
   *both* `android_default_dark.json` and `android_default_light.json` —
   the two files are key-for-key parallel; don't let them drift apart.
   Then check the same key in [`../soza`](../soza) — the two styles are
   also key-for-key parallel with each other (same 841 keys in every file).
2. Point the value at an existing role name from
   [`color-roles.md`](../../../color-roles.md) (`gray_1`–`gray_9`,
   `accent_1`–`accent_9`, `tt_background`, `tt_onBackground`,
   `red_5`/`orange_5`/`yellow_5`/`green_5`/`blue_5`/`purple_5`,
   `transparent_0`, `tr_accent_5`, `tr_accent_7`, `tr_gray_5`, `tr_gray_3`) —
   or propose a new role in `color-roles.md` if the existing set can't
   express what you're going for. This is a POC: the role system itself is
   fair game to extend or rework, not just the templates that consume it.
3. Match the file's existing convention for that *kind* of element (e.g. all
   the "text drawn on the outgoing bubble" keys should agree with each
   other, not each pick a different role at random) — unless the whole point
   of your change is to rethink that convention, in which case update this
   file's description of it too.
4. Sanity-check by reasoning through
   [theme-generation-flow.md](../../../theme-generation-flow.md) with a
   couple of very different accent colors — a role-based value should look
   coherent across the whole accent spectrum, not just the one color you
   were staring at while editing.
5. If a template key's value is neither a known role name nor one of the
   two documented literals above, that's a bug (not a third kind of
   allowed exception) — fix it to a role name.
