# Default style — Android templates

This folder holds the **"Default"** style template pair:
[`default_dark_template.json`](default_dark_template.json) and
[`default_light_template.json`](default_light_template.json).

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

## What "Default" is for

This is the baseline, safe-default look — a starting point before reaching
for something louder like "Soza." Compared to the Soza style in
[`../soza`](../soza), Default is the more conservative, higher-contrast,
lower-saturation option:

- **Accent color is used sparingly**, mostly on the elements that must carry
  it semantically (outgoing bubble fill, tab indicators, links, unread
  badges, primary buttons). A lot of secondary chrome — selectors, submenu
  icons, action-mode backgrounds — falls back to a neutral `gray_*` role or
  to `tt_background`/`tt_onBackground` rather than a tinted accent shade.
- **Outgoing-bubble internals read as "inverted."** Things drawn on top of
  the accent-colored outgoing bubble (`chat_outLoader`, `chat_outVoiceSeekbar`,
  `chat_outReplyLine`, `chat_outTimeText`, `chat_outReplyMessageText`, ...)
  are mapped to `tt_background` here, not to an accent or gray shade the way
  Soza does it. If you're editing bubble-related keys, keep that convention:
  content on the outgoing bubble should reference `tt_background`, not
  `accent_*`.
  - Note: `tt_background` for these inverted purposes reads on a *light*
    bubble; if you're deliberately darkening the outgoing bubble fill
    (`chat_outBubble`), double-check contrast on the internals too.
- **Fewer transparent/`tr_*` roles** are used overall than Soza — Default
  favors solid neutral fills over translucent overlays.
- One key, `chat_BlurAlpha`, is the **only literal color** in this template
  (`#BE000000`, ~74%-opaque black) rather than a role reference — it's a
  fixed blur-overlay alpha, not something that should scale with the accent.
  Every other key must reference a role name from
  [`color-roles.md`](../../../color-roles.md), never a literal hex value.

## Editing checklist

1. Pick the key you want to change (or add) and find its equivalent in
   *both* `default_dark_template.json` and `default_light_template.json` —
   the two files are key-for-key parallel; don't let them drift apart.
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

## Key sets are kept in parallel across styles

Default and Soza both cover the same 712 keys now (each file's own
dark/light pair has always matched exactly). They didn't start that way —
the two templates were originally captured independently, so each had a
handful of keys the other lacked (e.g. Default alone had `chat_outBroadcast`
and `chat_shareBackground`; Soza alone had `profile_tabText` and the
`voipgroup_*` keys). The ~20 keys Default was missing were backfilled from
Soza, reasoning from the closest analogous key already in Default (e.g. a
key's "in"/"out" or "unselected"/"selected" sibling, or another key in the
same functional family) rather than copying Soza's value outright, so the
new entries read as native Default choices, not transplants. Soza's ~17
missing keys were filled in the same way, anchored to Soza's own existing
conventions instead.

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
buttons. Default and Soza both now cover those same 148 keys too (712 → 860
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
→ `tt_background`, neutral-family on a text/icon-shaped key → `tt_onBackground`,
anything else neutral → `gray_8`/`gray_5`). A few needed judgment calls that
don't come from either rule: `chat_BlurAlphaSlow` got a second literal-hex
exception alongside `chat_BlurAlpha` (a lower-alpha sibling), the Premium
star-related keys got `yellow_5` instead of following Monet's neutral
classification (stars read as gold in this app's real semantics, not gray),
and `statisticChartLine_cyan`/`chat_tagCreator` were mapped onto `blue_5`/
`purple_5` since this system has no dedicated cyan role.

**The one thing worth re-checking if you touch any of these**: several are
background-and-foreground pairs (a button fill plus the icon/text drawn on
it, e.g. `voipgroup_muteButton` + `voipgroup_muteButton2`). Giving both
halves of a pair the same role — especially `accent_5` on both — makes the
content invisible against its own background. If you're adding a key that's
clearly layered on top of another new key, give it a role that's proven to
contrast (`tt_onBackground` or `gray_9`/`gray_3` against a `tt_background`
fill; a distinctly different accent step against an `accent_*` fill), not
just whatever the general fallback rule would hand you.
