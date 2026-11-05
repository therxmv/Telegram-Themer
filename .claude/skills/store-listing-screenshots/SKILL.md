---
name: store-listing-screenshots
description: Generate TelegramThemer's Play Store listing images — capture the 8 required app screenshots on a running emulator/device by driving the real UI with adb, then composite them into the final 1080x1920 marketing tiles (gradient background, headline, phone-frame mockup) plus a 1920x1080 (16:9) feature poster, exactly matching the Claude Design handoff bundle. Triggers on "store screenshots", "Play Store images", "store listing images", "marketing screenshots", "generate store images", "feature poster", "feature graphic".
---

# Store listing screenshots

Produces the 7 final Play Store marketing tiles (1080x1920 PNG each) plus one
1920x1080 (16:9) feature poster, from 8 raw device screenshots of the running
app. Two phases:

1. **Capture** — drive the app on a connected device/emulator with `adb`,
   screenshot each of the 8 required scenes.
2. **Compose** — render each scene into the exact marketing-tile design
   (gradient background, headline with a highlighted phrase, tilted
   phone-frame mockup with notch dot) using the same CSS as the original
   Claude Design handoff, via headless Chromium. The same pass also renders
   the feature poster (eyebrow label, headline, tagline, feature pills,
   three tilted phone frames) from a subset of the same screenshots.

The design source of truth is `config/tiles.json` (marketing tiles) and
`config/poster.json` (feature poster) in this skill — both are literal
transcriptions of the CSS from the handoff bundle in the user's Downloads
(`telegramthemer-store-listing-images/project/Play Store Screenshots.dc.html`
and `.../Feature Poster.dc.html` respectively, referenced when this skill was
created/updated). Don't hand-tune values in there against vibes; if the
design changes, re-transcribe from the `.dc.html` source. Marketing-tile
headlines deliberately omit the word "Telegram" — don't reintroduce it when
re-transcribing; re-word the surrounding text to read naturally instead of
just deleting the word in place.

## Phase 1 — capture

Needs one attached device/emulator (`adb devices`) with a debug build of
`com.therxmv.telegramthemer` installed (`./gradlew installDebug` if not).
Screenshots must come out at **1080x2400** — that's the emulator/device this
was authored against; a different resolution will still compose (the phone
frame crops with `object-fit: cover`) but will crop differently than the
reference.

Use `scripts/ui.py` throughout instead of guessing coordinates — UI layout
can shift between builds:

```bash
python3 scripts/ui.py dump                 # list every clickable/text element + center coords, as JSON
python3 scripts/ui.py tap --text "Dark"    # tap whatever element's text/content-desc/resource-id contains "Dark"
python3 scripts/ui.py tap --xy 540 1200    # raw coordinate tap, when dump doesn't give you a good handle
python3 scripts/ui.py swipe 900 1200 200 1200
python3 scripts/ui.py back
python3 scripts/ui.py screenshot screenshots/01_simple_editor_light.png
```

Before relying on `--text`, run `dump` and read the actual JSON — don't
assume an element's label; confirm it against the live tree.

### Scene sequence

Reaching all 8 scenes cleanly takes one continuous pass — later scenes build
on earlier ones (dark stays on, purple accent stays selected, etc.), so
don't reset in between except at the start. In order:

| # | Output file | State | How to get there |
|---|---|---|---|
| 1 | `01_simple_editor_light.png` | Fresh install: Simple editor, light, default blue accent | `ui.py reset-app` (force-stop + `pm clear` + relaunch `ThemeEditorActivity`) gives a clean first-run state. Screenshot immediately. |
| 2 | `02_advanced_chatlist.png` | Advanced editor, chat-list preview page | Open Advanced mode (tap the pencil/edit FAB on the Simple editor's preview). The Advanced screen is a horizontal pager between a chat-list preview and a bubbles preview — it opens on the chat-list page. Screenshot. |
| 3 | `03_advanced_bubbles.png` | Advanced editor, bubbles preview page | From scene 2, swipe the preview pager left (`ui.py swipe <right-x> <mid-y> <left-x> <mid-y>`) to bring the bubbles/messages phone into view. Screenshot. |
| 4 | `04_color_picker.png` | Color picker bottom sheet open, over the Advanced/bubbles screen | Tap any color-carrying element in the bubbles preview (e.g. a bubble background or text line) to open the picker sheet (color wheel + RGB fields). Screenshot, then dismiss without saving (back out) and navigate back to the Simple editor. |
| 5 | `05_dark_mode.png` | Simple editor, Dark style on, still default blue accent | From the Simple editor's Style row, `tap --text "Dark"`. Screenshot. |
| 6 | `06_dark_purple_accent.png` | Simple editor, Dark on, purple accent selected | Tap the purple swatch in the Accent row (`dump` first — swatches have no text, match by position/color order: default/blue, purple, pink, orange, green, red, then a custom-pipette button). Screenshot. |
| 7 | `07_how_to_use.png` | "How to use?" dialog open over the Dark+purple Simple editor | Tap the **Help** toolbar icon (top-right "?" — `tap --text "Help"`, content-desc `menu_help_action`). This reliably reopens the dialog on demand, independent of first-run state. Screenshot, then dismiss (`tap --text "UNDERSTAND"` or `understand`). |
| 8 | `08_amoled_dark.png` | Simple editor, Dark + AMOLED on, purple accent still selected | From scene 6/7's state, `tap --text "AMOLED"` in the Style row. Screenshot. |

After each `ui.py screenshot`, sanity-check dimensions (`sips -g pixelWidth -g pixelHeight <file>` on macOS, or install `pillow` so `ui.py screenshot` reports it itself) — should read 1080x2400.

If a step doesn't land where expected, `ui.py dump` and look at the actual
tree rather than retrying the same tap blind.

## Phase 2 — compose

Playwright 1.63 requires **Node ≥ 20**; this machine's default `node` is
v18, which fails outright (`npx playwright install` refuses to run). Use the
v22 already available via nvm:

```bash
export NVM_DIR="$HOME/.nvm" && source "$NVM_DIR/nvm.sh" && nvm use 22
```

One-time setup:

```bash
cd .claude/skills/store-listing-screenshots/scripts
npm install               # pulls in Playwright
npx playwright install chromium   # downloads the matching browser build (~280MB) unless already cached
```

Then, with all 8 raw screenshots in `screenshots/` (named exactly as in the
table above — `frames[].image` in `config/tiles.json` references those
filenames):

```bash
node scripts/compose.mjs screenshots/ output/
```

(`compose.mjs` inlines each screenshot as a base64 data URI rather than
`file://`-linking it — a page built via `setContent()` has an opaque origin
that Chromium won't grant local file access to, so a `file://` `<img src>`
silently renders blank. Don't "simplify" this back to a file path.)

This renders each of the 7 tiles in `config/tiles.json` to real HTML with
the original design's exact inline CSS (same gradients, same phone-frame
geometry, same Overpass font loaded from Google Fonts), screenshots it at a
1080x1920 headless-Chromium viewport with `deviceScaleFactor: 1`, and writes
`output/<tile-id>_<timestamp>.png` — the timestamp (`YYYYMMDD-HHMMSS`, local
time) is computed once per run and stamped into every filename from that
run, so freshness is visible directly in a file listing without opening
anything or checking mtimes. That's the same rendering technology the
reference design was authored in, so output should be pixel-identical to
the handoff bundle's `Play Store Screenshots.dc.html` for the same input
screenshots.

The same run then renders `config/poster.json` the same way at a 1920x1080
viewport and writes `output/feature_poster_<timestamp>.png` — the 16:9
feature poster (eyebrow label, headline, tagline, three feature pills, and
three tilted phone frames using `01_simple_editor_light.png`,
`03_advanced_bubbles.png`, `08_amoled_dark.png` from the same `screenshots/`
folder), matching `Feature Poster.dc.html` from the handoff bundle. It's
skipped automatically if `config/poster.json` is missing.

Re-running `compose.mjs` doesn't overwrite or clean up older timestamped
files in `output/` — remove stale ones yourself if you don't want them
lying around.

Verify each tile output is exactly 1080x1920 and the poster output is
exactly 1920x1080, and open one or two to eyeball before treating them as
final — especially tile `06_dark_purple_accent`.
Its `glow` in `config/tiles.json` does **not** match the literal text in
the source `.dc.html`: the source wraps it as
`linear-gradient(180deg, radial-gradient(...), #28211a)`, which is invalid
CSS (confirmed with Playwright — real Chromium drops the whole declaration,
rendering nothing there). But the actual reference render clearly shows a
purple glow in that tile's top-left corner, matching the same
single-`radial-gradient`-fading-to-transparent shape every other tile's
`glow` uses. `tiles.json` reproduces that visible reference rather than the
literal (buggy) source text — see `glowNote` there. If a future re-transcribe
from the `.dc.html` "fixes" this back to the literal broken value, check it
against a real render first.

## Notes

- The 7 output tiles map to 8 input screenshots because tile
  `02_advanced_themer` uses two (`02_advanced_chatlist.png` +
  `03_advanced_bubbles.png`) side by side.
- The feature poster reuses 3 of the same 8 screenshots (`01`, `03`, `08`
  — no separate capture step needed) at smaller phone-frame dimensions than
  the tiles (360x781 vs. the tiles' 646x1401).
- Default resolved design values baked into `compose.mjs`: no eyebrow label
  above the headline, full-strength background glow — those match the
  `.dc.html`'s actual defaults (`showEyebrow` default is `false`, despite
  the name suggesting otherwise; `glowStrength` default is `1`).
- Don't reproduce dev-only elements from the design bundle (the monospace
  filename caption under each tile, the drag handles) — those live outside
  the 1080x1920 canvas in the original and aren't part of the exported
  image.
