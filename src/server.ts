import util from 'util';
import { WebSocketServer, WebSocket } from 'ws';
import { ChromaLeague } from './ChromaLeague.js';
import { ChromaNativeSDK } from './razer-sdk/ChromaNativeSDK.js';
import { RunningState } from './state/RunningState.js';

const PORT = 7891;

const wss = new WebSocketServer({ port: PORT });
const chromaLeague = new ChromaLeague();

function broadcast(data: object): void {
    const msg = JSON.stringify(data);
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(msg);
        }
    });
}

function formatArg(arg: unknown): string {
    if (typeof arg === 'string') return arg;
    return util.inspect(arg, { depth: 4, breakLength: Infinity });
}

// Intercept console output and broadcast to all WebSocket clients
const originalLog = console.log;
console.log = (...args: unknown[]) => {
    const message = args.map(formatArg).join(' ');
    const formatted = `[${new Date().toISOString()}] ${message}\n`;
    originalLog(...args);
    broadcast({ type: 'log', message: formatted });
};

const originalError = console.error;
console.error = (...args: unknown[]) => {
    const message = args.map(formatArg).join(' ');
    const formatted = `[${new Date().toISOString()}] ERROR: ${message}\n`;
    originalError(...args);
    broadcast({ type: 'log', message: formatted });
};

// On new client: send SDK status and game status immediately
wss.on('connection', (ws) => {
    const available = new ChromaNativeSDK().isDllAvailable();
    ws.send(JSON.stringify({ type: 'sdk-status', available }));
    const inGame = RunningState.getRunningGame().getValue();
    ws.send(JSON.stringify({ type: 'game-status', inGame }));
});

// Start main application logic
chromaLeague.run();

// Watch game state and broadcast changes to all clients
(async () => {
    while (true) {
        const inGame = await RunningState.getRunningGame().waitForChange();
        broadcast({ type: 'game-status', inGame });
    }
})();

process.stdout.write(`[${new Date().toISOString()}] Chroma League server started on ws://localhost:${PORT}\n`);

// Graceful shutdown on SIGINT / SIGTERM
async function shutdown(): Promise<void> {
    await chromaLeague.shutdown();
    wss.close();
    process.exit(0);
}

process.on('SIGINT', shutdown);
process.on('SIGTERM', shutdown);
