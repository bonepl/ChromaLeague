package com.bonepl.chromaleague.league.json.eventdata;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.json.ApacheLeagueHttpClient;
import com.bonepl.chromaleague.league.json.GameDetectionThread;
import com.bonepl.chromaleague.league.json.eventdata.model.Event;
import com.bonepl.chromaleague.league.json.eventdata.model.Events;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class EventDataThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    boolean alive = true;
    int lastProcessedEventId;

    public void run() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                while (GameDetectionThread.isGameActive()) {
                    fetchAndUpdateData();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                lastProcessedEventId = -1;
            }
        }
    }

    void fetchAndUpdateData() {
        ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/eventdata")
                .map(events -> JsonIterator.deserialize(events, Events.class))
                .map(Events::getEvents)
                .map(this::collectUnprocessedEvents)
                .ifPresent(GameState::addUnprocessedEvents);
    }

    List<Event> collectUnprocessedEvents(List<Event> events) {
        if (!events.isEmpty() && (events.size() > lastProcessedEventId + 1)) {
            if (lastProcessedEventId == -1 && events.size() > 1) {
                logger.warn("Game reconnection detected, skipping passed events");
                lastProcessedEventId = events.size() - 1;
                return Collections.emptyList();
            }
            final List<Event> newEvents = events.subList(lastProcessedEventId + 1, events.size());
            lastProcessedEventId = events.size() - 1;
            return newEvents;
        }
        return Collections.emptyList();
    }

    public boolean hasUnprocessedEvents() {
        return GameState.hasUnprocessedEvents();
    }

    public Event pollNextUnprocessedEvent() {
        return GameState.pollNextUnprocessedEvent();
    }
}
