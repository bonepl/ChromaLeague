import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';

/**
 * Integration test for static color effects on actual hardware.
 * Displays full-keyboard static colors cycling through the spectrum.
 */
describe('StaticEffect Integration', () => {
    test('testStaticEffect', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const colors = [
                StaticColor.WHITE, StaticColor.GREEN, StaticColor.RED,
                StaticColor.BLACK, StaticColor.BLUE, StaticColor.YELLOW, StaticColor.CYAN,
            ];

            for (const color of colors) {
                await sdk.createKeyboardEffect(new SimpleFrame(color));
                await new Promise(resolve => setTimeout(resolve, 500));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
