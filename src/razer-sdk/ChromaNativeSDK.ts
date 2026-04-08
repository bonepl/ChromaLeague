import * as fs from 'node:fs';
import type {IFrame} from './animation/IFrame.js';
import {RZ_KEY_POSITIONS} from './sdk/RzKey.js';

const KEYBOARD_ROWS = 6;
const KEYBOARD_COLUMNS = 22;

const DLL_PATHS = [
    'C:\\Program Files\\Razer Chroma SDK\\bin\\RzChromaSDK64.dll',
    'C:\\Windows\\System32\\RzChromaSDK64.dll',
    'C:\\Windows\\SysWOW64\\RzChromaSDK64.dll'
];
const INIT_DELAY_MS = 2000;

// Effect types
const CHROMA_NONE = 0;
const CHROMA_CUSTOM = 2;

/**
 * Native DLL implementation for Razer Chroma SDK.
 * Uses koffi for FFI bindings to RzChromaSDK64.dll.
 *
 * Usage:
 *   const sdk = new ChromaNativeSDK();
 *   await sdk.connect();
 *   await sdk.createKeyboardEffect(someFrame);
 *   await sdk.close();
 */
type KoffiFunc = (...args: unknown[]) => number;
type KoffiLib = { func(name: string, ret: string, args: string[]): KoffiFunc };

export class ChromaNativeSDK {
    private readonly buffer = Buffer.alloc(KEYBOARD_ROWS * KEYBOARD_COLUMNS * 4);
    private lib: KoffiLib | null = null;
    private Init: KoffiFunc | null = null;
    private UnInit: KoffiFunc | null = null;
    private CreateKeyboardEffect: KoffiFunc | null = null;
    private initialized = false;
    private droppedFrames = 0;

    /**
     * Returns true if the Razer Chroma SDK DLL is found in one of the expected locations.
     */
    isDllAvailable(): boolean {
        return DLL_PATHS.some(path => fs.existsSync(path));
    }

    /**
     * Initialize connection to Chroma-enabled Razer device.
     */
    async connect(): Promise<void> {
        if (this.initialized) return;
        const foundPath = DLL_PATHS.find(path => fs.existsSync(path));

        if (!foundPath) {
            throw new Error(`Razer Chroma SDK DLL not found in common locations: ${DLL_PATHS.join(', ')}`);
        }

        // Dynamic import of koffi to avoid issues if not installed
        const koffiModule = await import('koffi');
        const koffi = koffiModule.default;

        this.lib = koffi.load(foundPath) as KoffiLib;
        this.Init = this.lib.func('Init', 'long', []);
        this.UnInit = this.lib.func('UnInit', 'long', []);
        this.CreateKeyboardEffect = this.lib.func('CreateKeyboardEffect', 'long', [
            'int', 'void *', 'void *'
        ]);

        const result = this.Init();
        if (result !== 0) {
            throw new Error(`Failed to initialize Razer Chroma SDK: error ${result}`);
        }

        console.log('Initialized Razer Chroma connection');

        // Wait for async SDK initialization
        await new Promise(r => setTimeout(r, INIT_DELAY_MS));
        this.initialized = true;
    }

    /**
     * Apply a keyboard effect from the given IFrame.
     * The effect is sent immediately to the Razer device.
     */
    async createKeyboardEffect(frame: IFrame): Promise<void> {
        const frameData = frame.pollFrame();

        if (!this.initialized) {
            this.droppedFrames++;
            if (this.droppedFrames % 100 === 1) {
                console.warn(`Chroma SDK not initialized, dropped ${this.droppedFrames} frame(s)`);
            }
            return;
        }
        this.droppedFrames = 0;
        const keysToColors = frameData.getKeysToColors();

        // Reset buffer
        this.buffer.fill(0);

        // Fill in colors
        keysToColors.forEach((color, key) => {
            const pos = RZ_KEY_POSITIONS.get(key);
            if (pos) {
                const offset = (pos.row * KEYBOARD_COLUMNS + pos.col) * 4;
                this.buffer.writeUInt32LE(color.getColor().forChroma(), offset);
            }
        });

        const result = this.CreateKeyboardEffect!(CHROMA_CUSTOM, this.buffer, null);
        if (result !== 0) {
            console.error(`Error from Razer SDK: ${result}`);
        }
    }

    /**
     * Turn off all keyboard lights.
     */
    async clearKeyboard(): Promise<void> {
        if (!this.initialized) {
            return;
        }
        this.CreateKeyboardEffect!(CHROMA_NONE, null, null);
    }

    /**
     * Close and reconnect to Chroma-enabled Razer device.
     */
    async reconnect(): Promise<void> {
        await this.close();
        await this.connect();
    }

    /**
     * Close and disconnect from Chroma-enabled Razer device.
     */
    async close(): Promise<void> {
        if (this.initialized && this.UnInit) {
            await this.clearKeyboard();
            await new Promise(r => setTimeout(r, 100));
            this.UnInit!();
            this.initialized = false;
            console.log('Razer Chroma session ended');
        }
    }
}
