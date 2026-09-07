---
name: update-claude-md
description: Audit and update CLAUDE.md files (root or any directory-scoped one) against the actual current state of the code they document. Use when the user says "update claude.md", "check if CLAUDE.md is stale", "/update-claude-md", or after `.githooks/pre-push` flags a directory whose CLAUDE.md may now be out of date.
---

# Update CLAUDE.md

Keeps `CLAUDE.md` files honest — every claim in them (file lists, conventions, counts, examples, paths) should still be true of the code today. This is a manual, on-demand audit; it makes no API calls itself beyond your own normal tool use in this session, and it's the intended follow-up to `.githooks/pre-push`'s reminders (that hook only flags a directory, it never edits anything).

## Scope

- No argument → audit **every** `CLAUDE.md` in the repo: currently just the root [`CLAUDE.md`](../../../CLAUDE.md) — find the full current list with `find . -name CLAUDE.md -not -path "*/build/*" -not -path "*/.git/*"`, don't hardcode this list (a directory-scoped one, e.g. `app/CLAUDE.md` or `preview/CLAUDE.md`, may exist by the time you run this).
- An argument (a path, or a directory name like `app` or `preview`) → audit only the `CLAUDE.md` that documents that directory.

## Steps

For each `CLAUDE.md` in scope:

1. **Read it in full.**
2. **Gather what actually changed.** Prefer, in order of how much signal they give:
   - `git log --oneline -- <dir>` since the `CLAUDE.md` was last modified (`git log -1 --format=%H -- <path/to/CLAUDE.md>`), then `git diff <that-sha> -- <dir>` for the full delta.
   - The directory's real current file listing (`Glob`/`find`), compared against any file/component/table listing the doc claims (module structure, layer breakdown, theme template asset names, etc.).
   - `mcp__codebase-memory-mcp__get_architecture` or `search_graph` scoped to the directory, if the git history is large or hard to skim.
3. **Judge staleness.** A doc is stale when it's wrong about something a reader would rely on: a file/class/package that no longer exists or was renamed, a described layer/module boundary the code no longer follows, a broken relative path/link, a theme-template asset name (`android_default_*.json`, `ios_soza_*.json`, etc.) that changed, an example that no longer compiles or matches current API shape. It is **not** stale over: prose style, ordering, or something still true just phrased differently than the code's latest naming.
4. **Fix it minimally.** Edit only what's actually wrong — match the file's existing tone, section structure, and level of detail (this doc is terse and reference-style, not a tutorial). Don't restructure or expand scope beyond correcting the stale claim(s). If you're adding a genuinely new convention/gotcha worth recording (not just fixing an existing claim), keep it to one or two lines in the style already used.
5. **If nothing is stale, make no edit** — don't touch the file just to have touched it.

## Report back

One line per file: unchanged, or a short description of what was fixed. If you're auditing the whole repo and find a directory with real structure/conventions worth documenting but no `CLAUDE.md` yet (e.g. `:app`'s `di` wiring or `:preview`'s color-key mapping growing complex), mention it as a suggestion — don't create one unprompted.
