import {DragonType} from '../rest/eventdata/DragonType.js';
import {Event} from '../rest/eventdata/Event.js';
import {RespawnIndicator} from './RespawnIndicator.js';

export class EventData {
    private readonly killedDragons: DragonType[] = [];
    private readonly processedEvents: Event[] = [];
    private readonly processedEventIds = new Set<number>();
    private lastProcessedEventTime: number | null = null;
    baronBuffEnd: number | null = null;
    elderBuffEnd: number | null = null;
    totalEldersKilled = 0;
    activePlayerKillingSpree = 0;
    activePlayerAssistSpree = 0;
    riftAnimationPlayed = false;
    respawnIndicator: RespawnIndicator = RespawnIndicator.IDLE;
    deathTime: number | null = null;
    respawnTime: number | null = null;

    getKilledDragons(): readonly DragonType[] {
        return this.killedDragons;
    }

    addKilledDragon(dragonType: DragonType): void {
        this.killedDragons.push(dragonType);
    }

    getLastProcessedEventTime(): number | null {
        return this.lastProcessedEventTime;
    }

    addProcessedEvents(events: Event[]): void {
        for (const event of events) {
            this.processedEvents.push(event);
            this.processedEventIds.add(event.EventID);
            if (this.lastProcessedEventTime === null || event.EventTime > this.lastProcessedEventTime) {
                this.lastProcessedEventTime = event.EventTime;
            }
        }
    }

    getUnprocessedEvents(fetchedEvents: Event[]): Event[] {
        return fetchedEvents.filter(fetched => !this.processedEventIds.has(fetched.EventID));
    }

    /** Test only */
    getProcessedEvents(): readonly Event[] {
        return this.processedEvents;
    }
}
