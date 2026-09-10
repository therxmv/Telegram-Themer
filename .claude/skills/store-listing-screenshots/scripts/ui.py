#!/usr/bin/env python3
"""adb-driven UI helper for the store-listing-screenshots skill.

Small wrapper around `adb` + `uiautomator dump` so an agent can inspect the
live screen (by text/content-desc, not hardcoded coordinates) and drive the
app without a dedicated instrumentation harness.

Usage:
  ui.py devices
  ui.py dump                                  # list interactive elements as JSON
  ui.py tap --text "Dark"                     # tap element whose text/desc/res-id matches (substring, case-insensitive)
  ui.py tap --xy 540 1200                     # tap raw coordinates
  ui.py swipe 900 1200 200 1200 [duration_ms]  # e.g. swipe left to move a horizontal pager
  ui.py back
  ui.py screenshot out.png                    # adb screencap -> pulled PNG, prints its pixel size
  ui.py reset-app [package]                   # force-stop + pm clear, so the next launch is a clean install
  ui.py launch [package] [activity]           # am start the launcher activity

All commands accept an optional --serial/-s to target a specific device;
otherwise the sole attached device is used (errors if there's more than one).
"""
import argparse
import json
import re
import subprocess
import sys
import xml.etree.ElementTree as ET

DEFAULT_PACKAGE = "com.therxmv.telegramthemer"
DEFAULT_ACTIVITY = ".ui.editor.ThemeEditorActivity"
DUMP_PATH_DEVICE = "/sdcard/window_dump.xml"
DUMP_PATH_LOCAL = "/tmp/telegramthemer_window_dump.xml"


def adb_base(serial):
    return ["adb"] + (["-s", serial] if serial else [])


def run(cmd, **kw):
    return subprocess.run(cmd, check=True, **kw)


def pick_serial(serial):
    if serial:
        return serial
    out = subprocess.run(["adb", "devices"], capture_output=True, text=True, check=True).stdout
    lines = [l for l in out.splitlines()[1:] if l.strip() and "\tdevice" in l]
    if len(lines) == 1:
        return lines[0].split("\t")[0]
    if not lines:
        sys.exit("No adb devices attached. Start the emulator first.")
    sys.exit(f"Multiple devices attached, pass --serial: {[l.split(chr(9))[0] for l in lines]}")


def cmd_devices(args):
    run(["adb", "devices", "-l"])


def parse_bounds(b):
    m = re.match(r"\[(\d+),(\d+)\]\[(\d+),(\d+)\]", b)
    x1, y1, x2, y2 = map(int, m.groups())
    return x1, y1, x2, y2


def dump_elements(serial):
    base = adb_base(serial)
    run(base + ["shell", "uiautomator", "dump", DUMP_PATH_DEVICE], stdout=subprocess.DEVNULL)
    run(base + ["pull", DUMP_PATH_DEVICE, DUMP_PATH_LOCAL], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    tree = ET.parse(DUMP_PATH_LOCAL)
    elements = []
    for node in tree.iter("node"):
        text = node.get("text") or ""
        desc = node.get("content-desc") or ""
        clickable = node.get("clickable") == "true"
        res_id = node.get("resource-id") or ""
        if not (text or desc or clickable):
            continue
        x1, y1, x2, y2 = parse_bounds(node.get("bounds"))
        elements.append({
            "text": text,
            "desc": desc,
            "res_id": res_id,
            "class": node.get("class") or "",
            "clickable": clickable,
            "cx": (x1 + x2) // 2,
            "cy": (y1 + y2) // 2,
            "bounds": [x1, y1, x2, y2],
        })
    return elements


def cmd_dump(args):
    serial = pick_serial(args.serial)
    elements = dump_elements(serial)
    print(json.dumps(elements, indent=2))


def find_element(elements, query):
    q = query.lower()
    # exact match first, then substring, preferring clickable elements
    for want_exact in (True, False):
        for want_clickable in (True, False):
            for el in elements:
                hay = [el["text"].lower(), el["desc"].lower(), el["res_id"].lower()]
                match = any(h == q for h in hay) if want_exact else any(q in h for h in hay if h)
                if match and el["clickable"] == want_clickable:
                    return el
    return None


def cmd_tap(args):
    serial = pick_serial(args.serial)
    base = adb_base(serial)
    if args.xy:
        x, y = args.xy
    else:
        elements = dump_elements(serial)
        el = find_element(elements, args.text)
        if not el:
            sys.exit(f"No element matched {args.text!r}. Run `ui.py dump` to inspect the screen.")
        x, y = el["cx"], el["cy"]
        print(f"tapping {el['text'] or el['desc'] or el['res_id']!r} at ({x},{y})", file=sys.stderr)
    run(base + ["shell", "input", "tap", str(x), str(y)])


def cmd_swipe(args):
    serial = pick_serial(args.serial)
    base = adb_base(serial)
    cmd = base + ["shell", "input", "swipe", str(args.x1), str(args.y1), str(args.x2), str(args.y2)]
    if args.duration:
        cmd.append(str(args.duration))
    run(cmd)


def cmd_back(args):
    serial = pick_serial(args.serial)
    run(adb_base(serial) + ["shell", "input", "keyevent", "KEYCODE_BACK"])


def cmd_screenshot(args):
    serial = pick_serial(args.serial)
    base = adb_base(serial)
    with open(args.out, "wb") as f:
        subprocess.run(base + ["exec-out", "screencap", "-p"], stdout=f, check=True)
    try:
        from PIL import Image
        with Image.open(args.out) as im:
            print(f"wrote {args.out} ({im.width}x{im.height})")
    except ImportError:
        print(f"wrote {args.out} (install pillow to auto-verify dimensions; expect 1080x2400)")


def cmd_reset_app(args):
    serial = pick_serial(args.serial)
    base = adb_base(serial)
    pkg = args.package or DEFAULT_PACKAGE
    run(base + ["shell", "am", "force-stop", pkg])
    run(base + ["shell", "pm", "clear", pkg], stdout=subprocess.DEVNULL)
    cmd_launch(argparse.Namespace(serial=serial, package=pkg, activity=args.activity))


def cmd_launch(args):
    serial = pick_serial(args.serial)
    base = adb_base(serial)
    pkg = args.package or DEFAULT_PACKAGE
    activity = args.activity or DEFAULT_ACTIVITY
    component = f"{pkg}/{activity}" if activity.startswith(".") else activity
    run(base + ["shell", "am", "start", "-n", component])


def main():
    p = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--serial", "-s", default=None)
    sub = p.add_subparsers(dest="cmd", required=True)

    sub.add_parser("devices").set_defaults(func=cmd_devices)
    sub.add_parser("dump").set_defaults(func=cmd_dump)

    tap = sub.add_parser("tap")
    tap.add_argument("--text", help="substring to match against text/content-desc/resource-id")
    tap.add_argument("--xy", nargs=2, type=int, metavar=("X", "Y"))
    tap.set_defaults(func=cmd_tap)

    swipe = sub.add_parser("swipe")
    swipe.add_argument("x1", type=int)
    swipe.add_argument("y1", type=int)
    swipe.add_argument("x2", type=int)
    swipe.add_argument("y2", type=int)
    swipe.add_argument("duration", nargs="?", type=int, default=None)
    swipe.set_defaults(func=cmd_swipe)

    sub.add_parser("back").set_defaults(func=cmd_back)

    shot = sub.add_parser("screenshot")
    shot.add_argument("out")
    shot.set_defaults(func=cmd_screenshot)

    reset = sub.add_parser("reset-app")
    reset.add_argument("package", nargs="?", default=None)
    reset.add_argument("--activity", default=None)
    reset.set_defaults(func=cmd_reset_app)

    launch = sub.add_parser("launch")
    launch.add_argument("package", nargs="?", default=None)
    launch.add_argument("activity", nargs="?", default=None)
    launch.set_defaults(func=cmd_launch)

    args = p.parse_args()
    args.func(args)


if __name__ == "__main__":
    main()
