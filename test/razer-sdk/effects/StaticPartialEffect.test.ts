import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

const SMILEY: RzKey[] = [
    RzKey.RZKEY_W, RzKey.RZKEY_3, RzKey.RZKEY_4, RzKey.RZKEY_R,
    RzKey.RZKEY_I, RzKey.RZKEY_9, RzKey.RZKEY_0, RzKey.RZKEY_P,
    RzKey.RZKEY_X, RzKey.RZKEY_SPACE, RzKey.RZKEY_COMA,
];

/**
 * Integration test for partial keyboard lighting on actual hardware.
 * Draws a smiley face pattern using selected keys.
 */
describe('StaticPartialEffect Integration', () => {
    test('testPartialStaticEffect', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            await sdk.createKeyboardEffect(new SimpleFrame(SMILEY, StaticColor.YELLOW));
            await new Promise(resolve => setTimeout(resolve, 2000));
        } finally {
            await sdk.close();
        }
    }, 30000);
});
