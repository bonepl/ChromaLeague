import {getNonBlockingResponse} from '../rest/http/LeagueHttpClients.js';
import type {ActivePlayer} from '../rest/activeplayer/ActivePlayer.js';
import {RunningState} from '../state/RunningState.js';

const URL = 'https://127.0.0.1:2999/liveclientdata/activeplayer';

export async function fetchActivePlayerTask(): Promise<void> {
    const activePlayer = await getNonBlockingResponse<ActivePlayer>(URL);
    if (activePlayer) {
        RunningState.getGameState().activePlayer = activePlayer;
    }
}
