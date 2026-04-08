import {MainHud} from '../hud/MainHud.js';
import {ActivePlayer} from '../rest/activeplayer/ActivePlayer.js';
import {GameStats} from '../rest/gamestats/GameStats.js';
import {PlayerList} from '../rest/playerlist/PlayerList.js';
import {EventData} from './EventData.js';

export class GameState {
    activePlayer: ActivePlayer | null = null;
    playerList: PlayerList | null = null;
    gameStats: GameStats | null = null;
    readonly eventData = new EventData();
    playerRiotId: string | null = null;
    private _mainHud: MainHud | null = null;

    get mainHud(): MainHud {
        if (this._mainHud === null) {
            this._mainHud = new MainHud();
        }
        return this._mainHud;
    }
}
