import {describe, expect, test} from 'vitest';
import type {ActivePlayer} from '../../../../src/rest/activeplayer/ActivePlayer.js';

describe('ActivePlayerResponseHandler', () => {
    test('testActivePlayerParsing', () => {
        //given
        const json = {
            championStats: {
                attackRange: 125.0,
                currentHealth: 255.59997,
                maxHealth: 655.5999755859375,
                resourceValue: 111.11111111111111,
                resourceMax: 222.222222222222222,
                resourceType: 'MANA',
            },
            currentGold: 123.45,
            level: 1,
            riotId: 'BonE#EUPL',
            riotIdGameName: 'BonE',
        };

        //when
        const activePlayer = json as ActivePlayer;

        //then
        expect(activePlayer).toBeDefined();
        verifyActivePlayer(activePlayer);
    });
});

export function verifyActivePlayer(activePlayer: ActivePlayer): void {
    expect(activePlayer).toBeDefined();
    expect(activePlayer.currentGold).toBe(123.45);
    expect(activePlayer.level).toBe(1);

    const championStats = activePlayer.championStats;
    expect(championStats.currentHealth).toBe(255.59997);
    expect(championStats.maxHealth).toBe(655.5999755859375);
    expect(championStats.resourceValue).toBe(111.11111111111111);
    expect(championStats.resourceMax).toBe(222.222222222222222);
    expect(championStats.resourceType).toBe('MANA');
}
