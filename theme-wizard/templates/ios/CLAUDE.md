# iOS templates — `.tgios-theme`

This folder holds the iOS counterpart to [`../android`](../android): two
styles (`default` and `soza`), each with a light and dark template (417
keys each), that resolve into a Telegram-for-iOS `.tgios-theme` file the
same way the Android templates resolve into `.attheme` files.

Read [`../../theme-generation-flow.md`](../../theme-generation-flow.md) and
[`../../color-roles.md`](../../color-roles.md) first. Everything below
assumes you already know what a "role" is and how a template resolves
against an accent-derived palette.

This whole `theme-wizard` tree is a standalone proof-of-concept — see the
top-level [`../../CLAUDE.md`](../../CLAUDE.md). Nothing here is a fixed
spec; it's one reasonable way to make the Android role system reach a
structurally different platform, open to being reworked.

## Why iOS needed its own conventions, not just new templates

`.tgios-theme` isn't a flat `key=value` list like `.attheme` — it's a
nested, indentation-scoped document (`root.tabBar.background`,
`chat.message.incoming.bubble.withWp.bg`, ...), it mixes non-color
settings in with colors (`dark: true`, `root.keyboard: light`,
`passcode.button: clear`), and its colors come in three shapes instead of
one (`RRGGBB`, `AARRGGBB`, and the literal keyword `clear`). Reusing
Android's "flat JSON, key → role name" template shape outright wouldn't
work, so it's adapted here rather than copied:

1. **Keys are flattened to dot-paths.** Each JSON template is still one
   flat object, exactly like the Android templates — but the key is the
   full nested path joined with `.` (`root.tabBar.background`,
   `chat.message.outgoing.bubble.withWp.gradientBg`). Producing the
   finished `.tgios-theme` file means splitting each dot-path back into
   its nested, indented form. This keeps the template itself simple (one
   flat JSON object, diffable line-by-line like Android's) while still
   round-tripping losslessly to the real nested format.
2. **Two kinds of values, not one.** Most values are still role names from
   [`color-roles.md`](../../color-roles.md), resolved exactly like
   Android. But exactly 8 keys per template aren't colors at all — they
   hold **literal** JSON values (booleans or fixed strings) that a
   template resolver must pass through unchanged instead of looking up in
   the palette:

   | Key | Default | Soza |
   |---|---|---|
   | `dark` | `false` (light) / `true` (dark) | same |
   | `basedOn` | `"day"` / `"night"` | same |
   | `root.keyboard` | `"light"` / `"dark"` | same |
   | `intro.statusBar` | `"black"` / `"white"` | same |
   | `root.statusBar` | `"black"` / `"white"` | same |
   | `actionSheet.bgType` | `"light"` / `"dark"` | same |
   | `notification.expanded.bgType` | `"light"` / `"dark"` | same |
   | `chat.animateMessageColors` | `false` (both modes) | `true` (both modes) |

   Every other key's value is a role name; there are no literal hex-color
   exceptions in these templates (unlike Android's `chat_BlurAlpha`) —
   `tr_background_9` below covers the one case that would otherwise have
   needed one.
3. **One new role**: `tr_background_9`, documented in
   [`color-roles.md`](../../color-roles.md#transparency-roles). iOS's bars
   and panels (nav bar, tab bar, action sheet, context menu, dialogs) are
   almost all translucent "materials" sitting at roughly 90% opacity over
   `tt_background` — a shape none of Android's four `tr_*` roles cover.
   Rather than inventing a one-off literal for every such key, one shared
   role covers all of them.
4. **A resolver-level formatting rule, not a template-level one**: which
   of `RRGGBB` / `AARRGGBB` / `clear` a resolved role gets written as
   depends on the *role*, not the key — see "Platform-specific resolution
   notes" in [`color-roles.md`](../../color-roles.md). The templates here
   never encode format directly; they just name a role.

## The "must stay light regardless of mode" problem

A recurring case throughout iOS's chat bubble, badge, and button colors:
content drawn on top of a *fixed-brightness* fill (white text on a solid
accent-colored button; white digits on a red notification badge) needs to
stay light in **both** light and dark mode, because the fill itself
doesn't get meaningfully darker in dark mode the way `tt_background` does.
Android's equivalent case (content on an accent/status fill) reuses
`tt_background` for this and accepts that it inverts oddly in dark mode,
since most of those Android keys are small icons where it barely reads.
iOS has more of these, and several are literal filled-pill text labels
where an inversion would actually look broken (black digits on a red
badge in dark mode).

These templates use `gray_9` — the fixed lightest step of the gray ramp,
which doesn't flip between modes — for that case instead. Look for `gray_9`
on keys like `*badgeText`, `*Fg` / `*Fg.withWp` on filled action buttons,
and outgoing-bubble text/controls; that's this convention, not a
coincidence or a copy-paste of the neutral-text tier.

**`gray_9` only belongs on a genuinely saturated accent/status
background — never on a plain gray tier.** `gray_9` and `gray_8` are one
step apart on the ramp — next to unreadable if paired together (e.g. a
`gray_9` text key sitting on a `gray_8` background). If the background a
`gray_9` key sits on is a gray tier rather than an `accent_*`/status role,
it needs `tt_background` instead (which correctly resolves to white in
light mode and black in dark, giving real separation from any gray step) —
not `gray_9`.

## Style folders

| Path | What it is |
|---|---|
| [`default/`](default) | The conservative style — neutral chrome, an accent-tinted but not overpowering outgoing bubble, incoming bubbles read as a plain neutral card. |
| [`soza/`](soza) | The louder style — accent reaches further into chrome that Default leaves neutral, the outgoing bubble sits a step darker/richer, incoming bubbles blend into the background instead of staying a visible neutral card. |

Both cover the same 417 leaf keys — the full set of themeable `.tgios-theme`
elements currently modeled, not counting per-instance metadata like a
theme's display `name`, which isn't a themeable element.
