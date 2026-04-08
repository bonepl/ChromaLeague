import {RunningState} from '../state/RunningState.js';
import {checkRiotApiUp} from './RiotApiChecker.js';

const FAILURES_BEFORE_DISCONNECT = 3;

export class CheckRiotApiTask {
    private consecutiveFailures = 0;

    async run(): Promise<void> {
        const isUp = await checkRiotApiUp();
        if (isUp) {
            this.consecutiveFailures = 0;
            RunningState.setRiotApi(true);
        } else {
            this.consecutiveFailures++;
            if (this.consecutiveFailures >= FAILURES_BEFORE_DISCONNECT) {
                RunningState.setRiotApi(false);
            }
        }
    }
}
