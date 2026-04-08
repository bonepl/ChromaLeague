import {ChromaNativeSDK} from '../razer-sdk/ChromaNativeSDK.js';
import {resetHttpClients} from '../rest/http/LeagueHttpClients.js';
import {RunningState} from '../state/RunningState.js';
import {fetchActivePlayerTask} from './FetchActivePlayerTask.js';
import {fetchGameStatsTask} from './FetchGameStatsTask.js';
import {fetchNewEventsTask} from './FetchNewEventsTask.js';
import {fetchPlayerListTask} from './FetchPlayerListTask.js';
import {fetchPlayerNameTask} from './FetchPlayerNameTask.js';
import {FixedDelayScheduler} from './FixedDelayScheduler.js';
import {GameLoader} from './GameLoader.js';
import {createRefreshMainHudTask} from './RefreshMainHudTask.js';

const PLAYER_LIST_FETCH_DELAY = 300;
const ACTIVE_PLAYER_FETCH_DELAY = 100;
const EVENTS_FETCH_DELAY = 300;
const MAIN_HUD_REFRESH_DELAY = 50;
const GAME_STATS_FETCH_DELAY = 300;

export class MainThreads {
    private readonly mainScheduler = new FixedDelayScheduler();
    private gameLoader: GameLoader | null = null;
    private readonly chromaNativeSDK: ChromaNativeSDK;
    private alive = true;

    constructor() {
        console.log('Loading game...');
        this.chromaNativeSDK = new ChromaNativeSDK();
    }

    async connect(): Promise<void> {
        await this.chromaNativeSDK.connect();
        // Start loading animation only after SDK is connected
        this.gameLoader = new GameLoader(this.chromaNativeSDK);
        this.mainScheduler.scheduleWithFixedDelay(fetchNewEventsTask, 0, EVENTS_FETCH_DELAY);
    }

    async initializeGameThreads(): Promise<void> {
        if (this.gameLoader) {
            this.gameLoader.close();
            this.gameLoader = null;
        }
        await fetchPlayerNameTask();
        console.log(`${RunningState.getGameState().playerRiotId} joined the game`);
        this.mainScheduler.scheduleWithFixedDelay(fetchGameStatsTask, 20, GAME_STATS_FETCH_DELAY);
        this.mainScheduler.scheduleWithFixedDelay(fetchPlayerListTask, 50, PLAYER_LIST_FETCH_DELAY);
        this.mainScheduler.scheduleWithFixedDelay(fetchActivePlayerTask, 50, ACTIVE_PLAYER_FETCH_DELAY);
        this.mainScheduler.scheduleWithFixedDelay(
            createRefreshMainHudTask(this.chromaNativeSDK), 150, MAIN_HUD_REFRESH_DELAY,
        );
    }

    async close(): Promise<void> {
        if (!this.alive) return;
        this.alive = false;
        RunningState.setRunningGame(false);
        this.gameLoader?.close();
        this.gameLoader = null;
        this.mainScheduler.shutdown();
        resetHttpClients();
        try {
            await this.chromaNativeSDK.close();
        } catch (ex) {
            console.warn('Exception while shutting down Chroma SDK', ex);
        }
        console.log('Player left the game, waiting for another...');
    }

    isAlive(): boolean {
        return this.alive;
    }
}
