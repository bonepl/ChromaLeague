import {afterEach, beforeEach, describe, expect, test, vi} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';

vi.mock('../../src/rest/http/LeagueHttpClients.js', () => ({
    getNonBlockingResponse: vi.fn().mockResolvedValue('BooonE'),
    getRetryingResponse: vi.fn().mockResolvedValue('BooonE'),
    setNonBlockingLeagueHttpClient: vi.fn(),
    setRetryingLeagueHttpClient: vi.fn(),
}));

describe('FetchPlayerNameTask', () => {
    beforeEach(() => {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
    });

    afterEach(() => {
        RunningState.setRunningGame(false);
    });

    test('testPlayerNameFetch', async () => {
        //given & when
        const { fetchPlayerNameTask } = await import('../../src/tasks/FetchPlayerNameTask.js');
        await fetchPlayerNameTask();

        //then
        expect(RunningState.getGameState().playerRiotId).toBe('BooonE');
    });
});
