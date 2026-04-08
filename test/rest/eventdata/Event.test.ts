import {describe, expect, test} from 'vitest';
import type {Event} from '../../../src/rest/eventdata/Event.js';

describe('EventTest', () => {
    test('testArtificialEventRead', () => {
        //given
        const json: Event = {
            Assisters: ['Teammate 1', 'Teammate 2'],
            DragonType: 'Fire',
            EventID: 7,
            EventName: 'DragonKill',
            EventTime: 933.6826782226563,
            KillerName: 'BooonE',
            VictimName: 'Noob',
            Result: 'Win',
        };

        //then
        expect(json.EventID).toBe(7);
        expect(json.EventName).toBe('DragonKill');
        expect(json.EventTime).toBe(933.6826782226563);
        expect(json.DragonType).toBe('Fire');
        expect(json.KillerName).toBe('BooonE');
        expect(json.VictimName).toBe('Noob');
        expect(json.Result).toBe('Win');
        expect(json.Assisters).toContain('Teammate 1');
        expect(json.Assisters).toContain('Teammate 2');
    });
});
