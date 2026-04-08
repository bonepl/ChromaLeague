import {describe, expect, test} from 'vitest';
import {Animation} from '../../../src/razer-sdk/animation/Animation.js';
import {AnimatedFrame} from '../../../src/razer-sdk/animation/AnimatedFrame.js';
import {LayeredFrame} from '../../../src/razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {getRzKeyValues, RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

describe('AnimationTest', () => {
    test('testEmptyFrameCreation', () => {
        //given
        const animation = new Animation();

        //then
        expect(animation.hasFrame()).toBe(false);
        expect(() => animation.pollFrame()).toThrow();
    });

    test('testAddingFrameToFront', () => {
        //given
        const animation = new Animation();
        animation.addToBack(new SimpleFrame(StaticColor.YELLOW));

        //when
        animation.addToFront(new SimpleFrame(StaticColor.RED));

        //then
        expect(animation.hasFrame()).toBe(true);
        const actualKeysToColors = animation.pollFrame().getKeysToColors();
        expect([...actualKeysToColors.values()].every(color => color === StaticColor.RED)).toBe(true);
        expect(() => animation.pollFrame()).toThrow();
    });

    test('testAddingFrameToBack', () => {
        //given
        const animation = new Animation();
        animation.addToFront(new SimpleFrame(StaticColor.RED));

        //when
        animation.addToBack(new SimpleFrame(StaticColor.YELLOW));

        //then
        expect(animation.hasFrame()).toBe(true);
        const actualKeysToColors = animation.pollFrame().getKeysToColors();
        expect([...actualKeysToColors.values()].every(color => color === StaticColor.RED)).toBe(true);
        expect(() => animation.pollFrame()).toThrow();
    });

    test('testAnimation', () => {
        //given
        const animation = new Animation();

        const layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(StaticColor.GREEN));
        animation.addToBack(layeredFrame);

        const animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(new SimpleFrame(RzKey.RZKEY_ENTER, StaticColor.RED));
        animatedFrame.addAnimationFrame(new SimpleFrame(RzKey.RZKEY_ENTER, StaticColor.BLUE));
        animation.addToFront(animatedFrame);

        //when 1st frame
        const actualKeysToColors = animation.pollFrame().getKeysToColors();

        //then
        expect(animation.hasFrame()).toBe(true);
        expect(actualKeysToColors.get(RzKey.RZKEY_ENTER)).toBe(StaticColor.RED);
        expect([...actualKeysToColors.entries()]
            .filter(([key]) => key !== RzKey.RZKEY_ENTER)
            .map(([, value]) => value)
            .every(color => color === StaticColor.GREEN)).toBe(true);
        expect(actualKeysToColors.size).toBe(getRzKeyValues().length);

        //when 2nd frame
        const actualKeysToColors2 = animation.pollFrame().getKeysToColors();

        //then
        expect(animation.hasFrame()).toBe(false);
        expect(actualKeysToColors2.get(RzKey.RZKEY_ENTER)).toBe(StaticColor.BLUE);
        expect(actualKeysToColors2.size).toBe(1);
        expect(() => animation.pollFrame()).toThrow();
    });
});
