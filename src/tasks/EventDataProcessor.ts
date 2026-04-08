import {DragonType} from '../rest/eventdata/DragonType.js';
import type {Event} from '../rest/eventdata/Event.js';
import {EventType, eventTypeFromEvent} from '../rest/eventdata/EventType.js';
import {RespawnIndicator} from '../state/RespawnIndicator.js';
import {RunningState} from '../state/RunningState.js';
import * as GameStateHelper from '../state/GameStateHelper.js';
import {getApproxDeathTimeForEventTime} from '../state/ExperienceUtil.js';

export class EventDataProcessor {
    private readonly currentTimeForReconnection: number;

    /**
     * @param currentTimeForReconnection - game time in seconds at the moment the player reconnected.
     *   Value 0.0 = normal game start (events are processed as they arrive).
     *   Value > 0 = mid-game reconnect (historical events are handled differently to avoid replaying effects).
     */
    constructor(currentTimeForReconnection = 0.0) {
        this.currentTimeForReconnection = currentTimeForReconnection;
    }

    processNewEvents(events: Event[]): void {
        for (const event of events) {
            this.processEventForEventData(event);
        }
    }

    private processEventForEventData(event: Event): void {
        const eventType = eventTypeFromEvent(event);
        switch (eventType) {
            case EventType.GAME_START:
                RunningState.setRunningGame(true);
                break;
            case EventType.ALLY_BARON_KILL:
                GameStateHelper.startBaronBuff(event.EventTime, this.currentTimeForReconnection);
                break;
            case EventType.ALLY_CLOUD_DRAGON_KILL:
                this.addKilledDragon(DragonType.CLOUD);
                break;
            case EventType.ALLY_CHEMTECH_DRAGON_KILL:
                this.addKilledDragon(DragonType.CHEMTECH);
                break;
            case EventType.ALLY_HEXTECH_DRAGON_KILL:
                this.addKilledDragon(DragonType.HEXTECH);
                break;
            case EventType.ALLY_INFERNAL_DRAGON_KILL:
                this.addKilledDragon(DragonType.INFERNAL);
                break;
            case EventType.ALLY_MOUNTAIN_DRAGON_KILL:
                this.addKilledDragon(DragonType.MOUNTAIN);
                break;
            case EventType.ALLY_OCEAN_DRAGON_KILL:
                this.addKilledDragon(DragonType.OCEAN);
                break;
            case EventType.ALLY_ELDER_DRAGON_KILL:
                GameStateHelper.startElderBuff(event.EventTime, this.currentTimeForReconnection);
                break;
            case EventType.ENEMY_ELDER_DRAGON_KILL:
                GameStateHelper.addKilledElder();
                break;
            case EventType.ACTIVE_PLAYER_DIED:
                this.resetAlivePlayerCounters(event);
                break;
            case EventType.ACTIVE_PLAYER_KILL:
                GameStateHelper.addPlayerKill();
                break;
            case EventType.ACTIVE_PLAYER_ASSIST:
                GameStateHelper.addPlayerAssist();
                break;
            case EventType.GAME_END_DEFEAT:
            case EventType.GAME_END_VICTORY:
                break;
            default:
                // No-op for enemy dragons, heralds, barons, unsupported
                break;
        }
    }
    private addKilledDragon(dragonType: DragonType): void {
        RunningState.getGameState().eventData.addKilledDragon(dragonType);
    }

    private resetAlivePlayerCounters(deathEvent: Event): void {
        const gameState = RunningState.getGameState();
        const eventData = gameState.eventData;
        eventData.deathTime = deathEvent.EventTime;
        if (this.currentTimeForReconnection === 0.0) {
            const { playerList, playerRiotId } = gameState;
            const respawnTimer = playerList?.getActivePlayer(playerRiotId ?? '')?.respawnTimer;
            if (respawnTimer == null) {
                console.warn('EventDataProcessor: playerList not yet loaded, skipping respawn timer calculation');
                return;
            }
            eventData.respawnTime = deathEvent.EventTime + respawnTimer;
            eventData.respawnIndicator = RespawnIndicator.CHARGING;
        } else {
            const approxDeathTime = getApproxDeathTimeForEventTime(
                deathEvent.EventTime, this.currentTimeForReconnection,
            );
            eventData.respawnTime = deathEvent.EventTime + approxDeathTime;
        }
        eventData.elderBuffEnd = null;
        eventData.baronBuffEnd = null;
        eventData.activePlayerKillingSpree = 0;
        eventData.activePlayerAssistSpree = 0;
    }
}
