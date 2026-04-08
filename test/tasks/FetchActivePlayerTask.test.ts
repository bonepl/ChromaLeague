import {beforeEach, describe, expect, test, vi} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';
import type {ActivePlayer} from '../../src/rest/activeplayer/ActivePlayer.js';

const activePlayerJson: ActivePlayer = {
    riotId: 'BonE#EUPL',
    riotIdGameName: 'BonE',
    currentGold: 123.45,
    level: 1,
    championStats: {
        attackRange: 125.0,
        currentHealth: 255.59997,
        maxHealth: 655.5999755859375,
        resourceMax: 222.222222222222222,
        resourceValue: 111.11111111111111,
        resourceType: 'MANA',
    },
};

vi.mock('../../src/rest/http/LeagueHttpClients.js', () => ({
    getNonBlockingResponse: vi.fn().mockResolvedValue(activePlayerJson),
    getRetryingResponse: vi.fn().mockResolvedValue(activePlayerJson),
    setNonBlockingLeagueHttpClient: vi.fn(),
    setRetryingLeagueHttpClient: vi.fn(),
}));

describe('FetchActivePlayerTask', () => {
    beforeEach(() => {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
    });

    test('testActivePlayerParsing', async () => {
        //given & when
        const { fetchActivePlayerTask } = await import('../../src/tasks/FetchActivePlayerTask.js');
        await fetchActivePlayerTask();

        //then
        const activePlayer = RunningState.getGameState().activePlayer!;
        expect(activePlayer).toBeDefined();
        expect(activePlayer.currentGold).toBe(123.45);
        expect(activePlayer.level).toBe(1);
        expect(activePlayer.championStats.currentHealth).toBe(255.59997);
        expect(activePlayer.championStats.maxHealth).toBe(655.5999755859375);
        expect(activePlayer.championStats.resourceValue).toBe(111.11111111111111);
        expect(activePlayer.championStats.resourceMax).toBe(222.222222222222222);
        expect(activePlayer.championStats.resourceType).toBe('MANA');
    });
});
