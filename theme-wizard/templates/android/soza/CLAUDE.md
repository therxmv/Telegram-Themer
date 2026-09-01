# Soza style — Android templates

This folder holds the **"Soza"** style template pair:
[`soza_dark_template.json`](soza_dark_template.json) and
[`soza_light_template.json`](soza_light_template.json).

Read [../../../theme-generation-flow.md](../../../theme-generation-flow.md)
and [../../../color-roles.md](../../../color-roles.md) first — this file
assumes you already know what a "role" (`accent_5`, `gray_8`, `tt_background`,
...) is and how a template turns into a finished theme.

This whole `theme-wizard` tree is a standalone proof-of-concept for the
theme generation system itself — the templates, the role list, and the
generation logic they describe are all design material, not a fixed spec.
Changing or improving any of it is welcome; treat the descriptions below as
"here's the reasoning behind the current state," not "here's what must stay
true."

## What "Soza" is for

Soza is the alternative, more saturated/expressive style — pick it when you
want the accent color to visibly dominate more of the UI. Compared to the
Default style in [`../default`](../default), measured across the two
templates:

- **The accent role is used more, and more broadly** (roughly 240 keys vs.
  ~221 in Default) — Soza reaches for `accent_*` on secondary chrome that
  Default would leave neutral: reply lines, voice seekbars, loaders, file
  name/info text, action-mode selectors, etc. all get an accent tint here.
- **Neutral `tt_background`/`tt_onBackground` fallbacks are used less**
  (~155 keys vs. ~208 in Default) — where Default plays it safe with a flat
  background/foreground color, Soza more often commits to a tinted role
  instead.
- **Outgoing-bubble internals read as "tinted," not "inverted."** Where
  Default maps things like `chat_outLoader`, `chat_outVoiceSeekbar`,
  `chat_outReplyLine`, `chat_outTimeText` to `tt_background`, Soza maps the
  same keys to an accent or gray shade (e.g. `chat_outReplyLine → accent_5`,
  `chat_outTimeText → gray_8`). The outgoing bubble fill itself
  (`chat_outBubble`) is also pinned to a darker step (`accent_2`) rather
  than the base `accent_5` Default uses, so the bubble reads as a deep tinted
  panel with accent/gray content on top of it, rather than a bright accent
  panel with neutral content on top.
- **More translucent (`tr_*`) roles are used** (~35 keys vs. ~20 in Default)
  — Soza leans on `tr_accent_5`/`tr_accent_7`/`tr_gray_5`/`tr_gray_3` for
  overlays and selectors more than Default does.
- **A few status-color keys use the fuller palette** (`yellow_5`, `purple_5`
  show up more often here) — e.g. `chat_attachPollText → yellow_5` and
  `chat_attachGalleryText → purple_5`, where Default just reuses `accent_5`
  for both. Keep that spirit when adding new attach/status-type keys: prefer
  a distinct status color over reusing the accent everywhere.
- One key, `chat_BlurAlpha`, is the **only literal color** in this template
  rather than a role reference (`#AA000000` in dark, `#BE000000` in light —
  a fixed blur-overlay alpha, not something that should scale with the
  accent). Every other key must reference a role name from
  [`color-roles.md`](../../../color-roles.md), never a literal hex value.

## Editing checklist

1. Pick the key you want to change (or add) and find its equivalent in
   *both* `soza_dark_template.json` and `soza_light_template.json` — the two
   files are key-for-key parallel; don't let them drift apart.
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

## Key sets are kept in parallel across styles

Soza and Default both cover the same 712 keys now (each file's own
dark/light pair has always matched exactly). They didn't start that way —
the two templates were originally captured independently, so each had a
handful of keys the other lacked (e.g. Soza alone had
`profile_tabText`/`profile_tabSelectedText` and the `voipgroup_*` group-call
keys; Default alone had `chat_outBroadcast` and `chat_shareBackground`). The
~17 keys Soza was missing were backfilled from Default, reasoning from the
closest analogous key already in Soza (e.g. a key's "in"/"out" or
"unselected"/"selected" sibling, or another key in the same functional
family) rather than copying Default's value outright, so the new entries
read as native Soza choices, not transplants. Default's ~20 missing keys
were filled in the same way, anchored to Default's own existing conventions
instead.

If a template still turns out to be missing a key some other tool expects,
that's a gap worth closing the same way — find the nearest analogous key
already in the file, follow the pattern established above for *how* this
style diverges from the other, and add it rather than leaving it unset.

## Absorbed 148 keys found only in Monet

[`../monet`](../monet) uses an entirely different, much larger role
vocabulary (Material You's tonal palettes, not `color-roles.md`'s roles —
see its `CLAUDE.md`), but it also simply *covers more UI surface area* than
Default/Soza did — things like the Premium gradients, the group-call
(`voipgroup_*`) screen in full, the image viewer, gift ribbons, reaction
buttons. Soza and Default both now cover those same 148 keys too (712 → 860
keys each), resolved into `color-roles.md`'s vocabulary rather than left
unset. One key monet has, `chat_outBubbleGradientAnimated`, was **not**
added — it's a boolean flag riding along in a color file, not an actual
color, so it isn't a role-template key at all.

None of these 148 were translated by reading Monet's tone number as if it
meant the same thing in our system (its own `CLAUDE.md` explains why that
doesn't transfer). Each one was resolved by: (1) finding the closest
existing sibling already in this file — an `in`/`out` pair, a `Selector`/
`Selected` variant, another key in the same functional family — and
mirroring *that* key's established relationship, or (2) when nothing closer
existed, falling back to a simple rule keyed off whether Monet classified
the key as accent-tinted or neutral (roughly: accent-family → `accent_5`,
neutral-family at a near-black/near-white tone on a background-shaped key
→ `tt_background`, neutral-family on a text/icon-shaped key → `gray_9`/
`gray_3`, anything else neutral → `gray_5`). A few needed judgment calls
that don't come from either rule: `chat_BlurAlphaSlow` got a second
literal-hex exception alongside `chat_BlurAlpha` (a lower-alpha sibling,
scaled from this file's own dark/light-asymmetric `chat_BlurAlpha` pair),
the Premium star-related keys got `yellow_5` instead of following Monet's
neutral classification (stars read as gold in this app's real semantics,
not gray), and `statisticChartLine_cyan`/`chat_tagCreator` were mapped onto
`blue_5`/`purple_5` since this system has no dedicated cyan role.

**The one thing worth re-checking if you touch any of these**: several are
background-and-foreground pairs (a button fill plus the icon/text drawn on
it, e.g. `voipgroup_muteButton` + `voipgroup_muteButton2`). Giving both
halves of a pair the same role — especially `accent_5` on both — makes the
content invisible against its own background. If you're adding a key that's
clearly layered on top of another new key, give it a role that's proven to
contrast (`gray_9`/`gray_3` against a `tt_background` fill; a distinctly
different accent step against an `accent_*` fill), not just whatever the
general fallback rule would hand you.
