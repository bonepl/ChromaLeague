export class FixedDelayScheduler {
    private timers: Map<number, NodeJS.Timeout> = new Map();
    private nextId = 0;

    scheduleWithFixedDelay(task: () => void | Promise<void>, initialDelay: number, delay: number): number {
        const id = this.nextId++;
        const runTask = async () => {
            try {
                await task();
            } catch (err) {
                console.error('[Scheduler] Task error:', err);
            } finally {
                if (this.timers.has(id)) {
                    this.timers.set(id, setTimeout(runTask, delay));
                }
            }
        };
        this.timers.set(id, setTimeout(runTask, initialDelay));
        return id;
    }

    shutdown(): void {
        for (const timer of this.timers.values()) clearTimeout(timer);
        this.timers.clear();
    }
}
