---
name: ios-theme-key
description: Use when working with TelegramThemer's iOS .tgios-theme template keys — looking up what a dot-path key like chat.message.incoming.bubble.withWp.bg or root.navBar.control draws, what it's paired/related to, or what role (accent_5, gray_8, tr_background_9, ...) means. Triggers on: what does key X do, explain this theme key, theme key relations, .tgios-theme, PresentationTheme, ios_default/ios_soza json.
---

# iOS theme key lookup

Answers "what does this `.tgios-theme` key draw, and what else is it tied to?"
for all 417 keys in the iOS templates
(`ios_default_{light,dark}.json` and `ios_soza_{light,dark}.json` in
`app/src/main/assets/`).

`references/` in this skill (below) documents what a key *draws*, and its
relations to other keys. Start here for "what is
`chat.message.outgoing.bubble.withWp.bg`".

This is a lookup/explanation skill — it answers "what is this key" and "what
is it related to", not "how do I safely change it across every template
file".

## Key shape: dot-paths, not flat names

Unlike Android's flat `chat_outBubble`-style keys, a `.tgios-theme` file is
nested and indentation-scoped. The templates flatten that nesting into a
dot-path (`root.tabBar.background`, `chat.message.incoming.bubble.withWp.bg`)
that round-trips losslessly back to the real nested YAML-like format. Every
key in this reference is written exactly as it appears in the templates and
in a real `.tgios-theme` export — search for it verbatim.

## Relation vocabulary

Every reference table ends in a **Relations** column. Most of the vocabulary
is shared with [`android-theme-key`](../android-theme-key/SKILL.md) — read
that skill's table if you haven't — with two iOS-specific additions:

| Tag | Meaning |
|---|---|
| `pair:withWp/withoutWp` | The two bubble/panel variants depending on whether the chat has a custom wallpaper set. Almost always identical or near-identical values — see [`patterns-and-provenance.md`](references/patterns-and-provenance.md). |
| `pair:withDefaultWp/withCustomWp` | Same idea, one level up: `chat.serviceMessage.components.*` picks between these based on whether the wallpaper is literally plain white (`color(0xffffff)`) or anything else, built-in pattern included. |
| `pair:in/out` | Incoming vs outgoing message counterpart. On iOS these live under separate `chat.message.incoming.*` / `chat.message.outgoing.*` subtrees rather than a suffix, but the relation is the same. |
| `pair:rest/pressed` | Normal vs highlighted/touch-down state of the same element (`*Bg` / `*HighlightedBg`, `buttonStroke` / `buttonHighlightedStroke`). |
| `pair:on/off` | Checked/unchecked, active/inactive, reaction-tapped/not-tapped. |
| `pair` | A two-key relationship that is none of the above — two gradient stops, a fill and its stroke, a label and its subtitle. |
| `fill→on-fill` | A surface and the icon/text drawn on top of it. Contrast pair. |
| `family` | An enumerated set that only looks right if kept coherent (the seven disclosure-action colors, the nine chart keys). |
| `mirror` | The same element in another context (a sheet vs the main screen, the archive avatar vs a regular one). |
| `alias` | A second key for the same concept under a different name/screen (`intro.statusBar` vs `root.statusBar` — same concept, two independent screens). |

## No `†` marker — there's no coverage gap to flag

Android's reference marks keys the templates have but the sample theme
doesn't (or vice versa) with `†`. That gap doesn't exist here: the 417 keys
in `ios_default_light.json` are **exactly** the keys found in two real,
independently-authored `.tgios-theme` exports used to ground this reference
(see below) minus their one non-themeable field (`name`). Every key this
reference documents is a real, live key Telegram's iOS client reads.

## Provenance: source code + real exports, not a guess

This reference is grounded in three things, cross-checked against each
other — see
[`patterns-and-provenance.md`](references/patterns-and-provenance.md) for
the full account:

1. **Telegram-iOS source** (`TelegramMessenger/Telegram-iOS`,
   `submodules/TelegramPresentationData/Sources/`) — `PresentationTheme.swift`
   defines the struct each key belongs to (and its Swift property name,
   which is almost always more verbose than the dot-path segment);
   `PresentationThemeCodable.swift` defines the exact `CodingKeys` strings
   that produce the `.tgios-theme` format and the fallback logic Telegram
   applies when an older theme file omits a key; `DefaultDayPresentationTheme.swift`
   is the constructor for Telegram's own stock "Day" theme, used to verify
   ambiguous keys against a known-good render.
2. **Two real, independently-made `.tgios-theme` exports** — "Blue Shadow"
   (a `day`/light theme) and "Instant Blue" (a `night`/dark theme) — used as
   the **Example** column in every table. Both turn out to be close
   recolors of Telegram's own stock Day/Night Classic themes (most neutral
   values match `DefaultDayPresentationTheme.swift`/
   `DefaultDarkPresentationTheme.swift` literally), which is good independent
   confirmation that the templates' role assignments track a real render.
3. **This project's own templates** (`app/src/main/assets/ios_*.json`),
   cross-checked against [`android-theme-key`](../android-theme-key/SKILL.md)
   where a real Android-key analog exists.

## Reference index

Six files, split by UI surface — the same split Android's reference uses, so
if you know that skill's layout this one is a straight parallel.

| File | Covers |
|---|---|
| [`references/foundations-and-chrome.md`](references/foundations-and-chrome.md) | Intro/onboarding, passcode lock, root chrome (status bar, tab bar, nav bar, search bar, keyboard), Settings-style `list.*` rows, `chart.*`. 112 of 417 keys |
| [`references/chat-list.md`](references/chat-list.md) | The main Chats screen — rows, badges, story rings, archive avatars, pinned/secret/verified states. 45 keys |
| [`references/chat-bubbles.md`](references/chat-bubbles.md) | `chat.message.*` — bubbles (incoming/outgoing/freeform), polls, reactions, Instant View action buttons, delivery/selection state. The largest surface, 147 keys |
| [`references/chat-panels.md`](references/chat-panels.md) | The rest of the chat screen around the bubbles — service messages, the text composer, the sticker/GIF/emoji panel, the reply-keyboard button panel, jump-to-bottom control, the wallpaper/animation switches. 75 keys |
| [`references/sheets-menus-notifications.md`](references/sheets-menus-notifications.md) | Action sheets, the long-press context menu, in-app and expanded notification banners. 36 keys |
| [`references/patterns-and-provenance.md`](references/patterns-and-provenance.md) | Suffix/shape patterns for decoding a key not otherwise listed (`withWp`/`withoutWp`, `bg`/`gradientBg`/`highlightedBg`, the 8 non-color literal keys), and how this reference was compiled |

## When a key isn't in this reference

These tables aim to cover all 417 keys, but if you hit one they somehow
miss — or want to confirm a description first-hand instead of trusting the
inferred ones flagged in `patterns-and-provenance.md` §20 — go straight to
the iOS client source instead of guessing:

- **[`TelegramMessenger/Telegram-iOS`](https://github.com/TelegramMessenger/Telegram-iOS)**
  — the actual iOS client this project themes.
- **[`PresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/PresentationTheme.swift)**
  — the struct every dot-path segment belongs to, and its (usually more
  verbose) Swift property name. Start here for "what struct owns this key".
- **[`PresentationThemeCodable.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/PresentationThemeCodable.swift)**
  — the exact `CodingKeys` strings that produce the `.tgios-theme` dot-path,
  plus the fallback logic documented in `patterns-and-provenance.md` §19.
- **[`DefaultDayPresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/DefaultDayPresentationTheme.swift)**
  / **[`DefaultDarkPresentationTheme.swift`](https://github.com/TelegramMessenger/Telegram-iOS/blob/master/submodules/TelegramPresentationData/Sources/DefaultDarkPresentationTheme.swift)**
  — the constructors for Telegram's own stock Day/Night themes, useful to
  verify an ambiguous key against a known-good render.
- Still nothing in those three files? Search the whole repo for the key
  (its dot-path, or the last segment or two):
  `https://github.com/search?q=repo%3ATelegramMessenger%2FTelegram-iOS+%22<key.or.segment>%22&type=code`
  — that's how this reference confirmed `message.freeform` and
  `panelContentVibrantOverlayColor` (see `patterns-and-provenance.md` §20).

**Found it? Write it back before moving on.** This reference is only as
useful as it is exhaustive — a key looked up in source and then discarded
just means the next lookup repeats the same dig. Add a row for it to
whichever `references/*.md` file matches its surface (see the Reference
index above), matching that file's existing `Key | Draws | Example |
Relations` columns, and name the source file/struct/method you confirmed it
from. If it's a genuinely new shape pattern rather than one more key of a
kind already listed, add it to `references/patterns-and-provenance.md` §18
instead (or as well).

## Scope

iOS only. Android's templates (819 flat keys) have their own reference at
[`android-theme-key`](../android-theme-key/SKILL.md) — for an Android key,
use that skill instead of guessing from the iOS map.
