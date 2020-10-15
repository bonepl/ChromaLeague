package com.bonepl.chromaleague.league.rest.eventdata;

import com.bonepl.chromaleague.league.EventProcessor;
import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.LeagueHttpClient;
import com.bonepl.chromaleague.league.rest.eventdata.model.Event;
import com.bonepl.chromaleague.league.rest.eventdata.model.Events;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class FetchNewEventsTask implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        if (GameState.isGameActive()) {
            logger.info("Fetching Events");
            LeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/eventdata")
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
