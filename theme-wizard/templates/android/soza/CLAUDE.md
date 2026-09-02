# Soza style — Android templates

This folder holds the **"Soza"** style template pair:
[`android_soza_dark.json`](android_soza_dark.json) and
[`android_soza_light.json`](android_soza_light.json) — 819 keys each, the
same key set as [`../default`](../default).

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

## What "Soza" is for

Soza is the alternative, more saturated/expressive style — pick it when you
want the accent color to visibly dominate more of the UI. Compared to the
Default style in [`../default`](../default), measured across the same 819
keys:

| | Default (dark / light) | Soza (dark / light) |
|---|---|---|
| `accent_*` | 282 / 283 | 299 / 298 |
| `tt_background` / `tt_onBackground` | 228 / 228 | 169 / 172 |
| `gray_*` | 187 / 187 | 226 / 223 |
| `tr_*` / `transparent_0` | 67 / 66 | 63 / 64 |
| status colors | 53 / 53 | 60 / 60 |

- **The accent role is used more, and more broadly** — Soza reaches for
  `accent_*` on secondary chrome that Default would leave neutral: reply
  lines, voice seekbars, loaders, file name/info text, action-mode
  selectors, etc. all get an accent tint here.
- **Neutral `tt_background`/`tt_onBackground` fallbacks are used less** —
  where Default plays it safe with a flat background/foreground color,
  Soza more often commits to a tinted role instead.
- **Outgoing-bubble internals read as "tinted," not "inverted," and the
  bubble fill itself flips brightness by mode** rather than sitting a fixed
  step off Default. `chat_outBubble` is `accent_9` (the *lightest* ramp
  step) in light mode and `accent_2` (a *dark* step) in dark mode — pale in
  light mode, deep in dark mode. `chat_outBubbleGradient` is `tt_background`
  in *both* modes rather than a second accent step, keeping the internal
  gradient nearly flat. A saturated accent like a strong purple or red will
  make the light-mode bubble read as a near-white wash — that's the
  intended effect, not a contrast bug.
  - Because the fill's brightness flips, on-bubble content is *not* a flat
    "always `accent_5`" rule — it splits into three tiers:
    - **Primary/message text** (`chat_messageTextOut`) uses
      `tt_onBackground` in both modes.
    - **Secondary/metadata text** (`chat_outTimeText`, `chat_outFileInfoText`)
      uses an opaque gray pair: `gray_5` light / `gray_8` dark.
    - **Name/emphasis text and interactive controls**
      (`chat_outContactNameText`, `chat_outFileNameText`, `chat_outLoader`,
      `chat_outReplyLine`, `chat_outVoiceSeekbar`) commit to a flat
      `accent_5` regardless of mode.
  - `chat_inBubble` is `tt_background` in both modes — literally the same
    color as the base chat background, blending away entirely. The
    outgoing bubble is the one surface in the conversation that gets Soza's
    signature treatment; the incoming bubble stays neutral by disappearing
    into the background instead of picking up a tint.
- **Selectors and press states are a translucent overlay**, not an opaque
  fill: `actionBarDefaultSelector`, `listSelector`, `dialogButtonSelector`,
  and similar "highlighted item" keys resolve to `tr_gray_5` (light) /
  `tr_gray_3` (dark). Default uses an opaque `gray_5`/`gray_8` for the same
  surfaces instead.
- **A few status-color keys use the fuller palette** — e.g.
  `chat_attachPollText → yellow_5` and `chat_attachGalleryText → purple_5`,
  where Default just reuses `accent_5` for both. Keep that spirit when
  adding new attach/status-type keys: prefer a distinct status color over
  reusing the accent everywhere.
- **Two literal colors, not role references**, and — unlike Default — they
  differ between light and dark: `chat_BlurAlpha` is `#AA000000` in dark
  mode and `#BE000000` in light; its sibling `chat_BlurAlphaSlow` is
  `#80000000` dark / `#99000000` light. Every other key in this template
  must reference a role name from
  [`color-roles.md`](../../../color-roles.md).

## Editing checklist

1. Pick the key you want to change (or add) and find its equivalent in
   *both* `android_soza_dark.json` and `android_soza_light.json` — the two
   files are key-for-key parallel; don't let them drift apart. Then check
   the same key in [`../default`](../default) — the two styles are also
   key-for-key parallel with each other (same 819 keys in every file).
2. Point the value at an existing role name from
   [`color-roles.md`](../../../color-roles.md) (`gray_1`–`gray_9`,
   `accent_1`–`accent_9`, `tt_background`, `tt_onBackground`,
   `red_5`/`orange_5`/`yellow_5`/`green_5`/`blue_5`/`purple_5`,
   `transparent_0`, `tr_accent_5`, `tr_accent_7`, `tr_gray_5`, `tr_gray_3`) —
   or propose a new role in `color-roles.md` if the existing set can't
   express what you're going for. This is a POC: the role system itself is
   fair game to extend or rework, not just the templates that consume it.
3. When in doubt, lean toward the more saturated/tinted choice over the
   neutral one — that's what distinguishes Soza from Default. Match the
   file's existing convention for that *kind* of element rather than
   guessing (e.g. check how the equivalent "in" key was mapped before
   choosing the "out" key's role, and vice versa) — unless the whole point
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
