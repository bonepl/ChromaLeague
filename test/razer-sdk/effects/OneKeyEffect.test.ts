import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {getRzKeyValues} from '../../../src/razer-sdk/sdk/RzKey.js';

/**
 * Integration test for individual key lighting on actual hardware.
 * Lights up each key one by one across the entire keyboard.
 */
describe('OneKeyEffect Integration', () => {
    test('testOneKeyEffect', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            for (const rzKey of getRzKeyValues()) {
                await sdk.createKeyboardEffect(new SimpleFrame(rzKey, StaticColor.RED));
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 60000);
});
