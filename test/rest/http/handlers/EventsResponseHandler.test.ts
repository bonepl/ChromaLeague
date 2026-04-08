import {describe, expect, test} from 'vitest';
import type {Event} from '../../../../src/rest/eventdata/Event.js';
import type {Events} from '../../../../src/rest/eventdata/Events.js';

describe('EventsResponseHandler', () => {
    test('testEventParsing', () => {
        //given
        const json: Events = {
            Events: [
                { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
                { EventID: 1, EventName: 'MinionsSpawning', EventTime: 5.06690979003906 },
                { EventID: 2, EventName: 'ChampionKill', EventTime: 7.1595916748047, KillerName: 'BooonE', VictimName: 'Warwick Bot', Assisters: [] },
            ],
        };

        //when
        const events = json.Events;

        //then
        expect(events).toBeDefined();
        verifyStandardEvent(events);
    });
});

export function verifyStandardEvent(events: Event[]): void {
    expect(events).toHaveLength(3);

    const event0 = events[0];
    expect(event0.EventID).toBe(0);
    expect(event0.EventName).toBe('GameStart');
    expect(event0.EventTime).toBe(0.0563616007566452);

    const event1 = events[1];
    expect(event1.EventID).toBe(1);
    expect(event1.EventName).toBe('MinionsSpawning');
    expect(event1.EventTime).toBe(5.06690979003906);

    const event2 = events[2];
    expect(event2.EventID).toBe(2);
    expect(event2.EventName).toBe('ChampionKill');
    expect(event2.EventTime).toBe(7.1595916748047);
}
