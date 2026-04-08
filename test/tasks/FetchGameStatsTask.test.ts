import {beforeEach, describe, expect, test, vi} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';
import type {GameStats} from '../../src/rest/gamestats/GameStats.js';

const gameStatsJson: GameStats = {
    gameMode: 'CLASSIC',
    gameTime: 15.123456789,
    mapTerrain: 'Infernal',
};

vi.mock('../../src/rest/http/LeagueHttpClients.js', () => ({
    getNonBlockingResponse: vi.fn().mockResolvedValue(gameStatsJson),
    getRetryingResponse: vi.fn().mockResolvedValue(gameStatsJson),
    setNonBlockingLeagueHttpClient: vi.fn(),
    setRetryingLeagueHttpClient: vi.fn(),
}));

describe('FetchGameStatsTask', () => {
    beforeEach(() => {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
    });

    test('testGameStatsParsing', async () => {
        //given & when
        const { fetchGameStatsTask } = await import('../../src/tasks/FetchGameStatsTask.js');
        await fetchGameStatsTask();

        //then
        const gameStats = RunningState.getGameState().gameStats!;
        expect(gameStats).toBeDefined();
        expect(gameStats.gameMode).toBe('CLASSIC');
        expect(gameStats.gameTime).toBe(15.123456789);
        expect(gameStats.mapTerrain).toBe('Infernal');
    });
});
