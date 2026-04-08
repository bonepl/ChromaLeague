import {describe, expect, test} from 'vitest';
import {LayeredFrame} from '../../../src/razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

function createTestLayer(): LayeredFrame {
    const layerOne = new SimpleFrame([RzKey.RZKEY_1, RzKey.RZKEY_2, RzKey.RZKEY_3], StaticColor.BLUE);
    const layerTwo = new SimpleFrame(RzKey.RZKEY_2, StaticColor.RED);
    const transparentLayer = new SimpleFrame([RzKey.RZKEY_1, RzKey.RZKEY_2, RzKey.RZKEY_3, RzKey.RZKEY_4], StaticColor.NONE);

    //when
    const layeredFrame = new LayeredFrame();
    layeredFrame.addFrame(layerOne);
    layeredFrame.addFrame(layerTwo);
    layeredFrame.addFrame(transparentLayer);
    return layeredFrame;
}

describe('LayeredFrameTest', () => {
    test('testEmptyFrameCreation', () => {
        //given
        const layeredFrame = new LayeredFrame();

        //when
        const actualFrame = layeredFrame.pollFrame();

        //then
        expect(actualFrame.getKeysToColors().size).toBe(0);
        expect(layeredFrame.hasFrame()).toBe(false);
        expect(() => layeredFrame.pollFrame()).toThrow();
    });

    test('testStartWithFrame', () => {
        //when
        const layeredFrame = new LayeredFrame();

        //then
        expect(layeredFrame.hasFrame()).toBe(true);
    });

    test('testLayerCreation', () => {
        //given
        const layeredFrame = createTestLayer();
        const actualColorMap = layeredFrame.pollFrame().getKeysToColors();

        //then
        expect(actualColorMap.get(RzKey.RZKEY_1)).toBe(StaticColor.BLUE);
        expect(actualColorMap.get(RzKey.RZKEY_3)).toBe(StaticColor.BLUE);
        expect(actualColorMap.get(RzKey.RZKEY_2)).toBe(StaticColor.RED);
        expect(actualColorMap.size).toBe(3);
        expect(layeredFrame.hasFrame()).toBe(false);
        expect(() => layeredFrame.pollFrame()).toThrow();
    });
});
