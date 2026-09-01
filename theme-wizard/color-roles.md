# Theme Color Roles

The full list of named shades that make up a generated palette (see
[theme-generation-flow.md](theme-generation-flow.md)). Every entry in the
template refers to one of these roles by name instead of to a literal color
— this page is the reference for what each role name means and where its
color comes from.

Roles that come in a numbered family (like `gray_5` or `accent_5`) all share
the same idea: the number is a luminosity step. **5 is the unchanged,
"default" shade. Numbers below 5 get progressively darker, numbers above 5
get progressively lighter.**

## Surface roles

The two foundational roles everything else sits on top of. They flip
between near-black and near-white depending on light/dark mode (and pull
from the device's system wallpaper colors when Monet is enabled).

| Role | Meaning |
|---|---|
| `tt_background` | The app's base background color |
| `tt_onBackground` | The color used for content drawn on top of the background (primary text, icons) |

## Gray ramp — `gray_1` … `gray_9`

A neutral gray, expanded into nine steps from darkest to lightest. Used for
secondary text, dividers, muted icons, and anything that shouldn't compete
visually with the accent.

| Role | Luminosity |
|---|---|
| `gray_1` | Darkest |
| `gray_2` | Very dark |
| `gray_3` | Dark |
| `gray_4` | Slightly dark |
| `gray_5` | Base gray (unchanged) |
| `gray_6` | Slightly light |
| `gray_7` | Light |
| `gray_8` | Very light |
| `gray_9` | Lightest |

> When the theme is generated from the device's wallpaper ("Monet"), the two
> extreme ends of this ramp (`gray_1`, `gray_8`, `gray_9`) are replaced with
> the matching steps from the accent ramp instead, so neutral surfaces still
> feel tied to the wallpaper color.

## Accent ramp — `accent_1` … `accent_9`

The user's chosen accent color, expanded the same way. This is the role
family most of the visually distinctive elements point to — bubbles, tab
indicators, links, unread badges, action buttons.

| Role | Luminosity |
|---|---|
| `accent_1` | Darkest |
| `accent_2` | Very dark |
| `accent_3` | Dark |
| `accent_4` | Slightly dark |
| `accent_5` | Base accent — the exact color the user picked |
| `accent_6` | Slightly light |
| `accent_7` | Light |
| `accent_8` | Very light |
| `accent_9` | Lightest |

## Status colors

Fixed accent colors reserved for specific meanings (avatars, online
indicators, warnings) that stay consistent regardless of the user's chosen
accent, so they remain recognizable across any theme.

| Role | Purpose | Default color |
|---|---|---|
| `red_5` | Red status color | `#E3B727` |
| `orange_5` | Orange status color | `#DF9700` |
| `yellow_5` | Yellow status color | `#E23333` |
| `green_5` | Green status color | `#52CF2C` |
| `blue_5` | Blue status color | `#299FE9` |
| `purple_5` | Purple status color | `#776BF5` |

Unlike the gray and accent families, these only exist at a single
luminosity step (5) — no darker/lighter variants are generated for them.

## Transparency roles

Faded, see-through variants used for subtle overlays, placeholder text, and
service backgrounds — built from the accent and gray ramps above, with
varying levels of opacity layered on top.

| Role | Built from | Opacity |
|---|---|---|
| `transparent_0` | — | Fully transparent |
| `tr_accent_5` | `accent_5` | ~47% opaque |
| `tr_accent_7` | `accent_7` | ~27% opaque |
| `tr_gray_5` | `gray_5` | ~47% opaque |
| `tr_gray_3` | `gray_3` | ~67% opaque |
| `tr_background_9` | `tt_background` | ~90% opaque |

`tr_background_9` was added for the iOS templates (see
[`templates/ios/`](templates/ios)): iOS leans heavily on near-opaque
translucent "materials" for bars and panels (nav bar, tab bar, action
sheet, context menu, dialogs) — a surface that's *almost* `tt_background`
but still shows a hint of whatever's behind it. Android's templates don't
currently need it (nothing in `.attheme` calls for that specific look), but
it's a legitimate general-purpose role, not an iOS-only hack — anything
that wants a nearly-solid, barely-translucent background surface can use
it.

## Platform-specific resolution notes

Roles resolve to a plain `RRGGBB` hex string by default (Android's
`.attheme` format). iOS's `.tgios-theme` format additionally supports
8-digit `AARRGGBB` and the literal keyword `clear`; when resolving a
template for that platform:

- `transparent_0` resolves to the literal `clear` keyword instead of
  `00000000` — iOS honors `clear` directly, so there's no reason to spell
  out a fully-transparent hex value.
- The other transparency roles (`tr_*`) resolve to their natural 8-digit
  `AARRGGBB` form (alpha byte + the base role's hex) rather than being
  flattened to 6 digits.
- Every other role (`gray_*`, `accent_*`, `tt_background`,
  `tt_onBackground`, the status colors) resolves to a plain 6-digit hex,
  same as on Android, for template fields that expect an opaque color.

## How a role turns into a real color

1. The user's accent (and the fixed base tones for gray/background) get
   expanded into the numbered ramps above.
2. Every role name in this document now maps to one concrete color.
3. The template looks up each themeable element's assigned role name in that
   map to get its final color.
4. If the user has manually overridden a specific element, that override
   wins over the role lookup for that element only — every other element
   keeps resolving through its role as normal.
