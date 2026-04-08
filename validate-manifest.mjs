import { readFileSync, statSync, existsSync } from 'fs';
import https from 'https';
import Ajv from 'ajv';

const SCHEMA_URL = 'https://raw.githubusercontent.com/overwolf/community-gists/master/overwolf-manifest-schema.json';

// ── helpers ──────────────────────────────────────────────────────────────────

function fetchJson(url) {
  return new Promise((resolve, reject) => {
    https.get(url, (res) => {
      let data = '';
      res.on('data', (chunk) => data += chunk);
      res.on('end', () => {
        try { resolve(JSON.parse(data)); }
        catch (e) { reject(new Error(`Failed to parse response: ${e.message}`)); }
      });
    }).on('error', reject);
  });
}

/** Read width/height from a PNG file header (bytes 16–23 of the IHDR chunk). */
function readPngDimensions(filePath) {
  const buf = readFileSync(filePath);
  const PNG_SIG = [0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a];
  for (let i = 0; i < 8; i++) {
    if (buf[i] !== PNG_SIG[i]) return null; // not a PNG
  }
  const width  = buf.readUInt32BE(16);
  const height = buf.readUInt32BE(20);
  return { width, height };
}

const errors   = [];
const warnings = [];

function error(msg)   { errors.push(`  ✗ ${msg}`); }
function warn(msg)    { warnings.push(`  ⚠ ${msg}`); }
function checkIcon(field, path, maxKB, expectedDim) {
  if (!path) return;
  if (!existsSync(path)) {
    error(`${field}: file not found — "${path}"`);
    return;
  }
  const sizeKB = statSync(path).size / 1024;
  if (sizeKB > maxKB) {
    error(`${field}: ${sizeKB.toFixed(1)} KB exceeds ${maxKB} KB limit — "${path}"`);
  }
  if (expectedDim && path.toLowerCase().endsWith('.png')) {
    const dim = readPngDimensions(path);
    if (!dim) {
      warn(`${field}: could not read PNG dimensions — "${path}"`);
    } else if (dim.width !== expectedDim || dim.height !== expectedDim) {
      error(`${field}: dimensions ${dim.width}×${dim.height} — expected ${expectedDim}×${expectedDim} — "${path}"`);
    }
  }
}

// ── load manifest ─────────────────────────────────────────────────────────────

let manifest;
try {
  manifest = JSON.parse(readFileSync('manifest.json', 'utf8'));
} catch (e) {
  console.error(`✗ manifest.json: ${e.message}`);
  process.exit(1);
}

// ── 1. Schema validation ──────────────────────────────────────────────────────

console.log('Fetching Overwolf manifest schema…');
const schema = await fetchJson(SCHEMA_URL);
const ajv = new Ajv({ allErrors: true, strict: false });
const validate = ajv.compile(schema);

// Some valid Overwolf manifest fields are not yet present in the community schema.
// Strip them before AJV validation to avoid false positives, while keeping them
// in the actual manifest file.
const KNOWN_EXTRA_META_FIELDS = ['uid'];
const manifestForValidation = JSON.parse(JSON.stringify(manifest));
for (const field of KNOWN_EXTRA_META_FIELDS) {
  delete manifestForValidation.meta?.[field];
}

console.log('\n[1] Schema validation');
if (validate(manifestForValidation)) {
  console.log('  ✓ Valid against Overwolf schema');
} else {
  for (const err of validate.errors) {
    error(`${err.instancePath || '(root)'} ${err.message}`);
  }
}

// ── 2. Manifest meta checks ───────────────────────────────────────────────────

console.log('\n[2] Manifest meta');
const meta = manifest.meta ?? {};

if (!meta.uid) {
  error('meta.uid is missing — required for stable installs (author + name must stay consistent)');
}
if (!meta['minimum-overwolf-version']) {
  warn('meta.minimum-overwolf-version is not set');
} else {
  console.log(`  ✓ minimum-overwolf-version: ${meta['minimum-overwolf-version']}`);
}
if (meta.name && meta.author) {
  console.log(`  ✓ name + author present (UID stability: "${meta.name}" / "${meta.author}")`);
} else {
  error('meta.name and/or meta.author missing — changing these changes the app UID');
}

// ── 3. Icon file checks ───────────────────────────────────────────────────────

console.log('\n[3] Icons');
// icon + icon_gray: < 30 KB, 256×256
checkIcon('meta.icon',      meta.icon,         30,  256);
checkIcon('meta.icon_gray', meta.icon_gray,    30,  256);
// launcher_icon: < 150 KB (multi-res ICO — dimension check skipped)
checkIcon('meta.launcher_icon', meta.launcher_icon, 150, null);
// store_icon: existence only
if (meta.store_icon && !existsSync(meta.store_icon)) {
  error(`meta.store_icon: file not found — "${meta.store_icon}"`);
}

// Dock icons referenced in windows (icon_image)
const windows = manifest.data?.windows ?? {};
for (const [winName, win] of Object.entries(windows)) {
  if (win.icon_image) {
    checkIcon(`windows.${winName}.icon_image`, win.icon_image, 30, 256);
  }
}

if (!errors.some(e => e.includes('meta.icon'))) console.log('  ✓ icon files present and within limits');

// ── 4. Window advertisement flags ────────────────────────────────────────────

console.log('\n[4] Window ad flags (block_top_window_navigation, popup_blocker, mute)');
for (const [winName, win] of Object.entries(windows)) {
  if (win.is_background_page) continue; // background pages are exempt
  const missing = ['block_top_window_navigation', 'popup_blocker', 'mute']
    .filter(flag => win[flag] !== true);
  if (missing.length) {
    warn(`windows.${winName}: recommended flags not set — ${missing.join(', ')}`);
  } else {
    console.log(`  ✓ windows.${winName}: all ad flags set`);
  }
}

// ── 5. Game targeting & launch events ────────────────────────────────────────

console.log('\n[5] Game targeting & launch events');
const data = manifest.data ?? {};
if (!data.game_targeting) {
  warn('data.game_targeting not configured');
} else {
  console.log(`  ✓ game_targeting: ${JSON.stringify(data.game_targeting)}`);
}
if (!data.launch_events?.length) {
  warn('data.launch_events not configured');
} else {
  console.log(`  ✓ launch_events: ${data.launch_events.length} event(s)`);
}

// ── Summary ───────────────────────────────────────────────────────────────────

console.log('\n── Summary ──────────────────────────────────────────────');
if (warnings.length) {
  console.warn('\nWarnings:');
  warnings.forEach(w => console.warn(w));
}
if (errors.length) {
  console.error('\nErrors:');
  errors.forEach(e => console.error(e));
  console.error('\nValidation FAILED.');
  process.exit(1);
} else {
  console.log('\nValidation PASSED' + (warnings.length ? ' (with warnings).' : '.'));
}
