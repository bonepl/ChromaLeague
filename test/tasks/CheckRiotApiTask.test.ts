import {beforeEach, describe, expect, test, vi} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';

vi.mock('../../src/tasks/RiotApiChecker.js', () => ({
    checkRiotApiUp: vi.fn().mockResolvedValue(false),
}));

import { CheckRiotApiTask } from '../../src/tasks/CheckRiotApiTask.js';

describe('CheckRiotApiTask', () => {
    beforeEach(() => {
        RunningState.setRiotApi(true);
        RunningState.setRunningGame(false);
    });

    test('shouldFailWhenRiotApiNotUp', async () => {
        const task = new CheckRiotApiTask();

        //when - 3 consecutive failures required before disconnect
        await task.run();
        await task.run();
        await task.run();

        //then
        expect(RunningState.getRiotApi().getValue()).toBe(false);
    });
});
