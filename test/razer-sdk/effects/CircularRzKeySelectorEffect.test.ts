import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../../src/razer-sdk/color/TransitionColor.js';
import {CircularRzKeySelector} from '../../../src/razer-sdk/sdk/CircularRzKeySelector.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

/**
 * Integration test for circular ripple effect on actual hardware.
 * Creates expanding ripple waves from the center key (H).
 */
describe('CircularRzKeySelectorEffect Integration', () => {
    test('testCircularRzKeySelectorAnimation', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const circularRzKeySelector = new CircularRzKeySelector(RzKey.RZKEY_H, 100);
            const transitionColor = new TransitionColor(StaticColor.RED, StaticColor.BLACK, 20);

            for (let i = 0; i < 30; i++) {
                const keys = circularRzKeySelector.getNextLayer();
                await sdk.createKeyboardEffect(new SimpleFrame(Array.from(keys), transitionColor));
                await new Promise(resolve => setTimeout(resolve, 200));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
