import {getNonBlockingResponse} from '../rest/http/LeagueHttpClients.js';
import type {Event} from '../rest/eventdata/Event.js';
import type {Events} from '../rest/eventdata/Events.js';
import {RunningState} from '../state/RunningState.js';
import {EventDataProcessor} from './EventDataProcessor.js';
import {processNewEventAnimations} from './EventAnimationProcessor.js';

const URL = 'https://127.0.0.1:2999/liveclientdata/eventdata';

export async function fetchNewEventsTask(): Promise<void> {
    try {
        const response = await getNonBlockingResponse<Events>(URL);
        if (response) {
            collectUnprocessedEvents(response.Events);
        }
    } catch (ex) {
        console.error('ERROR', ex);
    }
}

export function collectUnprocessedEvents(events: Event[]): void {
    if (!RunningState.getRunningGame().getValue()) {
        waitForGameStart(events);
    } else {
        const gameState = RunningState.getGameState();
        if (gameState.activePlayer !== null && gameState.playerList !== null) {
            const unprocessedEvents = gameState.eventData.getUnprocessedEvents(events);
            if (unprocessedEvents.length > 0) {
                if (hasPlayerReconnected(unprocessedEvents, gameState.eventData.getLastProcessedEventTime())) {
                    const gameTimeForReconnection = gameState.gameStats?.gameTime ?? 0;
                    new EventDataProcessor(gameTimeForReconnection).processNewEvents(unprocessedEvents);
                } else {
                    new EventDataProcessor().processNewEvents(unprocessedEvents);
                    processNewEventAnimations(unprocessedEvents);
                }
                gameState.eventData.addProcessedEvents(unprocessedEvents);
            }
        }
    }
}

/** Events older than 10s on first reconnect are considered already processed */
const INITIAL_RECONNECT_GAP_SECONDS = 10;
/** Events older than 5 minutes on a later reconnect within the same session are skipped */
const SESSION_RECONNECT_GAP_SECONDS = 300;

function hasPlayerReconnected(events: Event[], lastProcessedEventTime: number | null): boolean {
    if (lastProcessedEventTime === null) {
        return events[events.length - 1].EventTime - events[0].EventTime > INITIAL_RECONNECT_GAP_SECONDS;
    }
    return events[0].EventTime - lastProcessedEventTime > SESSION_RECONNECT_GAP_SECONDS;
}

function waitForGameStart(events: Event[]): void {
    if (events.length > 0) {
        new EventDataProcessor().processNewEvents(events.slice(0, 1));
    }
}
