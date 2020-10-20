package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.EventProcessor;
import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClient;
import com.bonepl.chromaleague.rest.eventdata.model.Event;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.chromaleague.rest.eventdata.model.Events;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class FetchNewEventsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/eventdata";
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        if (GameState.isGameActive()) {
            logger.trace("Fetching Events");
            LeagueHttpClient.get(URL)
                    .map(events -> JsonIterator.deserialize(events, Events.class))
                    .map(Events::getEvents)
                    .map(this::collectUnprocessedEvents)
                    .ifPresent(GameState::addUnprocessedEvents);
        } else {
            EventProcessor.setLastProcessedEventId(-1);
        }
    }

    List<Event> collectUnprocessedEvents(List<Event> events) {
        final int lastProcessedEventId = EventProcessor.getLastProcessedEventId();
        if (!events.isEmpty() && (events.size() > lastProcessedEventId + 1)) {
            if (lastProcessedEventId == -1 && events.size() > 1) {
                logger.warn("Game reconnection detected, skipping passed events");
                events.stream().map(EventType::fromEvent).forEach(EventProcessor::processEventForCustomData);
                EventProcessor.setLastProcessedEventId(events.size() - 1);
                return Collections.emptyList();
            }
            final List<Event> newEvents = events.subList(lastProcessedEventId + 1, events.size());
            EventProcessor.setLastProcessedEventId(events.size() - 1);
            return newEvents;
        }
        return Collections.emptyList();
    }
}
