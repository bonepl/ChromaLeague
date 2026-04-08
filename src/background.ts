/// <reference types="@overwolf/types" />

const SERVER_URL = 'ws://localhost:7891';
const RECONNECT_DELAY_MS = 2000;

let mainWindowId: string | null = null;
let lastSdkStatus: boolean | null = null;
let lastGameStatus: boolean | null = null;

function sendToWindow(msgId: string, content: unknown): void {
    if (mainWindowId) {
        overwolf.windows.sendMessage(mainWindowId, msgId, content, () => {});
    }
}

// -------------------------------------------------------------------
// Process Manager: auto-start Node.js sidecar when installed via .opk
// Falls back silently if plugin is unavailable (dev/sideload mode).
// -------------------------------------------------------------------
function tryStartServer(): void {
    overwolf.extensions.current.getExtraObject('process-manager-plugin', (pluginResult) => {
        if (!pluginResult.success || !pluginResult.object) {
            // Dev mode — server started manually via npm run dev:server
            return;
        }

        const plugin = pluginResult.object as {
            launchExe: (path: string, args: string, elevated: boolean, callback: (r: unknown) => void) => void;
        };

        overwolf.extensions.current.getManifest((manifestResult) => {
            if (!manifestResult.success) return;

            const uid = manifestResult.UID;
            const extPath = `${overwolf.io.paths.localAppData}\\Overwolf\\extensions\\${uid}`;
            const cmdPath = `${extPath}\\start-server.cmd`;

            plugin.launchExe('cmd.exe', `/C "${cmdPath}"`, false, () => {});
        });
    });
}

// -------------------------------------------------------------------
// WebSocket client — connects to the Node.js sidecar
// -------------------------------------------------------------------
function connectToServer(): void {
    const ws = new WebSocket(SERVER_URL);

    ws.onmessage = (event: MessageEvent) => {
        try {
            const msg = JSON.parse(event.data as string) as { type: string; message?: string; available?: boolean; inGame?: boolean };
            if (msg.type === 'log') {
                sendToWindow('log', msg.message);
            } else if (msg.type === 'sdk-status') {
                lastSdkStatus = msg.available ?? null;
                sendToWindow('chroma-sdk-status', msg.available);
            } else if (msg.type === 'game-status') {
                lastGameStatus = msg.inGame ?? null;
                sendToWindow('chroma-league-status', msg.inGame);
            }
        } catch {
            // ignore malformed messages
        }
    };

    ws.onclose = () => {
        setTimeout(connectToServer, RECONNECT_DELAY_MS);
    };

    ws.onerror = () => {
        // onclose fires after onerror — reconnect handled there
    };
}

// -------------------------------------------------------------------
// Overwolf window management
// -------------------------------------------------------------------
overwolf.windows.onMessageReceived.addListener((message) => {
    if (message.id === 'ready') {
        if (lastSdkStatus !== null) {
            sendToWindow('chroma-sdk-status', lastSdkStatus);
        }
        if (lastGameStatus !== null) {
            sendToWindow('chroma-league-status', lastGameStatus);
        }
    }
});

overwolf.windows.obtainDeclaredWindow('main', (result) => {
    if (result.success && result.window) {
        mainWindowId = result.window.id;
        overwolf.windows.restore(mainWindowId, () => {});
    }
});

overwolf.extensions.onAppLaunchTriggered.addListener(() => {
    if (mainWindowId) {
        overwolf.windows.restore(mainWindowId, () => {});
    }
});

// -------------------------------------------------------------------
// Boot sequence
// -------------------------------------------------------------------
tryStartServer();
connectToServer();
