import type {ChromaNativeSDK} from '../razer-sdk/ChromaNativeSDK.js';
import {RunningState} from '../state/RunningState.js';
import {clearErrorSuppression, logErrorSuppressed} from './TaskErrorUtils.js';

const TASK_KEY = 'RefreshMainHudTask';

export function createRefreshMainHudTask(chromaNativeSDK: ChromaNativeSDK): () => Promise<void> {
    let consecutiveFailures = 0;
    return async () => {
        try {
            const gameState = RunningState.getGameState();
            if (gameState.activePlayer !== null && gameState.playerList !== null) {
                await chromaNativeSDK.createKeyboardEffect(gameState.mainHud);
                clearErrorSuppression(TASK_KEY);
                consecutiveFailures = 0;
            }
        } catch (ex) {
            consecutiveFailures++;
            logErrorSuppressed(TASK_KEY, ex, 'Error while refreshing main HUD');
            if (consecutiveFailures >= 3) {
                consecutiveFailures = 0;
                console.warn('Chroma SDK unresponsive, attempting reconnect...');
                await chromaNativeSDK.reconnect();
            }
        }
    };
}
