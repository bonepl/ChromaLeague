import {describe, expect, test} from 'vitest';
import {StaticColor} from '../../../src/razer-sdk/color/StaticColor.js';

describe('StaticColorTest', () => {
    test('testColorCreation', () => {
        //given
        const color = new StaticColor(0, 123, 255);

        //then
        expect(color.red).toBe(0);
        expect(color.green).toBe(123);
        expect(color.blue).toBe(255);
    });

    test('testInvalidColors', () => {
        expect(() => new StaticColor(-1, 1, 1)).toThrow();
        expect(() => new StaticColor(256, 1, 1)).toThrow();
        expect(() => new StaticColor(1, -112345, 1)).toThrow();
        expect(() => new StaticColor(1, 500, 1)).toThrow();
        expect(() => new StaticColor(1, 1, -100)).toThrow();
        expect(() => new StaticColor(1, 1, 4689)).toThrow();
    });
});
