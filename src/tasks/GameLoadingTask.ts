import {LoadingAnimation} from '../hud/animations/LoadingAnimation.js';
import type {ChromaNativeSDK} from '../razer-sdk/ChromaNativeSDK.js';
import {RunningState} from '../state/RunningState.js';
import {clearErrorSuppression, logErrorSuppressed} from './TaskErrorUtils.js';

const TASK_KEY = 'GameLoadingTask';

export function createGameLoadingTask(chromaNativeSDK: ChromaNativeSDK, loadingAnimation: LoadingAnimation): () => Promise<void> {
    return async () => {
        if (!RunningState.getRunningGame().getValue()) {
            try {
                await chromaNativeSDK.createKeyboardEffect(loadingAnimation);
                clearErrorSuppression(TASK_KEY);
            } catch (ex) {
                logErrorSuppressed(TASK_KEY, ex, 'Error while refreshing loading animation');
            }
        }
    };
}
