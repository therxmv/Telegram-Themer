#!/usr/bin/env python3
"""
Applies automatic template fixes for the "isolated" and "systemic" contrast
failures found by check_contrast.py, by reassigning the FAILING pair's fg
key (text/icon) to a different, better-contrasting role - never touching
the bg key, on the assumption that text/icons should adapt to a background,
not the other way around.

Scope, deliberately: only touches theme-wizard/templates/*.json (the POC
template files this whole verification pipeline reads/writes). Does NOT
touch app/src/main/assets or any .kt source - those are a separate,
deliberate migration if/when these templates get promoted, per
theme-wizard/CLAUDE.md ("Nothing here needs to stay in sync with
app/src/main/assets"). Does NOT touch chat_BlurAlpha/chat_BlurAlphaSlow
(literal-hex keys) or the Android alpha-drop-on-export gap - both were
explicitly left alone per user decision.

For each failing pair, per template mode file (light/dark are independent
JSON files - a key's role in one doesn't affect the other):
  1. Build a candidate pool of roles to try for the fg key: the full gray_N
     and (if the original role was itself accent_N) accent_N ramps, plus
     tt_background/tt_onBackground. Neutral grays are always offered
     because "text/icon that reads clearly regardless of hue" is usually
     the right fix even when the original role was an accent shade.
  2. Score each candidate by (pass_count, min_ratio) across that mode's
     scenarios, using the pair's *current* bg (never modified).
  3. Apply the best-scoring candidate only if it strictly beats the
     current role's score - and prefer the smallest luminosity jump among
     ties, to keep the visual change minimal.
  4. If nothing beats the current role (common for "badge text on an
     accent whose lightness swings from very pale to very dark" - no
     single static role can satisfy both extremes), leave it and log it
     as a structural note instead of forcing a change.

Logs every decision to theme-wizard/reports/FIXES_APPLIED.md. Run
generate_themes.py + check_contrast.py again afterward to see the result.

Run: python3 theme-wizard/verification/apply_fixes.py
"""
from __future__ import annotations

import json
import re
from pathlib import Path

import resolve
from generate_themes import SCENARIOS, TEMPLATES
from check_contrast import REPORTS, VERIFICATION, parse_hex_any, composite, contrast_ratio, TEXT_THRESHOLD, ICON_THRESHOLD

ROLE_RE = re.compile(r"^(gray|accent)_(\d)$")


def family_and_level(role: str):
    m = ROLE_RE.match(role)
    if m:
        return m.group(1), int(m.group(2))
    return None, None


def distance(original: str, candidate: str) -> int:
    if candidate == original:
        return 0
    of, ol = family_and_level(original)
    cf, cl = family_and_level(candidate)
    if of is not None and of == cf:
        return abs(ol - cl)
    return 50  # cross-family jump penalty


def build_candidate_pool(original_role: str) -> list[str]:
    pool = [f"gray_{i}" for i in range(1, 10)] + ["tt_onBackground", "tt_background"]
    if original_role.startswith("accent_"):
        pool += [f"accent_{i}" for i in range(1, 10)]
    if original_role not in pool:
        pool.append(original_role)
    return pool


def score_candidate(fg_role: str, bg_role: str, scenarios: list[dict], threshold: float):
    passes = 0
    min_ratio = None
    for sc in scenarios:
        tints = resolve.get_tinted_color_schema(sc["accent"], sc["is_dark"], sc["is_amoled"])
        fg_hex, bg_hex = tints.get(fg_role), tints.get(bg_role)
        if fg_hex is None or bg_hex is None:
            continue
        fg_c, bg_c = parse_hex_any(fg_hex), parse_hex_any(bg_hex)
        if fg_c is None or bg_c is None:
            continue
        backdrop = parse_hex_any(tints["tt_background"])[0]
        fg_rgb = composite(fg_c[0], fg_c[1], backdrop)
        bg_rgb = composite(bg_c[0], bg_c[1], backdrop)
        ratio = contrast_ratio(fg_rgb, bg_rgb)
        if ratio >= threshold:
            passes += 1
        min_ratio = ratio if min_ratio is None else min(min_ratio, ratio)
    return passes, (min_ratio if min_ratio is not None else 0.0)


def load_template(platform: str, style: str, is_dark: bool) -> dict:
    mode = "dark" if is_dark else "light"
    path = TEMPLATES / platform / style / f"{style}_{mode}_template.json"
    return json.loads(path.read_text()), path


def find_bg_kind_for_fg(pairs: dict, fg_key: str):
    for p in pairs.get("text_pairs", []):
        if p["fg"] == fg_key:
            return p["bg"], p["kind"]
    return None, None


def repair_distinctness(pairs: dict, mode: str, template: dict, mode_scenarios: list[dict], section_lines: list[str]):
    """After contrast-driven fixes, a distinct_pairs constraint may now collide
    (both sides independently optimized toward the same role). Repair by
    re-picking the SECOND key's role, excluding the first key's role from the
    candidate pool, scored against whatever bg/kind that key's own text_pair
    (if any) declares."""
    for d in pairs.get("distinct_pairs", []):
        a, b = d["a"], d["b"]
        if a not in template or b not in template:
            continue
        if template[a] != template[b]:
            continue

        bg_key, kind = find_bg_kind_for_fg(pairs, b)
        if bg_key is None:
            bg_key, kind = find_bg_kind_for_fg(pairs, a)
        if bg_key is None:
            section_lines.append(f"- **{d['id']}** ({mode}): `{a}` and `{b}` collided on `{template[a]}` after fixes, and neither has a known bg to repair against - left colliding, needs a manual look.")
            continue
        kind = kind or "text"
        threshold = TEXT_THRESHOLD if kind == "text" else ICON_THRESHOLD
        bg_role = bg_key[5:] if bg_key.startswith("role:") else template.get(bg_key)
        if bg_role is None:
            continue

        original_b_role = template[b]
        excluded = template[a]
        best_role, best_score, best_dist = None, (-1, -1.0), 999
        for cand in build_candidate_pool(original_b_role):
            if cand == excluded:
                continue
            s = score_candidate(cand, bg_role, mode_scenarios, threshold)
            dist = distance(original_b_role, cand)
            if (s[0], s[1], -dist) > (best_score[0], best_score[1], -best_dist):
                best_role, best_score, best_dist = cand, s, dist

        if best_role and best_role != original_b_role:
            template[b] = best_role
            section_lines.append(
                f"- **{d['id']}** ({mode}): `{a}`/`{b}` both became `{original_b_role}` after the contrast pass - "
                f"reassigned `{b}` -> `{best_role}` to restore distinctness ({best_score[0]}/{len(mode_scenarios)} passing, {best_score[1]:.2f} min ratio)"
            )
        else:
            section_lines.append(f"- **{d['id']}** ({mode}): `{a}`/`{b}` collided on `{original_b_role}` and no alternative for `{b}` was found - left colliding, needs a manual look.")


def score_joint(fg_role: str, constraints: list[tuple[str, float]], scenarios: list[dict]):
    """Scores fg_role against MULTIPLE (bg_role, threshold) constraints at once -
    for when more than one pair shares the same fg key (e.g. android's
    chats_unreadCounterText is the fg for both the accent-colored and the
    muted-gray unread badge) - so a candidate is only accepted if it works
    for every constraint jointly, not whichever pair happened to be scored last."""
    total_pass, min_ratio = 0, None
    for bg_role, threshold in constraints:
        p, r = score_candidate(fg_role, bg_role, scenarios, threshold)
        total_pass += p
        min_ratio = r if min_ratio is None else min(min_ratio, r)
    return total_pass, (min_ratio if min_ratio is not None else 0.0)


def main():
    log_lines = ["# Fixes applied", "", "Generated by `apply_fixes.py`. Re-run `generate_themes.py` + `check_contrast.py` after this to verify.", ""]

    for platform in ("android", "ios"):
        pairs = json.loads((VERIFICATION / f"pairs_{platform}.json").read_text())
        for style in ("default", "soza"):
            report_path = REPORTS / f"{platform}_{style}.json"
            if not report_path.exists():
                continue
            report = json.loads(report_path.read_text())
            candidates_by_mode = {}  # mode -> (template dict, path) loaded lazily, edited in place

            section_lines = []

            # Group ALL pairs (any verdict - a currently-"ok" pair sharing an fg key
            # with a failing one is still a constraint that must not regress) by
            # (mode, fg_key), since more than one pair can point at the same fg key.
            groups = {}  # (mode, fg_key) -> list of pair_rollup entries
            for c in report["pair_rollup"]:
                fg_key = c["fg_key"]
                if fg_key.startswith("role:"):
                    if c["verdict"] != "ok":
                        section_lines.append(f"- **{c['id']}**: fg is a bare role reference (`{fg_key}`), not a template key - can't reassign. Needs a design-level fix.")
                    continue
                for mode, is_dark in (("light", False), ("dark", True)):
                    mode_scenario_names = {s["name"] for s in SCENARIOS if s["is_dark"] == is_dark}
                    # A pair belongs to this mode if any of ITS checked scenarios are - reconstruct
                    # from total/fail_count is lossy, so just include it in both and let "bg key not
                    # found in this template file" filter it out below for the mode it doesn't apply to.
                    groups.setdefault((mode, fg_key), []).append(c)

            for (mode, fg_key), group in groups.items():
                is_dark = mode == "dark"
                mode_scenarios = [s for s in SCENARIOS if s["is_dark"] == is_dark]

                if mode not in candidates_by_mode:
                    candidates_by_mode[mode] = load_template(platform, style, is_dark)
                template, path = candidates_by_mode[mode]

                if fg_key not in template:
                    continue  # this fg key isn't part of this mode's template at all

                constraints = []  # (bg_role, threshold)
                pair_ids = []
                for c in group:
                    bg_role = c["bg_key"][5:] if c["bg_key"].startswith("role:") else template.get(c["bg_key"])
                    if bg_role is None:
                        section_lines.append(f"- **{c['id']}** ({mode}): bg key `{c['bg_key']}` not found in this template file - skipped.")
                        continue
                    threshold = TEXT_THRESHOLD if c["kind"] == "text" else ICON_THRESHOLD
                    constraints.append((bg_role, threshold))
                    pair_ids.append(c["id"])
                if not constraints:
                    continue

                original_role = template[fg_key]
                current = score_joint(original_role, constraints, mode_scenarios)
                max_possible = len(constraints) * len(mode_scenarios)

                best_role, best_score, best_dist = original_role, current, 0
                for cand in build_candidate_pool(original_role):
                    s = score_joint(cand, constraints, mode_scenarios)
                    d = distance(original_role, cand)
                    if (s[0], s[1], -d) > (best_score[0], best_score[1], -best_dist):
                        best_role, best_score, best_dist = cand, s, d

                pair_label = "/".join(pair_ids)
                if best_role != original_role and (best_score[0] > current[0] or (best_score[0] == current[0] and best_score[1] > current[1])):
                    template[fg_key] = best_role
                    section_lines.append(
                        f"- **{pair_label}** ({mode}): `{fg_key}` `{original_role}` -> `{best_role}` "
                        f"({current[0]}/{max_possible} passing, {current[1]:.2f} min ratio -> "
                        f"{best_score[0]}/{max_possible} passing, {best_score[1]:.2f} min ratio)"
                    )
                else:
                    section_lines.append(
                        f"- **{pair_label}** ({mode}): no role beats the current choice "
                        f"(`{fg_key}`=`{original_role}`, best found {best_score[0]}/{max_possible} passing at {best_score[1]:.2f}:1) - "
                        f"likely needs a non-static-role fix (e.g. luminance-aware text color), left unchanged."
                    )

            for mode, (template, path) in candidates_by_mode.items():
                is_dark = mode == "dark"
                mode_scenarios = [s for s in SCENARIOS if s["is_dark"] == is_dark]
                repair_distinctness(pairs, mode, template, mode_scenarios, section_lines)

            for mode, (template, path) in candidates_by_mode.items():
                path.write_text(json.dumps(template, indent=2))

            if section_lines:
                log_lines.append(f"## {platform}/{style}")
                log_lines.extend(section_lines)
                log_lines.append("")

    (REPORTS / "FIXES_APPLIED.md").write_text("\n".join(log_lines))
    print("Wrote theme-wizard/reports/FIXES_APPLIED.md")


if __name__ == "__main__":
    main()
