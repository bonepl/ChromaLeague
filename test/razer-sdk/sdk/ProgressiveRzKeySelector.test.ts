import {describe, expect, test} from 'vitest';
import {
    ProgressiveRzKeySelector,
    ProgressiveRzKeySelectorBuilder
} from '../../../src/razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {RzKey} from '../../../src/razer-sdk/sdk/RzKey.js';

function joinSets(...sets: Set<RzKey>[]): Set<RzKey> {
    const result = new Set<RzKey>();
    for (const set of sets) {
        for (const key of set) {
            result.add(key);
        }
    }
    return result;
}

describe('ProgressiveRzKeySelectorTest', () => {
    test('testJoinSets', () => {
        //given
        const set1 = new Set([RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z]);
        const set2 = new Set([RzKey.RZKEY_Q, RzKey.RZKEY_R]);

        //when
        const actual = joinSets(set1, set2);

        //then
        expect(actual).toEqual(new Set([RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z, RzKey.RZKEY_R]));
    });

    test('testIllegalLengthException', () => {
        //given
        const testSet = [new Set([RzKey.RZKEY_ENTER])];

        //when
        expect(() => new ProgressiveRzKeySelector(testSet, -1)).toThrow();
        expect(() => new ProgressiveRzKeySelector(testSet, 0)).toThrow();
        expect(() => new ProgressiveRzKeySelector(testSet, 2)).toThrow();
    });

    test('testIllegalPartsException', () => {
        //when
        expect(() => new ProgressiveRzKeySelector(null as any)).toThrow();
        expect(() => new ProgressiveRzKeySelector([])).toThrow();
    });

    test('testLength1Progression', () => {
        //given
        const set1 = new Set([RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z]);
        const array2 = [RzKey.RZKEY_W, RzKey.RZKEY_S];
        const rzKey3 = RzKey.RZKEY_E;

        //when
        const progressiveRzKeySelector = new ProgressiveRzKeySelectorBuilder()
            .addPart(set1)
            .addPart(array2)
            .addPartFromKeys(rzKey3)
            .build();

        //then
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(new Set(array2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(new Set([rzKey3]));

        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(new Set(array2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(new Set([rzKey3]));
    });

    test('testLength2Progression', () => {
        //given
        const set1 = new Set([RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z]);
        const set2 = new Set([RzKey.RZKEY_W, RzKey.RZKEY_S]);
        const set3 = new Set([RzKey.RZKEY_E]);

        //when
        const progressiveRzKeySelector = new ProgressiveRzKeySelectorBuilder()
            .addPart(set1)
            .addPart(set2)
            .addPart(set3)
            .withLength(2)
            .build();

        //then
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set3);

        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set3);
    });

    test('testLength3Progression', () => {
        //given
        const set1 = new Set([RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z]);
        const set2 = new Set([RzKey.RZKEY_W, RzKey.RZKEY_S]);
        const set3 = new Set([RzKey.RZKEY_E]);

        //when
        const progressiveRzKeySelector = new ProgressiveRzKeySelector([set1, set2, set3], 3);

        //then
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set3);

        expect(progressiveRzKeySelector.getNextPart()).toEqual(set1);
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set1, set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(joinSets(set2, set3));
        expect(progressiveRzKeySelector.getNextPart()).toEqual(set3);
    });
});
