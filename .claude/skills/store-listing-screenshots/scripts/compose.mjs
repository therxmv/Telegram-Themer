#!/usr/bin/env node
// Renders config/tiles.json + raw app screenshots into the final 1080x1920
// Play Store listing images, using the exact CSS from the Claude Design
// handoff bundle (telegramthemer-store-listing-images/project/Play Store
// Screenshots.dc.html) so the output is pixel-identical to that design.
import { chromium } from "playwright";
import { readFileSync, mkdirSync, existsSync } from "fs";
import path from "path";
import { fileURLToPath } from "url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));

const [, , screenshotsDirArg, outDirArg] = process.argv;
const screenshotsDir = path.resolve(screenshotsDirArg || path.join(__dirname, "../screenshots"));
const outDir = path.resolve(outDirArg || path.join(__dirname, "../output"));
mkdirSync(outDir, { recursive: true });

const tiles = JSON.parse(readFileSync(path.join(__dirname, "../config/tiles.json"), "utf8"));
const posterConfigPath = path.join(__dirname, "../config/poster.json");
const poster = existsSync(posterConfigPath) ? JSON.parse(readFileSync(posterConfigPath, "utf8")) : null;

// One timestamp for the whole run, stamped into every output filename so it's
// obvious at a glance (in Finder/ls, no need to open the file) which render
// pass a given PNG came from.
function runTimestamp() {
  const d = new Date();
  const pad = (n) => String(n).padStart(2, "0");
  return `${d.getFullYear()}${pad(d.getMonth() + 1)}${pad(d.getDate())}-${pad(d.getHours())}${pad(d.getMinutes())}${pad(d.getSeconds())}`;
}
const timestamp = runTimestamp();

function esc(s) {
  return s.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

function headlineHTML(headline, { fontSize = 92, lineHeight = 1.32, maxWidth = "904px" } = {}) {
  const inner = headline.parts
    .map((p) => {
      if (!p.highlight) return esc(p.text);
      const bg = p.bg || "#229ED9";
      return `<span style="display:inline-block;white-space:nowrap;line-height:1;background:${bg};color:#ffffff;border-radius:999px;padding:0.16em 0.3em;margin:0 -0.06em;transform:translateY(0.055em)">${esc(p.text)}</span>`;
    })
    .join("");
  const maxWidthRule = maxWidth ? `max-width:${maxWidth};` : "";
  return `<div style="font-size:${fontSize}px;line-height:${lineHeight};font-weight:800;color:#0c1a26;letter-spacing:-.025em;text-wrap:pretty;${maxWidthRule}">${inner}</div>`;
}

function imageDataUri(imgPath) {
  // Playwright's setContent() pages have an opaque (about:blank) origin, which
  // Chromium refuses to let load file:// resources — so the screenshots must
  // be inlined as data URIs rather than referenced by path.
  const buf = readFileSync(imgPath);
  return `data:image/png;base64,${buf.toString("base64")}`;
}

function frameHTML(frame) {
  const imgPath = path.resolve(screenshotsDir, frame.image);
  if (!existsSync(imgPath)) {
    throw new Error(`missing screenshot: ${imgPath} (run the capture phase first)`);
  }
  const half = frame.dot / 2;
  const transform = frame.rotate ? `transform:rotate(${frame.rotate}deg);` : "";
  const zIndex = frame.zIndex != null ? `z-index:${frame.zIndex};` : "";
  const shadow = frame.shadow || "0 50px 100px rgba(12,26,38,.28), 0 0 0 1px rgba(12,26,38,.12)";
  return `
  <div style="position:absolute;left:${frame.left}px;top:${frame.top}px;${transform}${zIndex}">
    <div style="position:relative;box-sizing:border-box;width:${frame.width}px;height:${frame.height}px;border-radius:${frame.radius}px;padding:${frame.padding}px;background:#1b2027;box-shadow:${shadow}">
      <div style="width:100%;height:100%;border-radius:${frame.innerRadius}px;overflow:hidden;background:#000">
        <img src="${imageDataUri(imgPath)}" style="display:block;width:100%;height:100%;object-fit:cover">
      </div>
      <div style="position:absolute;top:${frame.dotTop}px;left:50%;margin-left:-${half}px;width:${frame.dot}px;height:${frame.dot}px;border-radius:50%;background:#05070a;box-shadow:0 0 0 1px rgba(255,255,255,.06)"></div>
    </div>
  </div>`;
}

function tileHTML(tile) {
  return `<!doctype html>
<html><head><meta charset="utf-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="">
<link href="https://fonts.googleapis.com/css2?family=Overpass:wght@400;600;700;800;900&display=swap" rel="stylesheet">
<style>html,body{margin:0;padding:0}</style>
</head>
<body style="font-family:Overpass,Helvetica,sans-serif">
<div style="position:relative;width:1080px;height:1920px;overflow:hidden;background:${tile.background}">
  <div style="position:absolute;inset:0;opacity:1;background:${tile.glow}"></div>
  <div style="position:absolute;left:88px;top:112px;right:88px;display:flex;flex-direction:column;gap:22px">
    ${headlineHTML(tile.headline)}
  </div>
  ${tile.frames.map(frameHTML).join("")}
</div>
</body></html>`;
}

function posterHTML(p) {
  const pillsHTML = p.pills
    .map(
      (label) =>
        `<div style="font-size:24px;font-weight:700;color:#0f5c8a;background:rgba(34,158,217,.16);border-radius:999px;padding:14px 26px">${esc(label)}</div>`
    )
    .join("");
  const frames = p.frames.map((f) => frameHTML(f.shadow ? f : { ...f, shadow: p.frameShadow }));
  return `<!doctype html>
<html><head><meta charset="utf-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="">
<link href="https://fonts.googleapis.com/css2?family=Overpass:wght@400;600;700;800;900&display=swap" rel="stylesheet">
<style>html,body{margin:0;padding:0}</style>
</head>
<body style="font-family:Overpass,Helvetica,sans-serif">
<div style="position:relative;width:${p.width}px;height:${p.height}px;overflow:hidden;background:${p.background}">
  <div style="position:absolute;inset:0;opacity:1;background:${p.glow}"></div>
  <div style="position:absolute;left:104px;top:196px;width:700px;display:flex;flex-direction:column;gap:34px">
    <div style="font-size:24px;font-weight:700;letter-spacing:.24em;color:#1a7fba;text-transform:uppercase">${esc(p.eyebrow)}</div>
    ${headlineHTML(p.headline, { fontSize: 88, lineHeight: 1.3, maxWidth: null })}
    <div style="font-size:34px;line-height:1.5;font-weight:400;color:#31485c;max-width:660px;text-wrap:pretty">${esc(p.tagline)}</div>
    <div style="display:flex;flex-wrap:wrap;gap:14px;margin-top:6px">${pillsHTML}</div>
  </div>
  ${frames.join("")}
</div>
</body></html>`;
}

const browser = await chromium.launch();
const page = await browser.newPage({ viewport: { width: 1080, height: 1920 }, deviceScaleFactor: 1 });

for (const tile of tiles) {
  process.stdout.write(`rendering ${tile.id}... `);
  await page.setContent(tileHTML(tile), { waitUntil: "networkidle" });
  await page.evaluate(() => document.fonts.ready); // wait for Overpass to actually be applied
  const outPath = path.join(outDir, `${tile.id}_${timestamp}.png`);
  await page.screenshot({ path: outPath });
  console.log(`-> ${outPath}`);
}

if (poster) {
  process.stdout.write(`rendering ${poster.id}... `);
  await page.setViewportSize({ width: poster.width, height: poster.height });
  await page.setContent(posterHTML(poster), { waitUntil: "networkidle" });
  await page.evaluate(() => document.fonts.ready);
  const outPath = path.join(outDir, `${poster.id}_${timestamp}.png`);
  await page.screenshot({ path: outPath });
  console.log(`-> ${outPath}`);
}

await browser.close();
