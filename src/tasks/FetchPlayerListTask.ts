import {getNonBlockingResponse} from '../rest/http/LeagueHttpClients.js';
import type {Player} from '../rest/playerlist/Player.js';
import {PlayerList} from '../rest/playerlist/PlayerList.js';
import {RunningState} from '../state/RunningState.js';

const URL = 'https://127.0.0.1:2999/liveclientdata/playerlist';

export async function fetchPlayerListTask(): Promise<void> {
    const players = await getNonBlockingResponse<Player[]>(URL);
    if (players) {
        RunningState.getGameState().playerList = new PlayerList(players);
    }
}
