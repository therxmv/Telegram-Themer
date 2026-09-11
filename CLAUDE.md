# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## About

Telegram Themer — Android app for creating custom Telegram themes (Android `.attheme` and iOS `.tgios-theme`
exports), with live preview, light/dark/AMOLED modes, gradients, and Monet (Android 12+) support.

## Module structure

Two Gradle modules, declared in `settings.gradle`:

- **`:app`** — the application. Namespace `com.therxmv.telegramthemer`. Depends on `:preview`.
- **`:preview`** — standalone library rendering a live mock of the Telegram chat UI (chat list + message
  bubbles) driven by a color map, used to preview theme edits in real time. Namespace `com.therxmv.preview`.

Shared dependency versions/config live in `global.gradle` (included from the root `build.gradle`); per-module
`build.gradle` files reference them via the `appDependencies`/`appConfig` maps. `:preview`'s module-level
Android config is factored into `gradle/snippets/module-config.gradle`.

## Architecture

`:app` follows a layered MVP-ish structure: `data` → `domain` → `ui`, wired together with Dagger 2 (manual
component/module setup under `app/.../di` and `app/.../app`, not Hilt despite the Hilt dependency being
present). `Injector` builds the root `ThemerAppComponent` from `ThemerApplication` and exposes it as
`UiDependencies`.

- **`domain`** — platform-agnostic contracts and models:
  - `model/ThemeState` — the single source of truth for an in-progress theme (style, platform, accent color,
    dark/AMOLED/Monet/gradient flags, and a map of per-key `overwrittenColors`). Presenters mutate a local
    copy and persist it via `SaveThemeUseCase`.
  - `model/Platform` (`ANDROID`/`IOS`) and `ThemeState.Styles` (`DEFAULT`/`SOZA`) select which color template
    and file adapter to use.
  - `adapter/ThemeFileAdapter` — interface for turning a `ThemeState` into an exportable theme `File`;
    implemented per platform in `data/adapter` (`AndroidThemeFileAdapter`, `IosThemeFileAdapter`) and
    dispatched by `PlatformThemeFileAdapter`.
  - `usecase/*` — `GetCachedThemeUseCase`/`SaveThemeUseCase` (persistence round-trip via
    `SharedPrefsSource`/`SharedPrefsDataSource`), `GetAtthemeFileUseCase` (build the export file),
    `GetPreviewColorsModelUseCase` (resolve the live-preview color map for the `:preview` module).

- **`data`** — implementations:
  - `values/*ThemeValuesProvider` — load the base color templates (see Theme templates below) per
    platform/style, dispatched through `PlatformThemeValuesProvider`; `ThemeColorsProvider` layers the user's
    accent color, dark/AMOLED/gradient/Monet flags, and `overwrittenColors` on top of the base template to
    produce final key→color values.
  - `adapter/ThemeToPreviewAdapter` — maps final theme colors to the `:preview` module's expected color keys
    (`AtthemePreviewKeys`).
  - `extensions/ColorExtensions`, `TintsExtensions` — color math (tinting/shading) shared by the providers.

- **`ui`** — one Activity (`ThemeEditorActivity`) hosting Navigation-Component fragments, each with its own
  Contract (View/Presenter interfaces) + Presenter, following the existing `BaseBinding*` base classes:
  - `editor/simple` and `editor/advanced` — the two editing modes (simple accent/style picker vs. per-key
    advanced overrides).
  - `editor/picker` — color picker bottom sheet (wraps the `colorpicker` library dependency).
  - `editor/options` — export/share/reset bottom sheet.
  - `ThemeEditorEventStore`/`ThemeEditorEvent` — a shared event bus (`StateFlow`-backed) presenters use to
    talk to each other (e.g. advanced fragment opening the color picker, options sheet triggering export)
    without direct references; `ThemeEditorPresenter` is the central collector and owns the canonical
    `themeState`, broadcasting changes to registered `ThemeStateListener`s.

## Code style

Don't add explanatory comments by default. Write one only where the code itself can't carry the reasoning —
a workaround, a non-obvious constraint forcing a particular approach, or genuinely complex logic. A comment
restating what the following line already says plainly is noise; delete it rather than write it.

## Theme templates

Base color templates for the four (platform × style) combinations ship as JSON assets in
`app/src/main/assets/`: `android_default_*.json`, `android_soza_*.json`, `ios_default_*.json`,
`ios_soza_*.json` (each with `_light`/`_dark` variants). These are the ground truth for what each theme key
renders and how keys relate to each other — do not hand-edit them without cross-checking both light and dark
variants for consistency. Two project skills (`android-theme-key`, `ios-theme-key`) exist specifically for
looking up what an individual template key draws and what it's paired with; use them instead of guessing key
semantics.

Android `.attheme` keys are flat (`chat_outBubble`); iOS `.tgios-theme` keys are dot-paths mirroring the
nested export format (`chat.message.incoming.bubble.withWp.bg`).

## Available tools

- **`codebase-memory` MCP** — knowledge-graph tools (`search_graph`, `trace_path`, `get_code_snippet`,
  `query_graph`, `get_architecture`, `search_code`) for structural questions about this codebase (call
  chains, dependencies, dead code). Prefer these over grepping for anything beyond a simple text/config
  search; run `index_repository` first if the project isn't indexed yet.
- **GitHub CLI (`gh`)** — use for PR/issue operations (creating/viewing PRs, checking CI status, reading
  issues) instead of hitting the GitHub API directly.
- **Context7 MCP** — resolve up-to-date docs/API references for third-party libraries (e.g. Dagger,
  Navigation Component, kotlinx.serialization, the `colorpicker` dependency) when behavior isn't obvious
  from the code already in this repo.
- **Project skills** (`.claude/skills/`):
  - `android-theme-key` — look up what an `.attheme` key (e.g. `chat_outBubble`) draws and what it's
    paired/related to.
  - `ios-theme-key` — look up what a `.tgios-theme` dot-path key (e.g. `chat.message.incoming.bubble.withWp.bg`)
    draws and what it's paired/related to.
