import {describe, expect, test} from 'vitest';
import {RzKeyJoiner} from '../../../src/razer-sdk/sdk/RzKeyJoiner.js';
import {RzKeySelector} from '../../../src/razer-sdk/sdk/RzKeySelector.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

describe('RzKeyJoinerTest', () => {
    test('testSimpleJoin', () => {
        //when
        const rzKeys = new RzKeyJoiner()
            .withKeys(RzKey.RZKEY_A).withKeys(RzKey.RZKEY_B).withKeys(RzKey.RZKEY_A).join();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(2);
        expect(rzKeys.has(RzKey.RZKEY_A)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_B)).toBe(true);
    });

    test('testArrayJoin', () => {
        //when
        const rzKeys = new RzKeyJoiner()
            .withKeys(RzKey.RZKEY_A, RzKey.RZKEY_B).withKeys(RzKey.RZKEY_A, RzKey.RZKEY_C).join();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(3);
        expect(rzKeys.has(RzKey.RZKEY_A)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_B)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_C)).toBe(true);
    });

    test('testEmptyJoins', () => {
        //when
        const rzKeys = new RzKeyJoiner().withKeys()
            .withCollection([])
            .withCollection(new Set<RzKey>()).join();

        //then
        expect(rzKeys.size).toBe(0);
    });

    test('testCollectionJoin', () => {
        //when
        const rzkeys1 = [RzKey.RZKEY_A, RzKey.RZKEY_A];
        const rzKeys2 = new Set([RzKey.RZKEY_B]);
        const rzKeys3 = [RzKey.RZKEY_B];

        //when
        const rzKeys = new RzKeyJoiner()
            .withCollection(rzkeys1).withCollection(rzKeys2).withCollection(rzKeys3).join();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(2);
        expect(rzKeys.has(RzKey.RZKEY_A)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_B)).toBe(true);
    });

    test('testKeySelectorJoin', () => {
        //when
        const rzKeySelector = new RzKeySelector()
            .withRowOf(RzKey.RZKEY_1).withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2);
        const rzKeySelector2 = new RzKeySelector()
            .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q).withColumnOf(RzKey.RZKEY_1);

        //then
        const rzKeys = new RzKeyJoiner()
            .withSelector(rzKeySelector).withSelector(rzKeySelector2).join();

        //then
        expect(rzKeys.size).not.toBe(0);
        expect(rzKeys.size).toBe(3);
        expect(rzKeys.has(RzKey.RZKEY_1)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_2)).toBe(true);
        expect(rzKeys.has(RzKey.RZKEY_Q)).toBe(true);
    });
});
