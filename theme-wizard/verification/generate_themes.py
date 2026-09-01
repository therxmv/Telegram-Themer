#!/usr/bin/env python3
"""
Generates real (byte-faithful) test theme files from theme-wizard/templates,
via the resolution logic ported in resolve.py - same math the app's
ThemeColorsProvider/AndroidThemeFileAdapter/IosThemeFileAdapter run, just
without needing an Android Context (assets.open()/filesDir are replaced
with plain file I/O; Monet is out of scope, see resolve.py's docstring).

For each (platform x style x scenario) this writes, under theme-wizard/test-themes/:
  - the real output file (.attheme for Android, .tgios-theme for iOS) -
    exactly what a user would get out of the app for that input.
  - a "<scenario>.resolved.json" sidecar: key -> {role, intended_hex,
    exported} for every template entry, consumed by check_contrast.py.
    "intended_hex" keeps full alpha for tr_*/transparent_0 roles (the
    color the role actually represents); "exported" is what that
    platform's real writer puts in the output file for that key -
    Android's writer silently drops alpha, see resolve.android_export_hex.

Run: python3 theme-wizard/verification/generate_themes.py
"""
from __future__ import annotations

import json
from pathlib import Path

import resolve

ROOT = Path(__file__).resolve().parent.parent  # theme-wizard/
TEMPLATES = ROOT / "templates"
OUT = ROOT / "test-themes"

STYLES = ["default", "soza"]

# Representative accent/mode combinations. Not exhaustive - picked to
# stress both ends of the palette math: the shipped default accent, a
# saturated warm hue, a pale/low-contrast-risk accent, and a very dark/deep
# accent (which pushes accent_8/accent_9 hard while lightening, and is
# close to background in dark mode).
SCENARIOS = [
    {"name": "light-blue", "is_dark": False, "is_amoled": False, "accent": "#007AFF"},
    {"name": "dark-blue", "is_dark": True, "is_amoled": False, "accent": "#007AFF"},
    {"name": "dark-blue-amoled", "is_dark": True, "is_amoled": True, "accent": "#007AFF"},
    {"name": "light-red", "is_dark": False, "is_amoled": False, "accent": "#E53935"},
    {"name": "dark-red", "is_dark": True, "is_amoled": False, "accent": "#E53935"},
    {"name": "light-pale-yellow", "is_dark": False, "is_amoled": False, "accent": "#FFD54F"},
    {"name": "dark-pale-yellow", "is_dark": True, "is_amoled": False, "accent": "#FFD54F"},
    {"name": "light-deep-purple", "is_dark": False, "is_amoled": False, "accent": "#4A148C"},
    {"name": "dark-deep-purple", "is_dark": True, "is_amoled": False, "accent": "#4A148C"},
]


def load_template(platform: str, style: str, is_dark: bool) -> dict:
    mode = "dark" if is_dark else "light"
    path = TEMPLATES / platform / style / f"{style}_{mode}_template.json"
    with open(path) as f:
        return json.load(f)


def resolve_android(template: dict, scenario: dict) -> dict:
    tints = resolve.get_tinted_color_schema(scenario["accent"], scenario["is_dark"], scenario["is_amoled"])
    result = {}
    for key, role in template.items():
        if role == resolve.ANDROID_GRADIENT_KEY:
            pass  # role is never literally this constant; guard kept for symmetry
        if key == resolve.ANDROID_GRADIENT_KEY:
            continue  # isGradient=False (app default) drops this key entirely
        intended = tints.get(role)
        if intended is None:
            # Not a real role name (e.g. a stray literal hex string, or a typo'd
            # role like "pu_700"). TintedThemeColors.get() has no name match, so
            # the real app silently falls back to Color.BLACK ("#000000" once
            # written out) - reproduced here rather than skipped, since that
            # fallback IS what ships; unresolved_role=True flags it for the report.
            exported = "#000000"
            result[key] = {"role": role, "intended_hex": None, "exported": exported, "unresolved_role": True}
            continue
        exported = resolve.android_export_hex(intended)
        result[key] = {"role": role, "intended_hex": intended, "exported": exported}
    return result


def resolve_ios(template: dict, scenario: dict) -> dict:
    tints = resolve.get_tinted_color_schema(scenario["accent"], scenario["is_dark"], scenario["is_amoled"])
    result = {}
    for key, role in template.items():
        if key in resolve.IOS_GRADIENT_KEYS:
            continue  # isGradient=False (app default) drops these
        if key in resolve.IOS_LITERAL_KEYS:
            result[key] = {"role": role, "intended_hex": None, "exported": role, "literal": True}
            continue
        intended = tints.get(role)
        if intended is None:
            # Same fallback as Android's resolver (IosThemeFileAdapter.resolveValue():
            # "tints.rawHex(role) ?: \"000000\"") - reproduced rather than skipped.
            exported = "000000"
            result[key] = {"role": role, "intended_hex": None, "exported": exported, "unresolved_role": True}
            continue
        exported = resolve.ios_export_value(role, intended)
        result[key] = {"role": role, "intended_hex": intended, "exported": exported}
    return result


def write_android_file(path: Path, resolved: dict) -> None:
    with open(path, "w") as f:
        for key, entry in resolved.items():
            f.write(f"{key}={entry['exported']}\n")


def write_ios_file(path: Path, resolved: dict, scenario: dict, style: str) -> None:
    tree: dict = {}
    for key, entry in resolved.items():
        segments = key.split(".")
        node = tree
        for seg in segments[:-1]:
            node = node.setdefault(seg, {})
        node[segments[-1]] = entry["exported"]

    lines = []
    mode = "dark" if scenario["is_dark"] else "light"
    accent_label = scenario["accent"].lstrip("#").lower()
    lines.append(f"name: {style}-{mode}-{accent_label}")

    def append_nested(node: dict, indent: int):
        for k, v in node.items():
            if isinstance(v, dict):
                lines.append("  " * indent + f"{k}:")
                append_nested(v, indent + 1)
            else:
                lines.append("  " * indent + f"{k}: {v}")

    append_nested(tree, 0)
    path.write_text("\n".join(lines) + "\n")


def main():
    generated = []
    for platform, ext, resolver, writer in [
        ("android", ".attheme", resolve_android, write_android_file),
        ("ios", ".tgios-theme", resolve_ios, write_ios_file),
    ]:
        for style in STYLES:
            for scenario in SCENARIOS:
                template = load_template(platform, style, scenario["is_dark"])
                resolved = resolver(template, scenario)

                out_dir = OUT / platform / style
                out_dir.mkdir(parents=True, exist_ok=True)

                real_file = out_dir / f"{scenario['name']}{ext}"
                if platform == "android":
                    write_android_file(real_file, resolved)
                else:
                    write_ios_file(real_file, resolved, scenario, style)

                sidecar = out_dir / f"{scenario['name']}.resolved.json"
                sidecar.write_text(json.dumps({"scenario": scenario, "keys": resolved}, indent=2))

                generated.append(str(real_file.relative_to(ROOT)))

    print(f"Generated {len(generated)} theme files under {OUT.relative_to(ROOT.parent)}/")
    for g in generated:
        print(f"  {g}")


if __name__ == "__main__":
    main()
