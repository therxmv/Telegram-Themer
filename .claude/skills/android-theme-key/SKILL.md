---
name: android-theme-key
description: Use when working with TelegramThemer's Android .attheme template keys — looking up what a key like chat_outBubble or windowBackgroundWhiteGrayText draws, what it's paired/related to, or what role (accent_5, gray_8, tr_gray_3, ...) means. Triggers on: what does key X do, explain this theme key, theme key relations, .attheme, ThemeColors, android_default/android_soza json.
---

# Android theme key lookup

Answers "what does this `.attheme` key draw, and what else is it tied to?"
for all 819 keys in the Android templates
(`theme-wizard/templates/android/{default,soza}/*.json` and their mirrors in
`app/src/main/assets/`).

Two documents, two different jobs:

- **`references/` in this skill** (below) — what a key *draws*, and its
  relations to other keys. Start here for "what is `chat_outBubble`".
- **[`theme-wizard/color-roles.md`](../../../theme-wizard/color-roles.md)** —
  what a *role* (`accent_5`, `gray_8`, `tr_gray_3`, …) resolves to. A
  template maps each key to one of these role names; this skill's reference
  says what the key means, `color-roles.md` says what the role means.

This is a lookup/explanation skill — it answers "what is this key" and "what
is it related to", not "how do I safely change it across every template
file".

## Relation vocabulary

Every reference table ends in a **Relations** column using this fixed
vocabulary — keys are almost never independent, so read this column before
treating any key as isolated:

| Tag | Meaning |
|---|---|
| `pair:in/out` | Incoming vs outgoing message counterpart. |
| `pair:rest/selected` | Normal vs multi-select-highlighted state of the same element. |
| `pair:on/off` | Checked/unchecked, active/inactive, enabled/disabled, pressed/unpressed. |
| `pair` | A two-key relationship that is none of the above — two gradient stops, a label and its subtitle, an element and its shadow. |
| `fill→on-fill` | A surface and the icon/text drawn on top of it. Contrast pair. |
| `family` | An enumerated set that only looks right if kept coherent. |
| `mirror` | The same element in another context (a sheet, the archive, the drawer). |
| `alias` | A second key for the same element under a different name. |

## Markers and the "Telegram default" column

- **`†`** — the key is in the templates but not in the sample theme
  (`theme-wizard/memory-map/test-theme/Default-light-monet-757.attheme`) the
  reference was originally built from. Not a warning, just provenance.
- **Telegram default** — Telegram's own stock **light**-theme value for the
  key, from `createDefaultColors()` in `ThemeColors.java` (Telegram ships no
  dark defaults). `#RRGGBB` is opaque; `#AARRGGBB` carries an alpha byte,
  usually the point of the key (overlays, selectors, shadows). `—` means
  Telegram computes the color at runtime (from the wallpaper or the accent)
  rather than shipping a stock value.

## Reference index

Six files, split by UI surface so a lookup only loads the part of the app
it's actually about. If the surface isn't obvious, `grep -rn '<key>'
.claude/skills/android-theme-key/references/` finds it regardless of file.

| File | Covers |
|---|---|
| [`references/foundations-and-navigation.md`](references/foundations-and-navigation.md) | Window chrome/text ramps, action bar and tab bars, chat list, side menu, avatars |
| [`references/chat-screen.md`](references/chat-screen.md) | The chat screen in full — bubbles, media, composer, reactions, replies/forwards, Instant View buttons, quotes/code/tables. The largest surface, 347 of 819 keys |
| [`references/controls-dialogs-profile.md`](references/controls-dialogs-profile.md) | Form controls, dialogs and bottom sheets, profile screen |
| [`references/media-calls-stories.md`](references/media-calls-stories.md) | Music player, stories, location/maps, shared media/files/sticker store, group voice and video (`voipgroup_*`) |
| [`references/charts-iv-gifts-misc.md`](references/charts-iv-gifts-misc.md) | Statistics charts, Instant View, gifts/stars/Premium/polls, one-off screens, named palette slots (`color_*`) |
| [`references/patterns-and-provenance.md`](references/patterns-and-provenance.md) | Suffix patterns for decoding a key not otherwise listed, open questions, how the reference was compiled |

## When a key isn't in this reference

These tables aim to cover all 819 keys, but if you hit one they somehow
miss — or want to confirm a description first-hand instead of trusting the
inferred ones flagged in `patterns-and-provenance.md` §20 — go straight to
the Android client source instead of guessing:

- **[`DrKLO/Telegram`](https://github.com/DrKLO/Telegram)** — the actual
  Android client this project themes.
- **[`ThemeColors.java`](https://github.com/DrKLO/Telegram/blob/master/TMessagesProj/src/main/java/org/telegram/ui/ActionBar/ThemeColors.java)**
  — the full key table (`colorKeysMap`) and Telegram's own stock
  light-theme defaults (`createDefaultColors()`). Start here for "does this
  key even exist" and "what does Telegram ship as its default".
- **[`ActionBar/Theme.java`](https://github.com/DrKLO/Telegram/blob/master/TMessagesProj/src/main/java/org/telegram/ui/ActionBar/Theme.java)**
  — wallpaper/service-background rendering logic and the bulk of the
  `getColor(Theme.key_*)` call sites, i.e. what a key actually draws.
- Still nothing in those two files? Search the whole repo for the key name:
  `https://github.com/search?q=repo%3ADrKLO%2FTelegram+%22<key_name>%22&type=code`
  (swap in the key, e.g. `chat_attachPhotoBackground`) — that's how this
  reference resolved its ambiguous one-off keys in the first place (see
  `patterns-and-provenance.md`, "Semantics").

**Found it? Write it back before moving on.** This reference is only as
useful as it is exhaustive — a key looked up in source and then discarded
just means the next lookup repeats the same dig. Add a row for it to
whichever `references/*.md` file matches its surface (see the Reference
index above), matching that file's existing `Key | Draws | Example |
Relations` columns, and name the source file/method you confirmed it from.
If it's a genuinely new suffix pattern rather than one more key of a kind
already listed, add it to `references/patterns-and-provenance.md` §19
instead (or as well).

## Scope

Android only. iOS's templates (`theme-wizard/templates/ios/`, 417 dot-path
keys) have their own reference at
[`ios-theme-key`](../ios-theme-key/SKILL.md) — for an iOS key, use that
skill instead of guessing from this map.
