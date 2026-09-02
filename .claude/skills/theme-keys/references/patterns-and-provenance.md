> Part of the [theme-keys](../SKILL.md) skill's Android key reference. Covers §19 Cross-cutting suffix patterns (decode a key this reference somehow missed), §20 Open questions/Worth watching, and how the reference was originally compiled.

## 19. Cross-cutting suffix patterns

If you meet a key this map somehow doesn't list, decode it from these. They hold
across every namespace above.

| Suffix / prefix | Means | Example |
|---|---|---|
| `chat_in*` / `chat_out*` | Received vs sent message. Always a pair. | — | `chat_inBubble` / `chat_outBubble` |
| `*Selected` | The multi-select state of the same element — **not** "the selected tab". | — | `chat_inTimeText` / `chat_inTimeSelectedText` |
| `*Checked` | A toggle/checkbox in its on state. | — | `switchTrack` / `switchTrackChecked` |
| `*Pressed` | The touch-down state of a button. | — | `chats_actionBackground` / `chats_actionPressedBackground` |
| `*Unchecked` / `*Disabled` | The off and non-interactive states. | — | `checkboxSquareUnchecked`, `checkboxSquareDisabled` |
| `*Active` / `*Unactive` | Selected vs unselected tab. Telegram's spelling is "Unactive", not "Inactive". | — | `actionBarTabActiveText` / `actionBarTabUnactiveText` |
| `*Archived` | The same element inside the Archived Chats list. | — | `chats_name` / `chats_nameArchived` |
| `*_threeLines` | The same element with three-line chat previews enabled. | — | `chats_message` / `chats_message_threeLines` |
| `*Cats` | The navigation drawer header's default patterned state, used when no wallpaper is set. | — | `chats_menuPhone` / `chats_menuPhoneCats` |
| `*Unscrolled` / `*Scrolled` | A group-call surface before vs after the sheet slides up. | — | `voipgroup_actionBar` / `voipgroup_actionBarUnscrolled` |
| `*Background` + `*Icon` / `*Text` | A fill and what is drawn on it. Always a contrast pair. | — | `chats_archiveBackground` / `chats_archiveIcon` |
| trailing `1` `2` `3` | Gradient stops, not alternatives. Change the whole set. | `voipgroup_topPanelBlue1` / `2` |
| numbered `Text2`…`Text8` | Luminosity steps of one semantic text color. | `windowBackgroundWhiteGrayText2`–`8` |
| `dialog*` | The `windowBackgroundWhite*` element redrawn inside a sheet. | — | `dialogTextBlack` ↔ `windowBackgroundWhiteBlackText` |
| leading `key_` | Telegram's own inconsistency: eight keys keep the `key_` prefix in the file format itself. Not a mistake to fix. | `key_graySectionText`, `key_sheet_other` |

---

## 20. Open questions

Everything the first two passes raised is now closed. What follows is what to
watch, not what is unknown.

**Closed — the templates and Telegram's key table are the same 819 keys.** No
template key is ignored on import; no key Telegram reads is left unthemed. The
`--glass_*` set was corrected to Telegram's real `glass_*` names rather than
deleted, and `glass_defaultIcon`/`glass_defaultText` were added to complete it.

**Closed — `noGradient*` is replaced by the real wallpaper gradient keys.** The
guess recorded in the previous pass was *wrong about the names*: Telegram spells
the stops `chat_wallpaper_gradient_to` (no `1`),
`key_chat_wallpaper_gradient_to2` and `key_chat_wallpaper_gradient_to3` — two of
the three carry the `key_` prefix. Added under those exact spellings, which also
gives `chat_wallpaper_gradient_rotation` something to rotate.

### Worth watching

**The wallpaper gradient may change how the background renders.** All four stops
resolve to the same role as `chat_wallpaper`, so a generated theme should look
identical to before — a gradient between four identical colors is a flat fill.
But their mere presence can move Telegram from a solid-color background to a
gradient drawable. This is the one addition with a plausible visual effect; it is
worth opening a generated theme before and after to confirm nothing shifted.

**The bubble-gradient toggle now covers four keys, not one.**
`AndroidThemeValuesProvider` used to drop a single `chat_outBubbleGradient` when
the user turns gradients off. `chat_outBubbleGradient2`, `chat_outBubbleGradient3`
and `chat_outBubbleGradientAnimated` would have survived that filter and kept
Telegram drawing a gradient, so `GRADIENT_KEY` became a `GRADIENT_KEYS` set. Any
future gradient key has to be added there too.

**Nine of the 97 additions inherit a role from an analog rather than a
first-hand reading.** Every new key was assigned by copying the role of its
closest existing sibling — `chat_inQuote` from `chat_inReplyLine`,
`chat_msgIvButtonPrimaryIn` from `chat_inContactBackground`, and so on — which
preserves each style's light/dark and in/out conventions automatically but is
not the same as having seen the element rendered. The Premium gradient family is
the least certain: Telegram ships it as a fixed five-hue rainbow, and mapping
every stop onto one accent turns it into a flat accent wash. That follows the
convention the templates already use for the group-call and story gradients, but
it is a style decision worth a second look.

---

## How this was compiled

- **Key set**: `templates/android/default/android_default_light.json`, all 819
  keys, after reconciling both ways against `ThemeColors.java`. The `†` set is
  exactly the 244 keys the template has and
  `test-theme/Default-light-monet-757.attheme` doesn't.
- **Reconciliation**: `ThemeColors.java`'s `colorKeysMap` is the table Telegram
  consults when reading an `.attheme`, so it defines both directions. A key
  absent from it changes nothing on import and was removed — that flagged 124,
  of which five were the misspelled `--glass_*` set and were renamed instead,
  leaving 119 deletions. A key present in it but missing from the templates left
  an element unthemed — 97 of those were added. All of it applied to the same
  eight Android template files (`theme-wizard/templates/android/**` and
  `app/src/main/assets/android_*.json`), which stay key-for-key identical.
- **Role assignment for the 97 additions**: each new key copies the role of its
  closest existing analog, per file, so the Default/Soza split and each style's
  light/dark and `in`/`out` conventions carry over without being restated. The
  analogs are named in the Relations column of each new row (`mirror`: … ).
- **Telegram default column**: `createDefaultColors()` from the same file, with
  `TELEGRAM_COLOR`, `TELEGRAM_COLOR_TEXT` and `DEFAULT_BLACK_TEXT` resolved to
  literals and the alpha byte kept wherever it isn't `FF`.
- **Semantics**: Telegram Android source (`DrKLO/Telegram`) — `ThemeColors.java`
  for the key table and stock colors, `ActionBar/Theme.java` for the wallpaper
  and service-background logic, plus targeted code searches for the keys whose
  names were ambiguous (`chat_attachPhotoBackground`, `chat_stickersHintPanel`,
  `chats_tabletSelectedOverlay`, `reactionStarSelector`, `wallpaperFileOffset`,
  `gift_ribbon`, `text_RedRegular`, and the `glass_*` set).
- **Visual grounding**: the 17 screenshots in
  [`screenshots/`](screenshots) — chat list, search, drawer, archive pull-down,
  chat with bubbles and link previews, attach sheet (File / Music / Poll /
  Gallery tabs), sticker settings, profile, group profile, settings list, and a
  channel with reaction chips.
- **Checks run**: the map's key set equals the templates' key set exactly, each
  key once; that set equals Telegram's live key table exactly, in both
  directions; all eight template files stay identical and parse as JSON with a
  non-empty role for every key; every role named is one the templates already
  used; the `†` set matches the computed set; every `pair:` relation resolves in
  both directions; no relation names a key that does not exist.

Descriptions of elements not visible in the screenshots and not individually
searched in source are inferred from Telegram's naming conventions and each key's
stock default color. They are reliable for the systematic families and least
certain for the one-off keys in §17.

One nuance the key-table test does not catch: a key can sit in Telegram's table
— so it survived the prune, or was added by it — and still have no remaining
drawing site in current master. `chat_inlineResultIcon` and
`sessions_devicesImage` both turned up only in `ThemeColors.java` during
spot-checking. They are described here from their names and defaults; they may
already be as inert as the keys that were removed.
