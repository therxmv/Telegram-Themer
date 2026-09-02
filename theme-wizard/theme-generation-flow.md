# Theme Generation Flow

A conceptual walkthrough of how one accent color picked by the user turns
into a complete, ready-to-share Telegram theme file — and how a single
element of that theme can be overridden without disturbing the rest.

## The big picture

There are three ingredients that combine to produce a theme:

1. **The accent color** — one color the user picks. Everything else is
   derived from it.
2. **The template** — a fixed list of every themeable element in Telegram
   for one platform (841 elements for Android's `.attheme` format, 417 for
   iOS's `.tgios-theme` format — see [`templates/`](templates)), where each
   element doesn't point to a color directly, but to a *role* like
   "background," "gray, slightly darker," "accent, much lighter."
3. **Overrides** — an optional set of manual exceptions, where the user says
   "for this one specific element, ignore whatever the template says and use
   this color instead."

The final theme file is produced by walking through every element in the
template, resolving each one's role into an actual color, checking whether
an override exists for it, and writing the result out as one line per
element.

## Step 1 — Choosing the accent color

The user picks a single color, either directly (a dedicated "change accent"
button) or indirectly (tapping a themed element in the live preview, which
is explained in the overrides section below). That one color becomes the
seed for the entire theme — nothing else needs to be chosen manually for a
full theme to exist.

Alongside the accent, a handful of simple on/off choices shape the outcome
without needing their own colors: light or dark, an extra-dark "AMOLED"
black, whether bubbles use a gradient, and whether to derive colors from the
device's system wallpaper theme ("Monet") instead of the picked accent.

## Step 2 — Turning one color into a full palette

A single accent color isn't enough to theme an entire app — buttons need a
slightly darker version of it, subtitles need a much lighter, faded version,
backgrounds need something else again. So before anything is applied, the
accent color is expanded into a *ramp* of related shades: the same hue made
progressively darker on one end and progressively lighter on the other, with
the original color sitting in the middle.

The same expansion happens for a neutral gray tone and for background/text
colors (which flip between near-black and near-white depending on light or
dark mode). A few fixed accent colors for status indicators — red, green,
blue, orange, yellow, purple — get the same darker/lighter treatment too,
kept separate from the user's accent so notifications and statuses stay
readable regardless of theme.

The result is a small palette of named shades — "accent, one step darker,"
"accent, default," "accent, three steps lighter," "gray, faded," and so on —
all mathematically derived from the single color the user chose. The full
list of shade names and what they mean lives in
[`color-roles.md`](color-roles.md).

## Step 3 — The template

The template is where the visual design of the theme actually lives. It's a
prepared list pairing every themeable part of the Telegram app — action bar
icon, chat bubble background, unread badge, tab underline, reply-quote line,
timestamp text, and hundreds more — with one shade from the palette built
in Step 2.

Crucially, the template never stores an actual color. It stores a
*reference* to a shade, such as "this element uses the lighter accent shade"
or "this element uses the faded gray." Because of that indirection, the
template itself never has to change when the user picks a new accent — it
always points at the same named shades, and those shades are simply
recalculated from whatever accent is currently selected.

There are two styles per platform — a default style and an alternative
"Soza" style, each with a light and a dark version — so the overall look can
change independently of the accent color. See each style's own `CLAUDE.md`
under [`templates/`](templates) for how Default and Soza actually diverge.

## Step 4 — Applying the template (producing the ready theme)

Producing the actual theme means walking through every entry in the
template, one themeable element at a time, and resolving its shade
reference against the palette calculated from the current accent. "Chat
bubble background points to the lighter accent shade" becomes "chat bubble
background is this exact color." Do that for every entry (841 on Android,
417 on iOS) and the result is a complete, concrete theme — every part of the
app assigned one real color, all of it consistently related back to the
single accent the user picked.

This resolved theme is used two ways at once: it's what repaints the live
mockup inside the app in real time as the user experiments, and — when the
user is ready — it's what gets written out as the finished theme file that
can be shared into Telegram.

A handful of template entries per platform aren't role references at all —
Android has two fixed literal colors (a blur overlay alpha that shouldn't
scale with the accent), and iOS has eight non-color settings (`dark`,
`keyboard`, and similar flags) that get passed through unchanged instead of
resolved against the palette. See each platform's template `CLAUDE.md` for
the exact list.

## Step 5 — Overriding one specific element

Sometimes the template's automatic choice isn't quite right for one element,
and the user wants to fix just that one thing without giving up the rest of
the generated theme. This is done directly from the live preview: tapping an
element on the mockup (say, the outgoing message bubble) opens the same
color picker used for the accent, but this time the picker is scoped to that
one element only.

Whatever color the user chooses becomes a standing exception attached to
that specific element. From then on, whenever the theme is resolved (for
preview or for export), that element skips the normal "look up my shade in
the palette" step entirely and simply uses the manually chosen color — while
every other element keeps following the template as before.

Because some visually distinct elements are secretly made up of more than
one underlying part (an outgoing bubble also has a separate "selected"
appearance; an avatar color also has a matching gradient partner color),
overriding one visible element actually sets every related part together, so
the change looks consistent rather than half-applied.

These overrides are cumulative and independent of the accent: picking a new
accent color recalculates the whole palette and reapplies the template as
usual, but any element the user has manually overridden keeps its manually
chosen color regardless. A dedicated reset action clears all overrides at
once, returning every element to what the template would generate on its
own — without affecting the accent color itself.

## Step 6 — The finished file

The end result is a plain text theme file, one line per themeable element,
each naming that element and giving it a concrete color — the outcome of
resolving the template against the accent-derived palette, with any manual
overrides substituted in over the top. That file is what gets shared to
Telegram (directly to the app when it's installed on the device) as the
finished, ready-to-use theme.

## Summary

- **Accent color** → expanded into a **palette** of related shades (darker
  and lighter versions, plus background/gray/status colors).
- **Template** → a fixed map of every themeable element (841 on Android,
  417 on iOS) to a shade in that palette, never to a literal color, plus a
  small number of fixed literals/flags that bypass the palette entirely.
- **Resolving** the template against the palette produces the actual theme,
  used both for the live preview and the exported file.
- **Overrides** let the user pin one element to a manually chosen color,
  bypassing the template just for that element, while the rest of the theme
  continues to follow the accent.
- **Reset** clears all overrides at once, without touching the accent.
