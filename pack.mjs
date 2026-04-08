import { execSync } from 'child_process';
import fs from 'fs';
import path from 'path';
import https from 'https';
import zlib from 'zlib';

// ── Config ─────────────────────────────────────────────────────────────────
const NODE_VERSION = '22.14.0';
const NODE_URL = `https://nodejs.org/dist/v${NODE_VERSION}/win-x64/node.exe`;
const PROCESS_MANAGER_URL =
    'https://github.com/overwolf/overwolf-plugins/raw/master/dist/process_manager.dll';

const pkg = JSON.parse(fs.readFileSync('package.json', 'utf8'));
const VERSION = pkg.version;
const STAGING = path.resolve('dist-opk', 'staging');
const OUTPUT = path.resolve('dist-opk', `chroma-league-${VERSION}.opk`);

// ── Helpers ────────────────────────────────────────────────────────────────
function download(url, dest) {
    return new Promise((resolve, reject) => {
        fs.mkdirSync(path.dirname(dest), { recursive: true });
        const file = fs.createWriteStream(dest + '.tmp');

        const attempt = (u) => {
            https.get(u, (res) => {
                if (res.statusCode === 301 || res.statusCode === 302) {
                    attempt(res.headers.location);
                    return;
                }
                if (res.statusCode !== 200) {
                    reject(new Error(`HTTP ${res.statusCode} for ${u}`));
                    return;
                }

                const total = parseInt(res.headers['content-length'] || '0', 10);
                let received = 0;
                let lastPct = -1;

                res.on('data', (chunk) => {
                    received += chunk.length;
                    if (total > 0) {
                        const pct = Math.floor((received / total) * 100);
                        if (pct !== lastPct && pct % 10 === 0) {
                            process.stdout.write(`\r  ${pct}%`);
                            lastPct = pct;
                        }
                    }
                });

                res.pipe(file);
                file.on('finish', () => {
                    file.close(() => {
                        process.stdout.write('\r  100%\n');
                        fs.renameSync(dest + '.tmp', dest);
                        resolve();
                    });
                });
            }).on('error', reject);
        };

        attempt(url);
    });
}

function copyFile(src, destDir) {
    const dest = path.join(destDir, path.basename(src));
    fs.mkdirSync(destDir, { recursive: true });
    fs.copyFileSync(src, dest);
}

function copyDir(src, dest) {
    fs.mkdirSync(dest, { recursive: true });
    for (const entry of fs.readdirSync(src, { withFileTypes: true })) {
        const srcPath = path.join(src, entry.name);
        const destPath = path.join(dest, entry.name);
        if (entry.isDirectory()) {
            copyDir(srcPath, destPath);
        } else {
            fs.copyFileSync(srcPath, destPath);
        }
    }
}

// ── Steps ──────────────────────────────────────────────────────────────────

// 1. Build
console.log('\n[1/6] Building bundles...');
execSync('node build.mjs', { stdio: 'inherit' });

// 2. process_manager.dll
const dllDest = path.resolve('plugins', 'process_manager.dll');
if (!fs.existsSync(dllDest)) {
    console.log('\n[2/6] Downloading process_manager.dll...');
    try {
        await download(PROCESS_MANAGER_URL, dllDest);
        console.log('  → plugins/process_manager.dll');
    } catch (err) {
        console.error(`\n[ERROR] Failed to download process_manager.dll: ${err.message}`);
        console.error('  Download manually from:');
        console.error('  https://github.com/overwolf/overwolf-plugins/tree/master/process-manager/dist');
        console.error('  and place it in: plugins/process_manager.dll');
        process.exit(1);
    }
} else {
    console.log('\n[2/6] process_manager.dll already present.');
}

// 3. node.exe
const nodeExeDest = path.resolve('node', 'node.exe');
if (!fs.existsSync(nodeExeDest)) {
    console.log(`\n[3/6] Downloading Node.js ${NODE_VERSION} (~60MB, one-time)...`);
    try {
        await download(NODE_URL, nodeExeDest);
        console.log('  → node/node.exe');
    } catch (err) {
        console.error(`\n[ERROR] Failed to download node.exe: ${err.message}`);
        console.error(`  Download manually from: ${NODE_URL}`);
        console.error('  and place it in: node/node.exe');
        process.exit(1);
    }
} else {
    console.log('\n[3/6] node/node.exe already present.');
}

// 4. Staging
console.log('\n[4/6] Preparing staging directory...');
if (fs.existsSync(STAGING)) fs.rmSync(STAGING, { recursive: true });
fs.mkdirSync(STAGING, { recursive: true });

// Root files
for (const f of ['manifest.json', 'background.html', 'index.html', 'style.css',
                  'icon.png', 'icon_gray.png', 'start-server.cmd', 'LICENSE']) {
    copyFile(f, STAGING);
}

// dist/
copyFile('dist/background.js', path.join(STAGING, 'dist'));
copyFile('dist/server.js',     path.join(STAGING, 'dist'));

// plugins/
copyFile('plugins/process_manager.dll', path.join(STAGING, 'plugins'));

// node/
copyFile('node/node.exe', path.join(STAGING, 'node'));

// node_modules/koffi/ — only win32_x64 binary, skip all other platforms
const koffiSrc  = 'node_modules/koffi';
const koffiDest = path.join(STAGING, 'node_modules', 'koffi');
for (const f of ['index.js', 'indirect.js', 'index.d.ts', 'package.json']) {
    copyFile(path.join(koffiSrc, f), koffiDest);
}
copyDir(
    path.join(koffiSrc, 'build', 'koffi', 'win32_x64'),
    path.join(koffiDest, 'build', 'koffi', 'win32_x64')
);

console.log('  → staging ready');

// 5. ZIP → .opk
console.log(`\n[5/6] Creating ${path.basename(OUTPUT)}...`);
fs.mkdirSync(path.dirname(OUTPUT), { recursive: true });
if (fs.existsSync(OUTPUT)) fs.unlinkSync(OUTPUT);

// Pure Node.js ZIP writer — forward-slash paths, files only (no directory entries)
// Avoids Overwolf extraction failure caused by PowerShell Compress-Archive
// producing backslash paths and empty directory entries.
{
    // CRC-32 table
    const crcTable = (() => {
        const t = new Uint32Array(256);
        for (let n = 0; n < 256; n++) {
            let c = n;
            for (let k = 0; k < 8; k++) c = (c & 1) ? 0xEDB88320 ^ (c >>> 1) : c >>> 1;
            t[n] = c;
        }
        return t;
    })();
    function crc32(buf) {
        let c = 0xFFFFFFFF;
        for (let i = 0; i < buf.length; i++) c = crcTable[(c ^ buf[i]) & 0xFF] ^ (c >>> 8);
        return (c ^ 0xFFFFFFFF) >>> 0;
    }
    function u16(n) { const b = Buffer.alloc(2); b.writeUInt16LE(n, 0); return b; }
    function u32(n) { const b = Buffer.alloc(4); b.writeUInt32LE(n >>> 0, 0); return b; }

    const LOCAL_SIG   = Buffer.from([0x50, 0x4B, 0x03, 0x04]);
    const CENTRAL_SIG = Buffer.from([0x50, 0x4B, 0x01, 0x02]);
    const EOCD_SIG    = Buffer.from([0x50, 0x4B, 0x05, 0x06]);

    // Collect all files recursively
    function collectFiles(dir, base = '') {
        const entries = [];
        for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
            const rel = base ? `${base}/${entry.name}` : entry.name;
            const abs = path.join(dir, entry.name);
            if (entry.isDirectory()) {
                entries.push(...collectFiles(abs, rel));
            } else {
                entries.push({ rel, abs });
            }
        }
        return entries;
    }

    const files = collectFiles(STAGING);
    const chunks = [];
    const centralDir = [];
    let offset = 0;

    for (const { rel, abs } of files) {
        const nameBuf = Buffer.from(rel, 'utf8');  // forward slashes already
        const raw = fs.readFileSync(abs);
        const compressed = zlib.deflateRawSync(raw, { level: 6 });
        // Use compressed only if smaller
        const useDeflate = compressed.length < raw.length;
        const data = useDeflate ? compressed : raw;
        const method = useDeflate ? 8 : 0;
        const crc = crc32(raw);
        const mtime = 0, mdate = 0;  // zero timestamps fine for OPK

        const local = Buffer.concat([
            LOCAL_SIG,
            u16(20), u16(0),
            u16(method), u16(mtime), u16(mdate),
            u32(crc), u32(data.length), u32(raw.length),
            u16(nameBuf.length), u16(0),
            nameBuf,
            data,
        ]);

        centralDir.push(Buffer.concat([
            CENTRAL_SIG,
            u16(20), u16(20), u16(0),
            u16(method), u16(mtime), u16(mdate),
            u32(crc), u32(data.length), u32(raw.length),
            u16(nameBuf.length), u16(0), u16(0),
            u16(0), u16(0), u32(0),
            u32(offset),
            nameBuf,
        ]));

        chunks.push(local);
        offset += local.length;
    }

    const centralBuf = Buffer.concat(centralDir);
    const eocd = Buffer.concat([
        EOCD_SIG,
        u16(0), u16(0),
        u16(files.length), u16(files.length),
        u32(centralBuf.length), u32(offset),
        u16(0),
    ]);

    fs.writeFileSync(OUTPUT, Buffer.concat([...chunks, centralBuf, eocd]));
}

// 6. Cleanup
console.log('\n[6/6] Cleaning up staging...');
fs.rmSync(STAGING, { recursive: true });

console.log(`\n✓ Done: ${OUTPUT}\n`);
