# theme-wizard

A standalone proof-of-concept workspace for the *theme generation system*
itself — separate from the shipped Android app elsewhere in this repo.
Nothing here needs to stay in sync with `app/src/main/assets` or any other
part of the main app; treat the current design as a first draft to iterate
on, not a spec to preserve.

Two things live here:

1. **Improving how theme generation works** — the accent → palette →
   template → finished-file pipeline, the role vocabulary, the templates
   that encode a visual style.
2. **Documenting what's already built** — two platforms (Android, iOS), two
   styles each (Default, Soza), light and dark. This thread is about making
   the ~1,250 element IDs across those templates legible: what each one
   actually draws in the app. See "Current focus" below.

## Map of this folder

| Path | What it is |
|---|---|
| [`theme-generation-flow.md`](theme-generation-flow.md) | Conceptual walkthrough of the pipeline: one accent color → a generated palette → a template → a finished theme, plus how single-element overrides work. Start here. |
| [`color-roles.md`](color-roles.md) | Reference for every named shade ("role") a template can point to — `accent_5`, `gray_8`, `tt_background`, the status colors, the translucent variants. |
| [`templates/android/default/`](templates/android/default) | The "Default" Android style: two templates (dark/light), 841 keys each, each key mapped to a role. Has its own `CLAUDE.md`. |
| [`templates/android/soza/`](templates/android/soza) | The "Soza" Android style — same 841 keys, more accent-forward mappings. Also has its own `CLAUDE.md`. |
| [`templates/ios/`](templates/ios) | The iOS platform's Default/Soza style pair, targeting `.tgios-theme` (417 keys each). Its `CLAUDE.md` explains the dot-path key convention and role/literal split this platform needed that Android didn't. |

There is currently only one Android role vocabulary (the `color-roles.md`
one) and only these two styles per platform — no other template variants
exist in this tree.

## Thread 1 — improving generation itself

Everything described in `theme-generation-flow.md` and `color-roles.md` is
up for reconsideration: the shape of the palette (how many luminosity
steps, how they're derived from one accent color), the set of named roles,
how a template expresses "this element uses that role," how overrides layer
on top, how styles like Default/Soza differentiate themselves. If an idea
improves on the current design, prefer it — this system exists to be
experimented with, not preserved as-is.

Worth knowing before touching the role vocabulary: the current templates
don't actually exercise every luminosity step `color-roles.md` defines.
Android only ever references `accent_{2,3,4,5,7,9}` and `gray_{1,3,5,8,9}`;
iOS uses the full `accent_1`–`accent_9` range but the same restricted
`gray_{1,3,5,8,9}` set. The unused steps aren't dead — they're legitimate,
just not currently reached for by any key — so don't treat "no template
uses `gray_2`" as a reason to remove it.

## Thread 2 — a second platform (iOS)

- **Android (`.attheme`)** is a flat list — hundreds of independent
  `key=#RRGGBB` lines, no nesting, no grouping. That flatness is exactly
  why the current template/role system works the way it does: every key is
  an independent lookup.
- **iOS (`.tgios-theme`)** is a nested, indentation-scoped document —
  sections like `root.tabBar`, `list`, `chat.message` each grouping related
  keys, plus a handful of non-color settings mixed in (`dark: false`,
  `keyboard: light`, `passcode.button: clear`). Some values are also
  8-digit ARGB (`e5f2f2f2`) rather than Android's mostly-6-digit RGB.

[`templates/ios/`](templates/ios) models this platform: a Default/Soza
style pair, same as Android, targeting `.tgios-theme`. It keeps
`color-roles.md`'s vocabulary (almost) unchanged — the same `gray_N`/
`accent_N`/status/transparency roles Android uses, plus one addition
(`tr_background_9`, for iOS's near-opaque translucent bars and panels) —
but doesn't reuse Android's flat-key-per-element shape outright. Instead
each nested iOS key is flattened to a dot-path (`root.tabBar.background`)
so the template stays one flat, diffable JSON object, and the small set of
8 non-color settings (`dark`, `basedOn`, `root.keyboard`, ...) are stored
as literal values rather than role references. See
[`templates/ios/CLAUDE.md`](templates/ios/CLAUDE.md) for the full
reasoning — it's one way to reconcile the structural mismatch, not the
only one, and is as open to rework as everything else in this folder.

## Current focus — what does each element ID actually mean?

The templates map every key to a *role* (`chat_outBubble → accent_5`), but
neither the templates nor these docs currently say what `chat_outBubble`
*is* — which part of the Telegram UI it paints. With 841 Android keys and
417 iOS keys, that's the current gap worth closing: a reference that lets
someone look up an unfamiliar key (`voipgroup_listViewBackground`,
`chat.message.outgoing.bubble.withWp.highlightedBg`) and learn what it
draws, without reverse-engineering it from Telegram's own source.

Nothing under this heading exists yet as a committed file — it's the
direction this workspace is being taken in next, not a finished thread like
the two above.
