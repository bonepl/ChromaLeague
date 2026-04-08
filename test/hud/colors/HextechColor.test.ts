import {describe, test} from 'vitest';
import {HextechColor} from '../../../src/hud/colors/HextechColor.js';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {getRzKeyValues} from '../../../src/razer-sdk/sdk/RzKey.js';

/** Integration test for hextech color animation on actual hardware. */
describe('Hextech Color Integration', () => {
    test('displays hextech color animation on keyboard', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const allKeys = getRzKeyValues();
            const keyColors = new Map(allKeys.map(key => [key, new HextechColor()]));

            for (let i = 0; i < 200; i++) {
                const frameMap = new Map();
                keyColors.forEach((color, key) => {
                    frameMap.set(key, color.getColor());
                });
                await sdk.createKeyboardEffect(new SimpleFrame(frameMap));
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
