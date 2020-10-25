package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.Events;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FetchNewEventsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/eventdata";
    private static final Logger LOGGER = LogManager.getLogger();
    private static int lastProcessedEventId = -1;

    @Override
    public void run() {
        LeagueHttpClient.get(URL)
                .map(events -> JsonIterator.deserialize(events, Events.class))
                .map(Events::getEvents)
                .ifPresent(FetchNewEventsTask::collectUnprocessedEvents);
    }

    static void collectUnprocessedEvents(List<Event> events) {
        if (lastProcessedEventId == -1 && events.size() > 1) {
            LOGGER.warn("Game reconnection detected, fast-forwarding past events");
            GameStateHelper.resetCustomData();
            EventDataProcessorTask.addEvents(events);
        } else {
            if (!events.isEmpty() && events.size() > lastProcessedEventId + 1) {
                final List<Event> newEvents = events.subList(lastProcessedEventId + 1, events.size());
                EventAnimationProcessorTask.addEvents(newEvents);
                EventDataProcessorTask.addEvents(newEvents);
            }
        }
        lastProcessedEventId = events.get(events.size() - 1).getEventID();
    }

    public static void resetProcessedEventCounter() {
        lastProcessedEventId = -1;
    }
}
