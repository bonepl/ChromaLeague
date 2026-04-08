import {getRetryingResponse} from '../rest/http/LeagueHttpClients.js';
import {RunningState} from '../state/RunningState.js';

const URL = 'https://127.0.0.1:2999/liveclientdata/activeplayername';

export async function fetchPlayerNameTask(): Promise<void> {
    try {
        RunningState.getGameState().playerRiotId = await getRetryingResponse<string>(URL);
    } catch (ex) {
        console.error('Error while fetching player name', ex);
        throw ex;
    }
}
