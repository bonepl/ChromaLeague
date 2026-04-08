import {afterEach, beforeEach, describe, expect, test, vi} from 'vitest';
import {FixedDelayScheduler} from '../../src/tasks/FixedDelayScheduler.js';

describe('FixedDelayScheduler', () => {
    beforeEach(() => {
        vi.useFakeTimers();
    });

    afterEach(() => {
        vi.useRealTimers();
    });

    test('task runs after initial delay', async () => {
        const scheduler = new FixedDelayScheduler();
        const calls: number[] = [];

        scheduler.scheduleWithFixedDelay(() => { calls.push(Date.now()); }, 100, 500);

        expect(calls).toHaveLength(0);
        await vi.advanceTimersByTimeAsync(100);
        expect(calls).toHaveLength(1);

        scheduler.shutdown();
    });

    test('task reruns after each delay', async () => {
        const scheduler = new FixedDelayScheduler();
        let count = 0;

        scheduler.scheduleWithFixedDelay(() => { count++; }, 0, 200);

        await vi.advanceTimersByTimeAsync(0);   // initial run
        expect(count).toBe(1);
        await vi.advanceTimersByTimeAsync(200); // second run
        expect(count).toBe(2);
        await vi.advanceTimersByTimeAsync(200); // third run
        expect(count).toBe(3);

        scheduler.shutdown();
    });

    test('task that throws is still rescheduled', async () => {
        const scheduler = new FixedDelayScheduler();
        let count = 0;

        scheduler.scheduleWithFixedDelay(() => {
            count++;
            throw new Error('task error');
        }, 0, 100);

        await vi.advanceTimersByTimeAsync(0);
        expect(count).toBe(1);
        await vi.advanceTimersByTimeAsync(100);
        expect(count).toBe(2);

        scheduler.shutdown();
    });

    test('shutdown stops all tasks', async () => {
        const scheduler = new FixedDelayScheduler();
        let count = 0;

        scheduler.scheduleWithFixedDelay(() => { count++; }, 0, 100);
        await vi.advanceTimersByTimeAsync(0);
        expect(count).toBe(1);

        scheduler.shutdown();
        await vi.advanceTimersByTimeAsync(500);
        expect(count).toBe(1); // no more runs
    });

    test('multiple independent tasks run separately', async () => {
        const scheduler = new FixedDelayScheduler();
        let countA = 0;
        let countB = 0;

        scheduler.scheduleWithFixedDelay(() => { countA++; }, 0, 100);
        scheduler.scheduleWithFixedDelay(() => { countB++; }, 50, 100);

        await vi.advanceTimersByTimeAsync(0);
        expect(countA).toBe(1);
        expect(countB).toBe(0);

        await vi.advanceTimersByTimeAsync(50);
        expect(countA).toBe(1);
        expect(countB).toBe(1);

        await vi.advanceTimersByTimeAsync(50);
        expect(countA).toBe(2);
        expect(countB).toBe(1);

        scheduler.shutdown();
    });
});
