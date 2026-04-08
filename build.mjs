import * as esbuild from 'esbuild';

// Bundle 1: OW Native background window (Chromium — no Node.js)
await esbuild.build({
    entryPoints: ['src/background.ts'],
    bundle: true,
    outfile: 'dist/background.js',
    platform: 'browser',
    format: 'iife',
});

// Bundle 2: Node.js sidecar server (full Node.js — koffi stays external)
await esbuild.build({
    entryPoints: ['src/server.ts'],
    bundle: true,
    outfile: 'dist/server.js',
    platform: 'node',
    format: 'cjs',
    external: ['koffi'],
});

console.log('Build complete: dist/background.js + dist/server.js');
