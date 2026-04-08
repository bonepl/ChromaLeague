import {describe, expect, test} from 'vitest';
import {AnimatedFrame} from '../../../src/razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../../src/razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';
import {Color} from '../../../src/razer-sdk/color/Color.js';

function assertHasFrameAndMatches(animatedFrame: AnimatedFrame, expectedColor: Color): void {
    expect(animatedFrame.hasFrame()).toBe(true);
    const values = [...animatedFrame.pollFrame().getKeysToColors().values()];
    expect(values.every(color => color === expectedColor)).toBe(true);
}

describe('AnimatedFrameTest', () => {
    test('testEmptyFrameCreation', () => {
        //given
        const animatedFrame = new AnimatedFrame();

        //then
        expect(() => animatedFrame.pollFrame()).toThrow();
    });

    test('testStartWithoutFrame', () => {
        //when
        const animatedFrame = new AnimatedFrame();

        //then
        expect(animatedFrame.hasFrame()).toBe(false);
    });

    test('testWrongAmountOfAnimationFrames', () => {
        //given
        const animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(0, new SimpleFrame(StaticColor.RED));
        animatedFrame.addAnimationFrame(-2, new SimpleFrame(StaticColor.RED));

        //then
        expect(animatedFrame.hasFrame()).toBe(false);
        expect(() => animatedFrame.pollFrame()).toThrow();
    });

    test('testHasFrames', () => {
        //given
        const animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(StaticColor.RED));
        animatedFrame.addAnimationFrame(2, new SimpleFrame(StaticColor.BLUE));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.RED);
        assertHasFrameAndMatches(animatedFrame, StaticColor.BLUE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.BLUE);
        expect(animatedFrame.hasFrame()).toBe(false);
        expect(() => animatedFrame.pollFrame()).toThrow();
    });

    test('testSingleFrameAdd', () => {
        //given
        const animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(StaticColor.BROWN));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.BROWN);
        expect(animatedFrame.hasFrame()).toBe(false);
        expect(() => animatedFrame.pollFrame()).toThrow();
    });

    test('testMultipleFrameAdd', () => {
        //given
        const animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(2, new SimpleFrame(StaticColor.ORANGE));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.ORANGE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.ORANGE);
        expect(animatedFrame.hasFrame()).toBe(false);
        expect(() => animatedFrame.pollFrame()).toThrow();
    });

    test('testAnimationStop', () => {
        //given
        const animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(5, new SimpleFrame(StaticColor.PURPLE));
        assertHasFrameAndMatches(animatedFrame, StaticColor.PURPLE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.PURPLE);

        //when
        animatedFrame.clearFrames();

        //then
        expect(animatedFrame.hasFrame()).toBe(false);
        expect(() => animatedFrame.pollFrame()).toThrow();
    });
});
