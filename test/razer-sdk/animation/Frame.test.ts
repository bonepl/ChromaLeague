import {describe, expect, test} from 'vitest';
import {Frame} from '../../../src/razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {Color} from '../../../src/razer-sdk/color/Color.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

describe('FrameTest', () => {
    test('testCustomEffectCreation', () => {
        //given
        const inputMap = new Map<RzKey, Color>([
            [RzKey.RZKEY_ESC, StaticColor.BLUE],
            [RzKey.RZKEY_ENTER, StaticColor.WHITE],
        ]);

        //when
        const frame = new Frame(inputMap);
        const actualMap = frame.getKeysToColors();

        //then
        expect(actualMap.size).toBe(inputMap.size);
        expect(actualMap.get(RzKey.RZKEY_ESC)).toBe(StaticColor.BLUE);
        expect(actualMap.get(RzKey.RZKEY_ENTER)).toBe(StaticColor.WHITE);
    });
});
