"""
Faithful Python port of the app's real theme-resolution code, so generated
test themes are byte-for-byte what the app would produce for the same
inputs. Ported (not reimplemented from scratch) from:

  - app/src/main/java/com/therxmv/telegramthemer/data/extensions/TintsExtensions.kt
  - app/src/main/java/com/therxmv/telegramthemer/data/extensions/ColorExtensions.kt
  - app/src/main/java/com/therxmv/telegramthemer/data/values/ThemeColorsProvider.kt
  - app/src/main/java/com/therxmv/telegramthemer/data/adapter/AndroidThemeFileAdapter.kt
  - app/src/main/java/com/therxmv/telegramthemer/data/adapter/IosThemeFileAdapter.kt

Deliberately NOT ported: Monet (state.isMonet), since it seeds from the
device's live wallpaper colors (Android's ContextCompat.getColor lookups)
and has no static input to reproduce headlessly. Every scenario here runs
with isMonet=False, same as the app's own default ThemeState.
"""
from __future__ import annotations

AMOLED_BLACK = "#000000"
DARK_BLACK = "#181818"
WHITE = "#FFFFFF"
GRAY = "#919191"

# BaseThemeColors defaults (domain/model/BaseThemeColors.kt) - fixed status
# hues, independent of the user's accent.
RED = "#E3B727"
ORANGE = "#DF9700"
YELLOW = "#E23333"
GREEN = "#52CF2C"
BLUE = "#299FE9"
PURPLE = "#776BF5"

# iOS LITERAL_KEYS (IosThemeFileAdapter.kt) - values copied verbatim from
# the template instead of resolved through the role map.
IOS_LITERAL_KEYS = {
    "dark",
    "basedOn",
    "root.keyboard",
    "intro.statusBar",
    "root.statusBar",
    "actionSheet.bgType",
    "notification.expanded.bgType",
    "chat.animateMessageColors",
}

# Gradient-only keys, dropped when ThemeState.isGradient is False (the
# app's default) - AndroidThemeValuesProvider/IosThemeValuesProvider.
ANDROID_GRADIENT_KEY = "chat_outBubbleGradient"
IOS_GRADIENT_KEYS = {
    "chat.message.outgoing.bubble.withWp.gradientBg",
    "chat.message.outgoing.bubble.withoutWp.gradientBg",
}


def hex_to_rgb(hex6: str) -> list[int]:
    """Mirrors String.hexToRgb() - expects "#RRGGBB" (exactly 6 hex digits)."""
    return [
        int(hex6[1:3], 16),
        int(hex6[3:5], 16),
        int(hex6[5:7], 16),
    ]


def rgb_to_hex(rgb: list[int]) -> str:
    return "#%02x%02x%02x" % tuple(rgb)


def _lighter(hex6: str, factor: float) -> str:
    rgb = hex_to_rgb(hex6)
    out = [min(255, max(0, int(c + factor * (255 - c)))) for c in rgb]
    return rgb_to_hex(out)


def _darker(hex6: str, factor: float) -> str:
    rgb = hex_to_rgb(hex6)
    out = [min(255, max(0, int(c * (1 - factor)))) for c in rgb]
    return rgb_to_hex(out)


def generate_all_tints(base_hex: str) -> list[str]:
    """Mirrors generateAllTints(): 11 steps (0..10), 0=black-ward, 10=white-ward, 5=unchanged."""
    tints = []
    for i in range(11):
        if i < 5:
            tints.append(_darker(base_hex, 1 - i * 0.2))
        elif i > 5:
            tints.append(_lighter(base_hex, (i - 5) * 0.2))
        else:
            tints.append(base_hex)
    return tints


def get_base_colors(accent_hex: str, is_dark: bool, is_amoled: bool) -> dict:
    """Mirrors ThemeColorsProvider.getBaseColors() (Monet branch omitted)."""
    black = AMOLED_BLACK if (is_amoled and is_dark) else DARK_BLACK
    white = WHITE
    gray = GRAY

    background = black if is_dark else white
    on_background = white if is_dark else black

    return {
        "background": background,
        "onBackground": on_background,
        # Real accent input is always lowercase (Int.colorToHex() -> Integer.toHexString(),
        # which never produces uppercase); normalize here so index-5 passthrough
        # ("#007AFF" itself, unchanged by generate_all_tints) matches the app's casing.
        "accent": accent_hex.lower(),
        "gray": gray,
        "red": RED,
        "orange": ORANGE,
        "yellow": YELLOW,
        "green": GREEN,
        "blue": BLUE,
        "purple": PURPLE,
        "transparent": "#00000000",
    }


def get_tinted_color_schema(accent_hex: str, is_dark: bool, is_amoled: bool) -> dict:
    """
    Mirrors ThemeColorsProvider.getTintedColorSchema(). Returns role name ->
    hex string, preserving digit count (6 for opaque, 8 for the alpha-
    prefixed tr_* / transparent_0 roles) exactly like TintedThemeColors.rawHex.
    """
    base = get_base_colors(accent_hex, is_dark, is_amoled)
    grays = generate_all_tints(base["gray"])
    accents = generate_all_tints(base["accent"])
    # Monet gray override (grays[1]/[8]/[9] <- accents[1]/[8]/[9]) intentionally skipped.

    colors: dict[str, str] = {}
    colors["tt_background"] = base["background"]
    colors["tt_onBackground"] = base["onBackground"]

    for i in range(1, 10):
        colors[f"gray_{i}"] = grays[i]
        colors[f"accent_{i}"] = accents[i]

    colors["red_5"] = base["red"]
    colors["orange_5"] = base["orange"]
    colors["yellow_5"] = base["yellow"]
    colors["green_5"] = base["green"]
    colors["blue_5"] = base["blue"]
    colors["purple_5"] = base["purple"]

    colors["transparent_0"] = base["transparent"]
    colors["tr_accent_5"] = "#77" + accents[5][1:]
    colors["tr_accent_7"] = "#44" + accents[7][1:]
    colors["tr_gray_5"] = "#77" + grays[5][1:]
    colors["tr_gray_3"] = "#AA" + grays[3][1:]
    colors["tr_background_9"] = "#E5" + base["background"][1:]

    return colors


def android_export_hex(role_hex: str) -> str:
    """
    Mirrors Int.colorToHex() as actually used by AndroidThemeFileAdapter:
    every value - including the alpha-prefixed tr_*/transparent_0 roles -
    is written through the SAME plain "#RRGGBB" writer, with no tr_*
    special-case. That function's own doc says "without transparency" and
    carries a "// TODO think about transparency" - so this is a confirmed,
    intentional-but-flagged gap, not a guess: every tr_* role loses its
    alpha and comes out as a fully opaque solid color in the real .attheme
    output. transparent_0 (#00000000) is the degenerate case where Java's
    Integer.toHexString(0) == "0", and dropping 2 chars from a 1-char
    string leaves "" - reproduced exactly, matching the shipped samples
    (e.g. `actionBarActionModeDefaultTop=#` in
    theme-wizard/samples/android/Default-dark-007aff-776.attheme).
    """
    # Real pipeline round-trips through Color.parseColor()/an Int, and
    # Integer.toHexString() always emits lowercase - independent of the
    # source string's casing - so the output is unconditionally lowercased.
    digits = role_hex[1:].lower()
    if len(digits) == 8:
        if digits == "00000000":
            return "#"
        return "#" + digits[2:]  # drop AA, keep RRGGBB
    return "#" + digits  # already plain RRGGBB


def ios_export_value(role_name: str, role_hex: str) -> str:
    """
    Mirrors IosThemeFileAdapter.resolveValue() for role (non-literal) keys:
    transparent_0 -> "clear"; tr_* keep their natural 8-digit AARRGGBB;
    everything else is plain 6-digit hex. No leading "#".
    """
    if role_name == "transparent_0":
        return "clear"
    return role_hex[1:].lower()
