# theme-wizard

A standalone proof-of-concept workspace for the *theme generation system*
itself — separate from the shipped Android app elsewhere in this repo.
Treat the current design as a first draft to iterate on, not a spec to
preserve.

**Exception, and it matters in practice:** `app/src/main/assets/
android_default_{dark,light}.json` (and the Soza pair alongside them) *are*
literally the same 819-key files as
[`templates/android/default/`](templates/android/default)'s — the shipped
app reads its templates straight from Android assets, not from this
folder, and there's no build step or symlink that keeps the two in sync.
If you edit a template here and want it to show up in a real build (which
is the only way a person can actually see or screenshot the result), you
must copy the file across yourself:
```
cp theme-wizard/templates/android/default/android_default_dark.json \
   app/src/main/assets/android_default_dark.json
```
And even then: a plain incremental build can serve a **stale** merged copy
from `app/build/intermediates/assets/.../mergeDebugAssets/` — if a change
that's confirmed present in `app/src/main/assets/` still isn't showing up
on-device, `./gradlew :app:clean :app:assembleDebug` (or Android
Studio's Clean Project + Rebuild) before assuming the template itself is
wrong.

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
| [`templates/android/default/`](templates/android/default) | The "Default" Android style: two templates (dark/light), 819 keys each, each key mapped to a role. Has its own `CLAUDE.md`. |
| [`templates/android/soza/`](templates/android/soza) | The "Soza" Android style — same 819 keys, more accent-forward mappings. Also has its own `CLAUDE.md`. |
| [`templates/ios/`](templates/ios) | The iOS platform's Default/Soza style pair, targeting `.tgios-theme` (417 keys each). Its `CLAUDE.md` explains the dot-path key convention and role/literal split this platform needed that Android didn't. |
| [`memory-map/`](memory-map) | Raw source material for the Android key reference: the sample `.attheme` and screenshots it was built against. The compiled reference itself now lives in the [`android-theme-key`](../.claude/skills/android-theme-key/SKILL.md) skill (see below). |

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
they don't say what `chat_outBubble` *is* — which part of the Telegram UI it
paints. Closing that gap means a reference someone can look an unfamiliar key
up in (`voipgroup_listViewBackground`,
`chat.message.outgoing.bubble.withWp.highlightedBg`) without
reverse-engineering it from Telegram's own source.

**The Android half is done** and lives as a skill rather than a single doc:
[`.claude/skills/android-theme-key/`](../.claude/skills/android-theme-key/SKILL.md) covers
all 819 keys, split into six `references/*.md` files by UI surface (so a
lookup only loads the surface it's about), with the relations between keys
(`chat_in*`/`chat_out*` twins, `*Selected` states, `dialog*` ↔
`windowBackgroundWhite*` mirrors, gradient-stop families) and Telegram's own
stock color for each. It was originally compiled as a single
`memory-map/android-element-map.md` file, which has since been split into the
skill's `references/` — `memory-map/` now holds only the sample `.attheme`
and screenshots it was built against.

Building it also reconciled the templates against Telegram's key table in
both directions. 124 keys Telegram no longer reads were dropped — except the
five `--glass_*` keys, whose stray `--` prefix was the only thing wrong with
them, so they were renamed to the real `glass_*` names. Then 97 keys
Telegram *does* read but the templates never covered were added: message
quote/code/table blocks, the 30-key `chat_msgIvButton*` set, the Premium
gradient family, the real wallpaper gradient stops, and `share_*`. Net 841
→ 819, and the two sets are now identical — nothing ignored on import,
nothing left unthemed.

Each addition inherits its role from the closest existing analog, per file,
so the Default/Soza and light/dark conventions carry over. Two things to
keep in mind, both written up at the end of the map: the new wallpaper
gradient stops are the one change that could alter how a background renders
(all four resolve to the same role, so it should look identical — worth
confirming), and the bubble-gradient toggle in `AndroidThemeValuesProvider`
now has to filter four keys instead of one, so any future gradient key must
be added to `GRADIENT_KEYS` as well.

**iOS is still open**: the same treatment for `templates/ios/`'s 417
dot-path keys has not been written.
