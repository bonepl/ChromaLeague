import {describe, expect, test} from 'vitest';
import {TransitionColor} from '../../../src/razer-sdk/color/TransitionColor.js';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';

describe('TransitionColorTest', () => {
    test('testTooFewStepsTransition', () => {
        expect(() => new TransitionColor(StaticColor.RED, StaticColor.GREEN, -1)).toThrow();
        expect(() => new TransitionColor(StaticColor.RED, StaticColor.GREEN, 0)).toThrow();
        expect(() => new TransitionColor(StaticColor.RED, StaticColor.GREEN, 1)).toThrow();
    });

    test('testTwoStepTransition', () => {
        const transitionColor = new TransitionColor(StaticColor.RED, StaticColor.GREEN, 2);
        expect(transitionColor.getColor()).toEqual(StaticColor.RED);
        expect(transitionColor.transitionFinished()).toBe(false);
        expect(transitionColor.getColor()).toEqual(StaticColor.GREEN);
        expect(transitionColor.transitionFinished()).toBe(true);
    });

    test('testThreeStepTransition', () => {
        const transitionColor = new TransitionColor(StaticColor.RED, StaticColor.GREEN, 3);
        expect(transitionColor.getColor()).toEqual(StaticColor.RED);
        expect(transitionColor.transitionFinished()).toBe(false);
        const midColor = transitionColor.getColor();
        expect(midColor.red).toBe(127);
        expect(midColor.green).toBe(127);
        expect(midColor.blue).toBe(0);
        expect(transitionColor.transitionFinished()).toBe(false);
        expect(transitionColor.getColor()).toEqual(StaticColor.GREEN);
        expect(transitionColor.transitionFinished()).toBe(true);
    });

    test('testSameColorTransition', () => {
        const transitionColor = new TransitionColor(StaticColor.YELLOW, StaticColor.YELLOW, 3);
        expect(transitionColor.getColor()).toEqual(StaticColor.YELLOW);
        expect(transitionColor.transitionFinished()).toBe(false);
        expect(transitionColor.getColor()).toEqual(StaticColor.YELLOW);
        expect(transitionColor.transitionFinished()).toBe(false);
        expect(transitionColor.getColor()).toEqual(StaticColor.YELLOW);
        expect(transitionColor.transitionFinished()).toBe(true);
    });
});
