# Default style — Android templates

This folder holds the **"Default"** style template pair:
[`android_default_dark.json`](android_default_dark.json) and
[`android_default_light.json`](android_default_light.json) — 819 keys each.

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
lower-saturation option. Measured across the 819 keys in each file:

| | Default (dark / light) | Soza (dark / light) |
|---|---|---|
| `accent_*` | 282 / 283 | 299 / 298 |
| `tt_background` / `tt_onBackground` | 228 / 228 | 169 / 172 |
| `gray_*` | 187 / 187 | 226 / 223 |
| `tr_*` / `transparent_0` | 67 / 66 | 63 / 64 |
| status colors | 53 / 53 | 60 / 60 |

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
  - Exception confirmed on-device: `listSelectorSDK21` — the ripple on *any*
    list row, including chat rows with an avatar photo — must stay
    translucent (`tr_gray_5` in `android_default_light.json`) even in
    Default. An opaque fill there doesn't read as a highlight, it replaces
    the avatar and text outright while pressed.
- **`gray_*`/`accent_*` resolve to the *same* hex in light and dark** — only
  `tt_background`/`tt_onBackground` flip. A role picked for contrast against
  a *flipping* surface (`tt_background`, `chat_wallpaper`,
  `windowBackgroundWhite`, `dialogBackground`, ...) therefore needs the
  **opposite-direction step** between the two files: light's "readable text
  on white" (`gray_3`) becomes dark's "readable text on near-black"
  (`gray_8`); light's "subtle divider on white" (`gray_8`/`gray_9`) becomes
  dark's "subtle divider on near-black" (`gray_3`, not `gray_1` — `gray_1`
  (`#1c1c1c`) is close enough to dark's `tt_background` (`#181818`) to be
  effectively invisible, the same failure mode as picking `gray_9` on a
  white surface in light). Copying a light fix's role name straight into
  the dark file without flipping the step is a real, easy-to-miss bug, not
  a style choice — it happened to ~100 keys here before being caught.
  - The opposite applies to a role that's pinned to one *fixed* shade
    regardless of mode — a status color, or an accent step used for
    branding rather than contrast (e.g. `chat_serviceBackground`,
    `chat_unreadMessagesStartBackground`, both a constant dark-ish accent
    in both files). Content drawn on *that* needs a role that reads as
    light-on-dark in **both** files, which means the two files can't share
    the same content-role name here either: light gets `tt_background`
    (white in light), dark needs `tt_onBackground` (white in dark) — using
    `tt_background` in the dark file would silently go black-on-dark.
  - Roles that are already mode-independent by nature — the status colors,
    and any accent/gray step used for its own sake rather than for contrast
    against a flipping surface (gradient family stops, `*Selected`/pressed
    tint families, syntax-highlight colors) — resolve to the identical hex
    in both files and should just be copied verbatim; don't invert those.
- **Recessed "card" surfaces read as wallpaper/accent-tinted only if you
  point them at the accent ramp directly.** `windowBackgroundGray` /
  `dialogBackgroundGray` / `iv_backgroundGray` are `accent_1`, not a
  `gray_*` step — under Monet the gray ramp only gets accent-tinted at its
  three extremes (`grays[1]/[8]/[9]`, see `color-roles.md`), and outside
  Monet it isn't tinted at all, so a `gray_*` value here reads as flat
  neutral in the common (non-Monet) case. Going straight to `accent_1`
  guarantees the tint in both Monet and manual-accent themes with no
  dependence on that override. Don't use `accent_1`/`windowBackgroundGray`
  for `windowBackgroundWhite` itself, though — that key is the main content
  surface (chat list included), used far too broadly for an accent tint to
  be anything but jarring there; keep it on `tt_background`.
- **When checking contrast on a `tr_*` role, composite it over its real
  backdrop first** — don't treat its own stripped RGB as opaque. A
  translucent fill's *effective* color is `alpha·role + (1-alpha)·backdrop`
  (e.g. `tr_accent_7` over `chat_inBubble`, not `tr_accent_7` alone); doing
  the naive opaque comparison produces false "still broken" contrast
  failures for things that actually render fine.
- **After any scripted/batch edit across many keys, diff against the last
  known-good version and specifically re-check the broad-blast-radius keys**
  (`windowBackgroundWhite`, `dialogBackground`, `chats_menuBackground`,
  `player_background`, and their kin — anything that's effectively "the
  app's main surface," used on dozens of screens) even if they weren't the
  ones you meant to touch. One editing pass this project went through
  silently left six such keys pointed at `accent_2` instead of
  `tt_background` — never intentionally written by any tracked script —
  and because they're used everywhere, the result (the *entire* app reading
  as accent-tinted instead of neutral) looked like a much bigger, scarier
  bug than a six-line JSON diff actually was. A key-count check alone
  (`len(keys) == 819`) won't catch this kind of silent value corruption;
  you have to actually diff the values.
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
   also key-for-key parallel with each other (same 819 keys in every file).
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
6. Copy the edited file to `app/src/main/assets/` and clean-rebuild before
   judging the result on-device — see
   [`../../../CLAUDE.md`](../../../CLAUDE.md) for why both steps are
   necessary.
