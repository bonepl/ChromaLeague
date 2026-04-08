import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {AnimatedFrame} from '../../../src/razer-sdk/animation/AnimatedFrame.js';
import {LayeredFrame} from '../../../src/razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../../src/razer-sdk/color/Color.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {getRzKeyValues, RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

function createFramesFromEnum(from: number, to: number, color: Color): AnimatedFrame {
    const allKeys = getRzKeyValues();
    const keys = allKeys.slice(from, from + to);
    const animatedFrame = new AnimatedFrame();
    for (let i = 0; i < to; i++) {
        animatedFrame.addAnimationFrame(2, new SimpleFrame(keys.slice(0, i), color));
    }
    return animatedFrame;
}

/**
 * Integration test for layered frame composition on actual hardware.
 * Demonstrates multiple animation layers with background and overlay effects.
 */
describe('LayeredCustomKeyboardEffect Integration', () => {
    test('testLayeredKeyboardEffect', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const firstAnimatedFrame = createFramesFromEnum(7, 18, StaticColor.GREEN);
            const secondAnimatedFrame = createFramesFromEnum(0, 11, StaticColor.BLUE);

            for (let i = 0; i <= 100; i += 5) {
                const layeredFrame = new LayeredFrame();
                layeredFrame.addFrame(new SimpleFrame(new StaticColor(30, 30, 0)));
                layeredFrame.addFrame(firstAnimatedFrame);
                layeredFrame.addFrame(secondAnimatedFrame);
                if (i % 10 === 0) {
                    layeredFrame.addFrame(new SimpleFrame(RzKey.RZKEY_SPACE, StaticColor.RED));
                }

                await sdk.createKeyboardEffect(layeredFrame);
                await new Promise(resolve => setTimeout(resolve, 100));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
