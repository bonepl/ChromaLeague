import {describe, expect, test} from 'vitest';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {Color} from '../../../src/razer-sdk/color/Color.js';
import {getRzKeyValues, RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

describe('SimpleFrameTest', () => {
    test('testEmptyFrameCreation', () => {
        //given
        const simpleFrame = new SimpleFrame();

        //when
        const actualFrame = simpleFrame.pollFrame();

        //then
        expect(actualFrame.getKeysToColors().size).toBe(0);
        expect(simpleFrame.hasFrame()).toBe(false);
        expect(() => simpleFrame.pollFrame()).toThrow();
    });

    test('testStartWithFrame', () => {
        //when
        const simpleFrame = new SimpleFrame();

        //then
        expect(simpleFrame.hasFrame()).toBe(true);
    });

    test('testFullColor', () => {
        //given
        const simpleFrame = new SimpleFrame(StaticColor.BLUE);

        //when
        const actualKeysToColors = simpleFrame.pollFrame().getKeysToColors();

        //then
        expect(simpleFrame.hasFrame()).toBe(false);
        expect(actualKeysToColors.size).toBe(getRzKeyValues().length);
        for (const rzKey of getRzKeyValues()) {
            expect(actualKeysToColors.get(rzKey)).toBe(StaticColor.BLUE);
        }
        expect(() => simpleFrame.pollFrame()).toThrow();
    });

    test('testOneKey', () => {
        //given
        const simpleFrame = new SimpleFrame(RzKey.RZKEY_1, StaticColor.RED);

        //when
        const actualKeysToColors = simpleFrame.pollFrame().getKeysToColors();

        //then
        expect(simpleFrame.hasFrame()).toBe(false);
        expect(actualKeysToColors.size).toBe(1);
        expect(actualKeysToColors.get(RzKey.RZKEY_1)).toBe(StaticColor.RED);
        expect(() => simpleFrame.pollFrame()).toThrow();
    });

    test('testMultipleKeys', () => {
        //given
        const rzKeys = [RzKey.RZKEY_2, RzKey.RZKEY_3];
        const simpleFrame = new SimpleFrame(rzKeys, StaticColor.YELLOW);

        //when
        const actualKeysToColors = simpleFrame.pollFrame().getKeysToColors();

        //then
        expect(simpleFrame.hasFrame()).toBe(false);
        expect(actualKeysToColors.size).toBe(2);
        for (const rzKey of rzKeys) {
            expect(actualKeysToColors.get(rzKey)).toBe(StaticColor.YELLOW);
        }
        expect(() => simpleFrame.pollFrame()).toThrow();
    });

    test('testKeyMap', () => {
        //given
        const expectedKeysToColorMap = new Map<RzKey, Color>([
            [RzKey.RZKEY_Q, StaticColor.GREEN],
            [RzKey.RZKEY_W, StaticColor.CYAN],
            [RzKey.RZKEY_E, StaticColor.CYAN],
        ]);
        const simpleFrame = new SimpleFrame(expectedKeysToColorMap);

        //when
        const actualKeysToColors = simpleFrame.pollFrame().getKeysToColors();

        //then
        expect(simpleFrame.hasFrame()).toBe(false);
        expect(actualKeysToColors.size).toBe(3);
        expect(actualKeysToColors.get(RzKey.RZKEY_Q)).toBe(StaticColor.GREEN);
        expect(actualKeysToColors.get(RzKey.RZKEY_W)).toBe(StaticColor.CYAN);
        expect(actualKeysToColors.get(RzKey.RZKEY_E)).toBe(StaticColor.CYAN);
        expect(() => simpleFrame.pollFrame()).toThrow();
    });
});
