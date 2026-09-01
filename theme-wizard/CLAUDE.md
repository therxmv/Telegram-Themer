# theme-wizard

A standalone proof-of-concept workspace for the *theme generation system*
itself — separate from the shipped Android app elsewhere in this repo. Two
things live here:

1. **Improving how theme generation works** — the accent → palette →
   template → finished-file pipeline, the role vocabulary, the templates
   that encode a visual style. Nothing in this folder needs to match what
   the app currently ships; treat the current design as a first draft to
   iterate on, not a spec to preserve.
2. **Exploring new theme platforms** — today the only implemented platform
   is Android's `.attheme` format. The `samples/ios` reference files exist
   because Telegram for iOS has its own, structurally different theme
   format, and adding platform #2 is explicitly in scope for this POC.

Nothing here needs to stay in sync with `app/src/main/assets` or any other
part of the main app — this is free-standing design material.

## Map of this folder

| Path | What it is |
|---|---|
| [`theme-generation-flow.md`](theme-generation-flow.md) | Conceptual walkthrough of the pipeline: one accent color → a generated palette → a template → a finished theme, plus how single-element overrides work. Start here. |
| [`color-roles.md`](color-roles.md) | Reference for every named shade ("role") a template can point to — `accent_5`, `gray_8`, `tt_background`, the status colors, the translucent variants. |
| [`templates/android/default/`](templates/android/default) | The "Default" Android style: two templates (dark/light), each key mapped to a role. Has its own `CLAUDE.md` describing that style's specific look. |
| [`templates/android/soza/`](templates/android/soza) | The "Soza" Android style, same shape. Also has its own `CLAUDE.md`. |
| [`templates/android/monet/`](templates/android/monet) | A third Android style using a completely different, much larger role vocabulary (Material You's 5-palette tonal system) instead of `color-roles.md`'s `gray_N`/`accent_N`. Its own `CLAUDE.md` explains the role system and how it diverges structurally from Default/Soza. |
| [`templates/ios/`](templates/ios) | The iOS platform's Default/Soza style pair, targeting `.tgios-theme`. Its `CLAUDE.md` explains the dot-path key convention and role/literal split this platform needed that Android didn't. |
| [`samples/android/`](samples/android) | Real, finished `.attheme` files — flat `key=#hexcolor` text — for seeing what a Default/Soza-style template resolves *into*. |
| [`samples/ios/`](samples/ios) | Real Telegram-for-iOS `.tgios-theme` files — the reference material [`templates/ios/`](templates/ios) was built from. |

## The two threads, in more detail

### Thread 1 — improving generation itself

Everything described in `theme-generation-flow.md` and `color-roles.md` is
up for reconsideration: the shape of the palette (how many luminosity
steps, how they're derived from one accent color), the set of named roles,
how a template expresses "this element uses that role," how overrides layer
on top, how styles like Default/Soza differentiate themselves. If an idea
improves on the current design, prefer it — this system exists to be
experimented with, not preserved as-is.

### Thread 2 — a second platform (iOS)

Open one of the `.tgios-theme` files in `samples/ios` and compare it to a
`.attheme` file in `samples/android`: they don't just use different color
syntax, they're organized completely differently.

- **Android (`.attheme`)** is a flat list — hundreds of independent
  `key=#RRGGBB` lines, no nesting, no grouping. That flatness is exactly
  why the current template/role system works the way it does: every key is
  an independent lookup.
- **iOS (`.tgios-theme`)** is a nested, indentation-scoped document —
  sections like `root.tabBar`, `list`, `chat.message` each grouping related
  keys, plus a handful of non-color settings mixed in (`dark: false`,
  `keyboard: light`, `passcode.button: clear`). Some values are also
  8-digit ARGB (`e5f2f2f2`) rather than Android's mostly-6-digit RGB.

[`templates/ios/`](templates/ios) now models this platform: a Default/Soza
style pair, same as Android, targeting `.tgios-theme`. It keeps
`color-roles.md`'s vocabulary (almost) unchanged — the same `gray_N`/
`accent_N`/status/transparency roles Android uses, plus one addition
(`tr_background_9`, for iOS's near-opaque translucent bars and panels) —
but doesn't reuse Android's flat-key-per-element shape outright. Instead
each nested iOS key is flattened to a dot-path (`root.tabBar.background`)
so the template stays one flat, diffable JSON object, and the small set of
non-color settings (`dark`, `keyboard`, `basedOn`, ...) are stored as
literal values rather than role references. See
[`templates/ios/CLAUDE.md`](templates/ios/CLAUDE.md) for the full
reasoning — it's one way to reconcile the structural mismatch, not the
only one, and is as open to rework as everything else in this folder.
