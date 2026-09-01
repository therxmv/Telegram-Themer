#!/usr/bin/env python3
"""
Runs contrast/distinctness checks over the resolved.json sidecars produced
by generate_themes.py, using the pair registries in pairs_android.json /
pairs_ios.json, and writes JSON + Markdown reports under theme-wizard/reports/.

What it checks, per (platform x style x scenario):
  - WCAG contrast ratio for every declared bg/fg pair (>=4.5:1 for "text",
    >=3.0:1 for "icon"/"large_text"), compositing alpha-prefixed colors
    over that scenario's tt_background.
  - "Distinctness" pairs: two keys that must NOT resolve to the same real
    (exported) color, e.g. selected vs. unselected tab text.

Then rolled up across all scenarios of a (platform, style, pair):
  - fails in 0 scenarios -> OK
  - fails in SOME but not all -> "isolated": likely fixable by reassigning
    that one template key to a different existing role.
  - fails in ALL scenarios -> "systemic": the role pair itself is bad
    regardless of accent - likely a palette/ramp issue, not a one-key fix.

Also separately flags, once per template (accent-independent, since it's
about which roles a template *uses*, not what they resolve to):
  - keys whose role isn't a real role name (typo'd/literal - silently
    falls back to black in the real app, see generate_themes.py).
  - Android keys using a tr_*/transparent_0 role, since AndroidThemeFileAdapter's
    export path drops alpha for every role (see resolve.android_export_hex) -
    every one of these will render as a solid opaque color, not a translucent
    overlay, in the real generated .attheme file.

Run: python3 theme-wizard/verification/check_contrast.py
"""
from __future__ import annotations

import json
from pathlib import Path

import resolve

ROOT = Path(__file__).resolve().parent.parent  # theme-wizard/
TEST_THEMES = ROOT / "test-themes"
TEMPLATES = ROOT / "templates"
REPORTS = ROOT / "reports"
VERIFICATION = ROOT / "verification"

STYLES = ["default", "soza"]
PLATFORMS = ["android", "ios"]

TEXT_THRESHOLD = 4.5
ICON_THRESHOLD = 3.0


# ---------- color helpers ----------

def parse_hex_any(s: str | None):
    """Returns ((r,g,b), alpha 0..1) or None if unmeasurable (empty/"clear")."""
    if not s:
        return None
    s = s.lstrip("#")
    if s == "" or s.lower() == "clear":
        return None
    if len(s) == 8:
        a = int(s[0:2], 16) / 255
        rgb = (int(s[2:4], 16), int(s[4:6], 16), int(s[6:8], 16))
        return rgb, a
    if len(s) == 6:
        rgb = (int(s[0:2], 16), int(s[2:4], 16), int(s[4:6], 16))
        return rgb, 1.0
    return None


def composite(rgb, alpha, backdrop_rgb):
    if alpha >= 1.0:
        return rgb
    return tuple(round(rgb[i] * alpha + backdrop_rgb[i] * (1 - alpha)) for i in range(3))


def relative_luminance(rgb):
    def chan(c):
        c = c / 255
        return c / 12.92 if c <= 0.03928 else ((c + 0.055) / 1.055) ** 2.4

    r, g, b = (chan(c) for c in rgb)
    return 0.2126 * r + 0.7152 * g + 0.0722 * b


def contrast_ratio(rgb_a, rgb_b):
    la, lb = relative_luminance(rgb_a), relative_luminance(rgb_b)
    lighter, darker = max(la, lb), min(la, lb)
    return (lighter + 0.05) / (darker + 0.05)


def rgb_to_hex_display(rgb):
    return "#%02x%02x%02x" % rgb


# ---------- pair resolution ----------

def get_ref(ref: str, keys: dict, tints: dict):
    """Returns (parsed_color_or_None, note_or_None) for a bg/fg/a/b reference."""
    if ref.startswith("role:"):
        role = ref[5:]
        hexval = tints.get(role)
        if hexval is None:
            return None, f"role '{role}' not in tint schema"
        return parse_hex_any(hexval), None

    entry = keys.get(ref)
    if entry is None:
        return None, f"key '{ref}' not found in template"

    note = "role unresolved (typo/literal in template) - real app falls back to black here" if entry.get("unresolved_role") else None
    hx = entry.get("intended_hex") or entry.get("exported")
    return parse_hex_any(hx), note


def exported_display(ref: str, keys: dict, tints: dict) -> str | None:
    """The literal string a user would actually see written to the real file, for distinctness comparisons."""
    if ref.startswith("role:"):
        role = ref[5:]
        return tints.get(role)
    entry = keys.get(ref)
    return entry.get("exported") if entry else None


# ---------- checking ----------

def check_scenario(platform: str, style: str, sidecar_path: Path, pairs: dict):
    data = json.loads(sidecar_path.read_text())
    scenario = data["scenario"]
    keys = data["keys"]
    tints = resolve.get_tinted_color_schema(scenario["accent"], scenario["is_dark"], scenario["is_amoled"])
    backdrop = parse_hex_any(tints["tt_background"])[0]

    pair_results = []
    for pair in pairs.get("text_pairs", []):
        bg, bg_note = get_ref(pair["bg"], keys, tints)
        fg, fg_note = get_ref(pair["fg"], keys, tints)
        if bg is None or fg is None:
            pair_results.append({
                "id": pair["id"], "scenario": scenario["name"], "skipped": True,
                "reason": bg_note or fg_note or "unmeasurable (transparent)",
            })
            continue
        bg_rgb = composite(bg[0], bg[1], backdrop)
        fg_rgb = composite(fg[0], fg[1], backdrop)
        ratio = contrast_ratio(bg_rgb, fg_rgb)
        threshold = TEXT_THRESHOLD if pair["kind"] == "text" else ICON_THRESHOLD
        pair_results.append({
            "id": pair["id"], "scenario": scenario["name"], "skipped": False,
            "kind": pair["kind"], "bg_key": pair["bg"], "fg_key": pair["fg"],
            "bg_hex": rgb_to_hex_display(bg_rgb), "fg_hex": rgb_to_hex_display(fg_rgb),
            "ratio": round(ratio, 2), "threshold": threshold, "pass": ratio >= threshold,
            "note": bg_note or fg_note,
        })

    distinct_results = []
    for d in pairs.get("distinct_pairs", []):
        a = exported_display(d["a"], keys, tints)
        b = exported_display(d["b"], keys, tints)
        if a is None or b is None:
            distinct_results.append({"id": d["id"], "scenario": scenario["name"], "skipped": True})
            continue
        same = a.strip("#").lower() == b.strip("#").lower()
        distinct_results.append({
            "id": d["id"], "scenario": scenario["name"], "skipped": False,
            "a_key": d["a"], "b_key": d["b"], "a_hex": a, "b_hex": b,
            "indistinguishable": same, "note": d.get("note", ""),
        })

    return pair_results, distinct_results


def find_unresolved_roles(platform: str, style: str) -> list[dict]:
    """Template-level (accent-independent) scan: keys whose role isn't a real role name."""
    known_roles = set(resolve.get_tinted_color_schema("#007AFF", False, False).keys())
    found = []
    for mode in ("light", "dark"):
        path = TEMPLATES / platform / style / f"{style}_{mode}_template.json"
        template = json.loads(path.read_text())
        literal_keys = resolve.IOS_LITERAL_KEYS if platform == "ios" else set()
        for key, role in template.items():
            if key in literal_keys:
                continue
            if role not in known_roles:
                found.append({"mode": mode, "key": key, "role": role})
    return found


def find_alpha_dropped_on_android_export(style: str) -> list[dict]:
    """Template-level scan: Android keys whose role is translucent (tr_*/transparent_0) -
    AndroidThemeFileAdapter's export drops alpha for every role, so these ship opaque."""
    found = []
    for mode in ("light", "dark"):
        path = TEMPLATES / "android" / style / f"{style}_{mode}_template.json"
        template = json.loads(path.read_text())
        for key, role in template.items():
            if role.startswith("tr_") or role == "transparent_0":
                found.append({"mode": mode, "key": key, "role": role})
    return found


def main():
    REPORTS.mkdir(parents=True, exist_ok=True)
    all_platform_reports = {}
    all_unresolved = {}
    all_alpha_dropped = {}

    for platform in PLATFORMS:
        pairs_path = VERIFICATION / f"pairs_{platform}.json"
        pairs = json.loads(pairs_path.read_text())

        for style in STYLES:
            style_dir = TEST_THEMES / platform / style
            sidecars = sorted(style_dir.glob("*.resolved.json"))

            all_pair_results = []
            all_distinct_results = []
            for sc in sidecars:
                pr, dr = check_scenario(platform, style, sc, pairs)
                all_pair_results.extend(pr)
                all_distinct_results.extend(dr)

            # Rollup per pair id across scenarios.
            rollup = {}
            for r in all_pair_results:
                if r["skipped"]:
                    continue
                b = rollup.setdefault(r["id"], {"fails": [], "total": 0, "kind": r["kind"], "bg_key": r["bg_key"], "fg_key": r["fg_key"]})
                b["total"] += 1
                if not r["pass"]:
                    b["fails"].append({"scenario": r["scenario"], "ratio": r["ratio"], "threshold": r["threshold"], "bg_hex": r["bg_hex"], "fg_hex": r["fg_hex"]})

            classified = []
            for pair_id, b in rollup.items():
                n_fail = len(b["fails"])
                if n_fail == 0:
                    verdict = "ok"
                elif n_fail == b["total"]:
                    verdict = "systemic"
                else:
                    verdict = "isolated"
                classified.append({"id": pair_id, "verdict": verdict, "fail_count": n_fail, "total": b["total"], **b})

            distinct_violations = {}
            for r in all_distinct_results:
                if r["skipped"] or not r["indistinguishable"]:
                    continue
                distinct_violations.setdefault(r["id"], {"note": r["note"], "a_key": r["a_key"], "b_key": r["b_key"], "scenarios": []})
                distinct_violations[r["id"]]["scenarios"].append(r["scenario"])

            report = {
                "platform": platform, "style": style,
                "scenarios_checked": [s["scenario"]["name"] for s in (json.loads(p.read_text()) for p in sidecars)],
                "pair_rollup": classified,
                "distinct_violations": distinct_violations,
            }
            all_platform_reports[f"{platform}_{style}"] = report
            (REPORTS / f"{platform}_{style}.json").write_text(json.dumps(report, indent=2))

            all_unresolved[f"{platform}_{style}"] = find_unresolved_roles(platform, style)
            if platform == "android":
                all_alpha_dropped[style] = find_alpha_dropped_on_android_export(style)

    write_summary_md(all_platform_reports, all_unresolved, all_alpha_dropped)
    print(f"Wrote reports to {REPORTS.relative_to(ROOT.parent)}/")


def write_summary_md(reports: dict, unresolved: dict, alpha_dropped: dict):
    lines = ["# Theme verification summary", ""]
    lines.append(
        "Generated by `theme-wizard/verification/generate_themes.py` + `check_contrast.py` "
        "against `theme-wizard/templates/`, across 9 representative accent/mode scenarios "
        "per platform/style (see `generate_themes.py`'s `SCENARIOS`). Raw per-(platform,style) "
        "data is in the sibling `.json` files in this folder."
    )
    lines.append("")

    lines.append("## 1. Template bugs (accent-independent - broken in every generated theme)")
    lines.append("")
    any_unresolved = False
    for name, items in unresolved.items():
        if not items:
            continue
        any_unresolved = True
        lines.append(f"### {name}")
        lines.append("Key's role isn't a real role name (typo, or a literal value the resolver doesn't support). "
                      "The real app silently falls back to solid black for these, regardless of accent:")
        lines.append("")
        for it in items:
            lines.append(f"- `{it['key']}` ({it['mode']}) -> role `\"{it['role']}\"` (not a valid role)")
        lines.append("")
    if not any_unresolved:
        lines.append("None found.")
        lines.append("")

    lines.append("## 2. Android: alpha lost on export (translucent roles ship opaque)")
    lines.append("")
    lines.append(
        "`AndroidThemeFileAdapter`'s writer (`Int.colorToHex()`, see "
        "`app/src/main/java/com/therxmv/telegramthemer/data/extensions/ColorExtensions.kt`) "
        "strips alpha from every value with no `tr_*` special-case (the iOS adapter does have one). "
        "Every key below is templated to a translucent role but will render as a solid, fully "
        "opaque color in the real generated `.attheme` file - not the faded overlay the template intends."
    )
    lines.append("")
    for style, items in alpha_dropped.items():
        lines.append(f"### {style} ({len(items)} affected keys)")
        by_role = {}
        for it in items:
            by_role.setdefault(it["role"], set()).add(it["key"])
        for role, keys in sorted(by_role.items()):
            lines.append(f"- `{role}`: {len(keys)} keys, e.g. `{sorted(keys)[0]}`")
        lines.append("")

    lines.append("## 3. Systemic contrast failures (fail across ALL representative scenarios)")
    lines.append("Likely a palette/ramp issue (or the role pairing itself is wrong) - not fixable by nudging one template key, since every accent hits it.")
    lines.append("")
    any_systemic = False
    for name, report in reports.items():
        systemic = [c for c in report["pair_rollup"] if c["verdict"] == "systemic"]
        if not systemic:
            continue
        any_systemic = True
        lines.append(f"### {name}")
        for c in sorted(systemic, key=lambda c: min(f["ratio"] for f in c["fails"])):
            worst = min(c["fails"], key=lambda f: f["ratio"])
            lines.append(
                f"- **{c['id']}** (`{c['bg_key']}` bg / `{c['fg_key']}` fg, needs >={c['fails'][0]['threshold']}:1): "
                f"fails {c['fail_count']}/{c['total']} scenarios, worst {worst['ratio']}:1 "
                f"({worst['bg_hex']} vs {worst['fg_hex']}, `{worst['scenario']}`)"
            )
        lines.append("")
    if not any_systemic:
        lines.append("None found.")
        lines.append("")

    lines.append("## 4. Isolated contrast failures (fail for SOME accents only)")
    lines.append("Likely fixable at the template level - reassign that one key to a different existing role (a step lighter/darker on the same ramp).")
    lines.append("")
    any_isolated = False
    for name, report in reports.items():
        isolated = [c for c in report["pair_rollup"] if c["verdict"] == "isolated"]
        if not isolated:
            continue
        any_isolated = True
        lines.append(f"### {name}")
        for c in sorted(isolated, key=lambda c: -c["fail_count"]):
            worst = min(c["fails"], key=lambda f: f["ratio"])
            failing_scenarios = ", ".join(f["scenario"] for f in c["fails"])
            lines.append(
                f"- **{c['id']}** (`{c['bg_key']}` bg / `{c['fg_key']}` fg, needs >={c['fails'][0]['threshold']}:1): "
                f"fails {c['fail_count']}/{c['total']} ({failing_scenarios}), worst {worst['ratio']}:1 "
                f"({worst['bg_hex']} vs {worst['fg_hex']}, `{worst['scenario']}`)"
            )
        lines.append("")
    if not any_isolated:
        lines.append("None found.")
        lines.append("")

    lines.append("## 5. Indistinguishable states (two roles that must differ but don't)")
    lines.append("")
    any_distinct = False
    for name, report in reports.items():
        viol = report["distinct_violations"]
        if not viol:
            continue
        any_distinct = True
        lines.append(f"### {name}")
        for pair_id, v in viol.items():
            lines.append(f"- **{pair_id}**: `{v['a_key']}` == `{v['b_key']}` in {len(v['scenarios'])} scenario(s) ({', '.join(v['scenarios'])}). {v['note']}")
        lines.append("")
    if not any_distinct:
        lines.append("None found.")
        lines.append("")

    (REPORTS / "SUMMARY.md").write_text("\n".join(lines))


if __name__ == "__main__":
    main()
