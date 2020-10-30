package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.Events;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class FetchNewEventsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/eventdata";
    private static final Logger LOGGER = LogManager.getLogger();
    private static int lastProcessedEventId = -1;

    @Override
    public void run() {
        try {
            LeagueHttpClient.getResponse(URL)
                    .map(events -> JsonIterator.deserialize(events, Events.class))
                    .map(Events::getEvents)
                    .filter(events -> !events.isEmpty())
                    .ifPresent(FetchNewEventsTask::collectUnprocessedEvents);
        } catch (Exception ex) {
            LOGGER.error("Error while fetching new events", ex);
        }
    }

    static void collectUnprocessedEvents(List<Event> events) {
        if (RunningState.getGameState() == null) {
            waitForGameStart(events);
        } else if (RunningState.getGameState().isActivePlayerAvailable() && RunningState.getGameState().isPlayerListAvailable()) {
            if (hasPlayerReconnected(events)) {
                LOGGER.warn("Game reconnection detected, fast-forwarding past {} events", events.size());
                EventDataProcessorTask.addEvents(events);
            } else {
                if (containsNewEventsToProcess(events)) {
                    final List<Event> newEvents = events.subList(lastProcessedEventId + 1, events.size());
                    newEvents.forEach(event -> LOGGER.debug("Adding new event: {}", event));
                    EventAnimationProcessorTask.addEvents(newEvents);
                    EventDataProcessorTask.addEvents(newEvents);
                }
            }
            lastProcessedEventId = events.get(events.size() - 1).getEventID();
        }
    }

    private static boolean containsNewEventsToProcess(List<Event> events) {
        return events.size() > lastProcessedEventId + 1;
    }

    private static boolean hasPlayerReconnected(List<Event> events) {
        return lastProcessedEventId == -1 && events.size() > 1;
    }

    private static void waitForGameStart(List<Event> events) {
        if (!events.isEmpty()) {
            final Event gameStart = events.get(0);
            EventDataProcessorTask.addEvents(Collections.singletonList(gameStart));
        }
    }

    public static void resetProcessedEventCounter() {
        lastProcessedEventId = -1;
    }
}
