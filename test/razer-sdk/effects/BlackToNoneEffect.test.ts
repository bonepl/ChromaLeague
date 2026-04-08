import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {AnimatedFrame} from '../../../src/razer-sdk/animation/AnimatedFrame.js';
import {Animation} from '../../../src/razer-sdk/animation/Animation.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {Color} from '../../../src/razer-sdk/color/Color.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

const FIRST_LETTERS: RzKey[] = [
    RzKey.RZKEY_Q, RzKey.RZKEY_W, RzKey.RZKEY_E, RzKey.RZKEY_R,
    RzKey.RZKEY_T, RzKey.RZKEY_Y, RzKey.RZKEY_U,
];

function createMovingBlackPixel(color: Color): AnimatedFrame {
    const movingBlackPixel = new AnimatedFrame();
    for (const rzKey of FIRST_LETTERS) {
        movingBlackPixel.addAnimationFrame(2, new SimpleFrame(rzKey, color));
    }
    return movingBlackPixel;
}

/**
 * Integration test comparing opaque black vs transparent black on actual hardware.
 * Demonstrates layering behavior with BLACK (overwrites) vs NONE (transparent).
 */
describe('BlackToNoneEffect Integration', () => {
    test('testNonTransparentBlack', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const animation = new Animation();
            animation.addToFront(createMovingBlackPixel(StaticColor.BLACK));

            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, StaticColor.CYAN));
                await sdk.createKeyboardEffect(animation);
                await new Promise(resolve => setTimeout(resolve, 100));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);

    test('testTransparentBlack', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const animation = new Animation();
            animation.addToFront(createMovingBlackPixel(StaticColor.NONE));

            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, StaticColor.CYAN));
                await sdk.createKeyboardEffect(animation);
                await new Promise(resolve => setTimeout(resolve, 100));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
