package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.Events;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.state.RunningState;
import com.jsoniter.JsonIterator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchNewEventsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/eventdata";
    private static final Logger LOGGER = Logger.getLogger(FetchNewEventsTask.class.getName());
    private static int lastProcessedEventId = -1;
    private final EventDataProcessor eventDataProcessor = new EventDataProcessor();
    private final EventAnimationProcessor eventAnimationProcessor = new EventAnimationProcessor();

    @Override
    public void run() {
        try {
            LeagueHttpClient.getResponse(URL)
                    .map(events -> JsonIterator.deserialize(events, Events.class))
                    .map(Events::getEvents)
                    .filter(events -> !events.isEmpty())
                    .ifPresent(this::collectUnprocessedEvents);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching Events");
        }
    }

    void collectUnprocessedEvents(List<Event> events) {
        if (RunningState.getGameState() == null) {
            waitForGameStart(events);
        } else if (RunningState.getGameState().isActivePlayerAvailable() && RunningState.getGameState().isPlayerListAvailable()) {
            final List<Event> unprocessedEvents = RunningState.getGameState().getEventData().getUnprocessedEvents(events);
            if (!unprocessedEvents.isEmpty()) {
                if (!hasPlayerReconnected(unprocessedEvents)) {
                    eventAnimationProcessor.processNewEvents(unprocessedEvents);
                }
                eventDataProcessor.processNewEvents(unprocessedEvents);
                RunningState.getGameState().getEventData().addProcessedEvents(unprocessedEvents);
            }
        }
    }

    private static boolean containsNewEventsToProcess(List<Event> events) {
        return events.size() > lastProcessedEventId + 1;
    }

    private static boolean hasPlayerReconnected(List<Event> events) {
        return events.get(events.size() - 1).getEventTime() - events.get(0).getEventTime() > 10;
    }

    private static void waitForGameStart(List<Event> events) {
        if (!events.isEmpty()) {
            new EventDataProcessor().processNewEvents(events.subList(0, 1));
        }
    }
}
