import {LoadingAnimation} from '../hud/animations/LoadingAnimation.js';
import type {ChromaNativeSDK} from '../razer-sdk/ChromaNativeSDK.js';
import {FixedDelayScheduler} from './FixedDelayScheduler.js';
import {createGameLoadingTask} from './GameLoadingTask.js';

const GAME_LOADING_REFRESH_DELAY = 50;

export class GameLoader {
    private readonly scheduler = new FixedDelayScheduler();

    constructor(chromaNativeSDK: ChromaNativeSDK) {
        this.scheduler.scheduleWithFixedDelay(
            createGameLoadingTask(chromaNativeSDK, new LoadingAnimation()),
            0,
            GAME_LOADING_REFRESH_DELAY,
        );
    }

    close(): void {
        this.scheduler.shutdown();
    }
}
