# Theme verification

Generates real, resolved test theme files from `theme-wizard/templates/`
and checks them for contrast/distinctness issues - "how the final theme
actually looks in the Telegram client" (white background + light grey
text, accent button + accent button content, etc.), not the abstract role
names in a template.

## Why resolve first, then check

Templates never store colors, only role references (`gray_8`, `accent_5`,
...). Reasoning about contrast from role names alone means replaying the
palette math in your head. Resolving to real hex first removes that layer:
checks run on the literal `#RRGGBB` pairs a user would actually see.

## Run it

```bash
python3 theme-wizard/verification/generate_themes.py
python3 theme-wizard/verification/check_contrast.py
```

`generate_themes.py` is a byte-faithful Python port of the app's own
resolution code (`ThemeColorsProvider`, `AndroidThemeFileAdapter`,
`IosThemeFileAdapter` - see `resolve.py`'s docstring for exact source
files), verified to produce identical output to the real app for every
shared key against `theme-wizard/samples/android/*.attheme`. It resolves
each (platform x style x scenario) into `theme-wizard/test-themes/`:

- the real output file (`.attheme` / `.tgios-theme`)
- a `<scenario>.resolved.json` sidecar (key -> role, intended color, and
  what that platform's real exporter actually writes - these two can
  differ, see "Android's alpha-drop" below)

`SCENARIOS` in `generate_themes.py` covers 9 accent/light-dark/amoled
combinations chosen to stress both ends of the palette math (saturated,
pale, and very dark accents) - not just the shipped-sample blue. Extend
that list to widen coverage; Monet is intentionally out of scope (it seeds
from live wallpaper colors with no static input to reproduce headlessly).

`check_contrast.py` then runs WCAG contrast + distinctness checks (pair
definitions in `pairs_android.json` / `pairs_ios.json`) across every
generated scenario and writes:

- `theme-wizard/reports/<platform>_<style>.json` - full structured results
- `theme-wizard/reports/SUMMARY.md` - human-readable findings, in priority order

## The role-pair registry (`pairs_android.json` / `pairs_ios.json`)

Extends the fg/bg groupings already curated in
`app/src/main/java/com/therxmv/telegramthemer/data/adapter/ThemeToPreviewAdapter.kt`
(what the app itself treats as one visual unit - chat bubbles, chat list
rows, appbar, player panel) rather than re-deriving pairs by guessing at
naming conventions each run. It's incomplete by construction - covers the
pairs identified so far, not all ~700/~419 template keys - and is meant to
grow over time as a checked-in file, not be rebuilt from scratch per run.

Each entry is `{id, bg, fg, kind, ...}`. `kind: "text"` requires >=4.5:1
contrast, `"icon"`/`"large_text"` requires >=3.0:1 (WCAG AA). A `role:X`
value (instead of a template key) looks the color up directly from the
resolved palette - used for canonical surfaces like `tt_background` that
many keys share 1:1 rather than through one specific key.

`distinct_pairs` are a second, cheaper kind of check: two keys that must
NOT resolve to the same real color (selected vs. unselected tab text,
active vs. inactive reaction pill, ...) - plain equality, catches
"invisible state" bugs that pass contrast math but still look broken.

## Reading the report: where does a fix belong

`check_contrast.py` classifies each pair's failures across all 9 scenarios:

- **fails in 0** - OK.
- **fails in SOME, not all** ("isolated") - accent-dependent. Fix at the
  **template** level: reassign that one key to a different existing role
  (a step lighter/darker on the same ramp) in the relevant
  `theme-wizard/templates/<platform>/<style>/*.json` file.
- **fails in ALL 9** ("systemic") - not accent-dependent, so a template
  reassignment for one key won't fully fix it. Points at the **role
  pairing itself**, or the ramp math in `ThemeColorsProvider`/
  `TintsExtensions.kt`, or `theme-wizard/color-roles.md`'s definition of
  that role - since every key using that role pair inherits the same
  problem.

Never edit the generated files in `test-themes/` directly - they're
disposable output, regenerated from the template every run.

## Applying fixes (`apply_fixes.py`)

```bash
python3 theme-wizard/verification/apply_fixes.py
python3 theme-wizard/verification/generate_themes.py   # re-resolve with the new roles
python3 theme-wizard/verification/check_contrast.py    # confirm
```

For every "isolated"/"systemic" pair, reassigns the **fg** key (never bg) to
whichever role in `gray_1..9` (+ `accent_1..9` if the original was an accent
shade, + `tt_background`/`tt_onBackground`) scores best across that mode's
scenarios - applied only if it's a strict improvement. Logs every decision
(including "nothing beats the current choice, left unchanged") to
`theme-wizard/reports/FIXES_APPLIED.md`.

Two correctness traps this went through, both now handled automatically -
worth knowing about if you extend the pair registries:

- **Two pairs sharing one fg key.** Android's `chats_unreadCounterText` is
  the fg for both `unread_counter` and `unread_counter_muted` (different
  bg). Optimizing each pair independently let whichever ran second silently
  overwrite the first's fix. Fixed by grouping all pairs by `(mode, fg_key)`
  and scoring candidates jointly across every constraint that key is under.
- **Maximizing contrast alone collapses visual hierarchy.** The very first
  run reassigned nearly everything to `tt_onBackground` (it has the highest
  contrast against almost any neutral background), which passed every
  contrast check but silently made primary/secondary pairs identical - e.g.
  `chats_name`/`chats_message`, or worse, `chat_messagePanelText`/
  `chat_messagePanelHint` (a typed-text color indistinguishable from its own
  placeholder). Fixed by adding those pairs to `distinct_pairs` too, so the
  same post-pass that resolves accidental `distinct_pairs` collisions
  (`repair_distinctness`) catches these. If you see two roles that read as
  "primary/secondary" of each other converge to the same value after a fix
  pass, that's the sign to add the pair to `distinct_pairs` rather than
  assume it's fine because contrast passes.

What's left after a fix pass is the genuinely hard residual: text/icons/
buttons drawn **on an accent-colored surface** (`chat_outBubble`,
`chats_unreadCounter`, `actionControlBg`, a reaction pill, an avatar
background) where the 9 scenarios' accent spans from very pale to very
dark. No single static role can contrast against both extremes - the real
fix is a luminance-aware/dynamic text color (choose light or dark text
based on the accent's own brightness), which is an app-logic change, not a
template edit. `apply_fixes.py` correctly declines to force a role there
and logs it as "likely needs a non-static-role fix" instead.

## Two structural findings this surfaced (not accent-dependent - see SUMMARY.md #1-2)

- A handful of keys (`chat_BlurAlpha`, `statisticChartLine_indigo` in the
  light default template, ...) point at something that isn't a real role
  name (a literal hex string, or a typo like `"pu_700"`). The real app's
  `TintedThemeColors.get()` has no match for these and silently falls back
  to solid black, in every scenario, regardless of accent.
- Android's `AndroidThemeFileAdapter` writes every value through
  `Int.colorToHex()`, which strips alpha unconditionally (see
  `app/src/main/java/com/therxmv/telegramthemer/data/extensions/ColorExtensions.kt`,
  which itself carries a `// TODO think about transparency`) - unlike the
  iOS adapter, which does special-case translucent roles. Every Android key
  templated to a `tr_*`/`transparent_0` role (dozens per style) therefore
  ships as a solid opaque color instead of the translucent overlay the
  template intends.
