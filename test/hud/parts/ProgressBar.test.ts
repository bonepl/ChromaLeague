import {describe, expect, test} from 'vitest';
import {indexToFill, ProgressBar} from '../../../src/hud/parts/ProgressBar.js';
import {BLACKWIDOW_FIRST_ROW} from '../../../src/hud/PredefinedKeySets.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';

describe('ProgressBar Integration', () => {
    /** Integration test for ProgressBar with increasing percentage on actual hardware. */
    test('creates a progress bar with increasing percentage', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            for (let percent = 0; percent <= 100; percent += 5) {
                const bar = new ProgressBar(BLACKWIDOW_FIRST_ROW, percent, StaticColor.GREEN);
                await sdk.createKeyboardEffect(bar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);

    test('indexToFill returns full size at 100%', () => {
        expect(indexToFill(20, 100)).toBe(20);
    });

    test('indexToFill returns 0 at 0%', () => {
        expect(indexToFill(20, 0)).toBe(0);
    });

    test('indexToFill returns proportional value for mid percentages', () => {
        expect(indexToFill(20, 50)).toBe(10);
        expect(indexToFill(10, 30)).toBe(3);
    });

    test('indexToFill returns full size when percent exceeds 100', () => {
        expect(indexToFill(20, 150)).toBe(20);
    });
});
