import {describe, test} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {ProgressiveRzKeySelector} from '../../../src/razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {getColumn, RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../../src/razer-sdk/sdk/RzKeySelector.js';

/**
 * Integration test for progressive column selector effect on actual hardware.
 * Creates a sliding window of columns moving across the keyboard.
 */
describe('ProgressiveRzKeySelectorEffect Integration', () => {
    test('testProgressiveRzKeySelectorAnimation', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            const startCol = getColumn(RzKey.RZKEY_TILDE);
            const endCol = getColumn(RzKey.RZKEY_BACKSLASH);
            const parts: Set<RzKey>[] = [];
            for (let col = startCol; col <= endCol; col++) {
                parts.push(new RzKeySelector()
                    .withColumn(col)
                    .withRowBetween(RzKey.RZKEY_TILDE, RzKey.RZKEY_LCTRL)
                    .asSet());
            }
            const progressiveRzKeySelector = new ProgressiveRzKeySelector(parts, 3);

            for (let i = 0; i < 60; i++) {
                const nextPart = progressiveRzKeySelector.getNextPart();
                await sdk.createKeyboardEffect(new SimpleFrame([...nextPart], StaticColor.WHITE));
                await new Promise(resolve => setTimeout(resolve, 100));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
