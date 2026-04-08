import {describe, test} from 'vitest';
import {MountainColor} from '../../../src/hud/colors/MountainColor.js';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {getRzKeyValues} from '../../../src/razer-sdk/sdk/RzKey.js';

/** Integration test for mountain color animation on actual hardware. */
describe('Mountain Color Integration', () => {
    test('displays mountain color animation on keyboard', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const allKeys = getRzKeyValues();
            const keyColors = new Map(allKeys.map(key => [key, new MountainColor()]));

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
