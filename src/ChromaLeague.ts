import {FixedDelayScheduler} from './tasks/FixedDelayScheduler.js';
import {CheckRiotApiTask} from './tasks/CheckRiotApiTask.js';
import {MainTask} from './tasks/MainTask.js';
import {RunningState} from './state/RunningState.js';

export class ChromaLeague {
    private readonly scheduler = new FixedDelayScheduler();
    private readonly mainTask = new MainTask();
    private readonly riotApiChecker = new CheckRiotApiTask();

    run(): void {
        this.scheduler.scheduleWithFixedDelay(() => this.riotApiChecker.run(), 0, 1000);
        this.scheduler.scheduleWithFixedDelay(() => this.mainTask.run(), 100, 500);
    }

    async shutdown(): Promise<void> {
        console.log('Shutting down Chroma League...');
        this.scheduler.shutdown();
        
        // Wake up MainTask if it's waiting for API or Game state
        RunningState.getRiotApi().reset();
        RunningState.getRunningGame().reset();
        
        // Close MainTask and its resources (including ChromaNativeSDK)
        await this.mainTask.close();
        console.log('Chroma League shutdown complete.');
    }
}
