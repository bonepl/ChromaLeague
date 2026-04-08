import {RunningState} from '../state/RunningState.js';
import {MainThreads} from './MainThreads.js';

export class MainTask {
    private mainThreads: MainThreads | null = null;
    private gameInitFailed = false;

    async run(): Promise<void> {
        try {
            if (this.gameInitFailed) {
                this.gameInitFailed = false;
                if (this.mainThreads?.isAlive() && RunningState.getRunningGame().getValue()) {
                    await this.mainThreads.initializeGameThreads();
                }
                return;
            }

            const riotApiUp = await RunningState.getRiotApi().waitForChange();
            if (riotApiUp) {
                if (this.mainThreads === null || !this.mainThreads.isAlive()) {
                    this.mainThreads = new MainThreads();
                    await this.mainThreads.connect();
                }
                const runningGame = await RunningState.getRunningGame().waitForChange();
                if (runningGame) {
                    await this.mainThreads.initializeGameThreads();
                }
            } else {
                if (this.mainThreads !== null && this.mainThreads.isAlive()) {
                    await this.mainThreads.close();
                }
                RunningState.getRunningGame().reset();
            }
        } catch (ex) {
            console.error('Exception in MainTask', ex);
            if (this.mainThreads?.isAlive() && RunningState.getRunningGame().getValue()) {
                this.gameInitFailed = true;
            }
        }
    }

    async close(): Promise<void> {
        this.gameInitFailed = false;
        if (this.mainThreads !== null && this.mainThreads.isAlive()) {
            await this.mainThreads.close();
        }
        this.mainThreads = null;
    }
}
