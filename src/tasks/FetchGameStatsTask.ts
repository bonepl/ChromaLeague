import {getNonBlockingResponse} from '../rest/http/LeagueHttpClients.js';
import type {GameStats} from '../rest/gamestats/GameStats.js';
import {RunningState} from '../state/RunningState.js';

const URL = 'https://127.0.0.1:2999/liveclientdata/gamestats';

export async function fetchGameStatsTask(): Promise<void> {
    const gameStats = await getNonBlockingResponse<GameStats>(URL);
    if (gameStats) {
        RunningState.getGameState().gameStats = gameStats;
    }
}
