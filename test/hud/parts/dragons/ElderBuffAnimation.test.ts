import {describe, test} from 'vitest';
import {ElderBuffAnimation} from '../../../../src/hud/parts/dragons/ElderBuffAnimation.js';
import {ChromaNativeSDK} from '../../../../src/razer-sdk/ChromaNativeSDK.js';

describe('ElderBuffAnimation Integration', () => {
    /** Integration test for ElderBuffAnimation on actual hardware. */
    test('produces frames for elder buff animation', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const animation = new ElderBuffAnimation();
            for (let i = 0; i < 60; i++) {
                await sdk.createKeyboardEffect(animation);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
