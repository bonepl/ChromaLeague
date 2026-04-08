import {describe, expect, test} from 'vitest';
import {RzKeySelector} from '../../../src/razer-sdk/sdk/RzKeySelector.js';
import {getColumn, getRow, getRzKeyValues, RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

describe('RzKeySelectorTest', () => {
    test('testEmptySelection', () => {
        //when
        const rzKeysList = new RzKeySelector().asList();
        const rzKeysSet = new RzKeySelector().asSet();

        //then
        const allKeys = getRzKeyValues();
        expect(allKeys.every(k => rzKeysList.includes(k))).toBe(true);
        expect(rzKeysSet).toEqual(new Set(allKeys));
    });

    test('testSingleKeySelection', () => {
        //given
        const expected = RzKey.RZKEY_TILDE;

        //when
        const rzKeys = new RzKeySelector()
            .withRowOf(expected).withColumnOf(expected).asList();

        //then
        expect(rzKeys.length).not.toBe(0);
        expect(rzKeys[0]).toBe(expected);
    });

    test('testRangeSelection', () => {
        //given
        const expected = new Set([
            RzKey.RZKEY_INSERT, RzKey.RZKEY_HOME,
            RzKey.RZKEY_PAGEUP, RzKey.RZKEY_DELETE,
            RzKey.RZKEY_END, RzKey.RZKEY_PAGEDOWN,
        ]);

        //when
        const rzKeys = new RzKeySelector()
            .withColumnBetween(RzKey.RZKEY_INSERT, RzKey.RZKEY_PAGEUP)
            .withRowBetween(RzKey.RZKEY_INSERT, RzKey.RZKEY_DELETE)
            .asSet();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(6);
        for (const key of expected) {
            expect(rzKeys.has(key)).toBe(true);
        }
    });

    test('testRectangleSelection', () => {
        const expected = new Set([
            RzKey.RZKEY_NUMPAD7, RzKey.RZKEY_NUMPAD8,
            RzKey.RZKEY_NUMPAD4, RzKey.RZKEY_NUMPAD5,
            RzKey.RZKEY_NUMPAD1, RzKey.RZKEY_NUMPAD2,
        ]);

        //when
        const rzKeys = new RzKeySelector()
            .withRectangleBetween(RzKey.RZKEY_NUMPAD7, RzKey.RZKEY_NUMPAD2).asSet();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(6);
        for (const key of expected) {
            expect(rzKeys.has(key)).toBe(true);
        }
    });

    test('testColumnSort', () => {
        //when
        const rzKeys = new RzKeySelector()
            .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q)
            .withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2)
            .sortedByColumn().asList();

        //then
        expect(rzKeys.length).not.toBe(0);
        expect(rzKeys.length).toBe(4);
        expect(rzKeys.slice(0, 2).every(rzKey => getColumn(rzKey) === 2)).toBe(true);
        expect(rzKeys.slice(2, 4).every(rzKey => getColumn(rzKey) === 3)).toBe(true);
    });

    test('testRowSort', () => {
        //when
        const rzKeys = new RzKeySelector()
            .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q)
            .withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2)
            .sortedByRow().asList();

        //then
        expect(rzKeys.length).not.toBe(0);
        expect(rzKeys.length).toBe(4);
        expect(rzKeys.slice(0, 2).every(rzKey => getRow(rzKey) === 1)).toBe(true);
        expect(rzKeys.slice(2, 4).every(rzKey => getRow(rzKey) === 2)).toBe(true);
    });
});
