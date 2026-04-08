import {ActivePlayer} from '../src/rest/activeplayer/ActivePlayer.js';
import {ChampionStats} from '../src/rest/activeplayer/ChampionStats.js';
import {GameStats} from '../src/rest/gamestats/GameStats.js';
import {Player} from '../src/rest/playerlist/Player.js';
import {PlayerList} from '../src/rest/playerlist/PlayerList.js';
import {Team} from '../src/rest/playerlist/Team.js';
import {RunningState} from '../src/state/RunningState.js';

export const PLAYER_RIOT_ID_GAME_NAME = 'BonE';
export const PLAYER_RIOT_ID = 'BonE#EUPL';

export class GameStateMocks {
    private mockChampionStats: ChampionStats = {
        attackRange: 0,
        currentHealth: 0,
        maxHealth: 0,
        resourceMax: 0,
        resourceValue: 0,
        resourceType: 'MANA',
    };

    private mockActivePlayer: ActivePlayer = {
        riotId: PLAYER_RIOT_ID,
        riotIdGameName: PLAYER_RIOT_ID_GAME_NAME,
        currentGold: 0,
        level: 1,
        championStats: this.mockChampionStats,
    };

    constructor() {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
        RunningState.getGameState().playerRiotId = PLAYER_RIOT_ID;
    }

    championStats(): ChampionStats {
        RunningState.getGameState().activePlayer = this.mockActivePlayer;
        return this.mockChampionStats;
    }

    activePlayer(): ActivePlayer {
        RunningState.getGameState().activePlayer = this.mockActivePlayer;
        return this.mockActivePlayer;
    }

    playerList(): PlayerList {
        const player: Player = {
            riotId: PLAYER_RIOT_ID,
            riotIdGameName: PLAYER_RIOT_ID_GAME_NAME,
            team: Team.ORDER,
            championName: 'TestChamp',
            respawnTimer: 0,
            isDead: false,
        };
        const pl = new PlayerList([player]);
        RunningState.getGameState().playerList = pl;
        return pl;
    }

    gameStats(): GameStats {
        const gs: GameStats = { gameMode: 'CLASSIC', gameTime: 0, mapTerrain: 'Default' };
        RunningState.getGameState().gameStats = gs;
        return gs;
    }
}
