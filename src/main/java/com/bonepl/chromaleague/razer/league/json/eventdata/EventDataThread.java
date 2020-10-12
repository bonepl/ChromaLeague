package com.bonepl.chromaleague.razer.league.json.eventdata;

import com.bonepl.chromaleague.razer.league.json.GameDetectionThread;
import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.eventdata.model.Event;
import com.bonepl.chromaleague.razer.league.json.eventdata.model.Events;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventDataThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    private final LeagueHttpClient leagueHttpClient;
    boolean alive = true;
    int lastProcessedEventId;
    Queue<Event> unprocessedEvents;

    public EventDataThread(LeagueHttpClient leagueHttpClient) {
        this.leagueHttpClient = leagueHttpClient;
    }

    private void init() {
        lastProcessedEventId = -1;
        unprocessedEvents = new LinkedBlockingQueue<>();
    }

    public void run() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                while (GameDetectionThread.isGameActive()) {
                    List<Event> events = fetchData();
                    unprocessedEvents.addAll(collectUnprocessedEvents(events));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                init();
            }
        }
    }

    List<Event> fetchData() {
        String json = leagueHttpClient.fetchJson("https://127.0.0.1:2999/liveclientdata/eventdata");
        if (json != null) {
            return JsonIterator.deserialize(json, Events.class).getEvents();
        }
        return Collections.emptyList();
    }

    List<Event> collectUnprocessedEvents(List<Event> events) {
        if (!events.isEmpty() && (events.size() > lastProcessedEventId + 1)) {
            final List<Event> newEvents = events.subList(lastProcessedEventId + 1, events.size());
            newEvents.forEach(ev -> logger.info("New event registered: " + ev));
            lastProcessedEventId = events.size() - 1;
            if (detectRealTimeEventProcessing(newEvents)) {
                return newEvents;
            } else {
                logger.warn("Game reconnection detected, skipping passed events");
            }
        }
        return Collections.emptyList();
    }

    private boolean detectRealTimeEventProcessing(List<Event> newEvents) {
        return Math.abs(newEvents.get(0).getEventTime() - newEvents.get(newEvents.size() - 1).getEventTime()) < 10.0;
    }

    public boolean hasUnprocessedEvents() {
        return !unprocessedEvents.isEmpty();
    }

    public Event pollNextUnprocessedEvent() {
        return unprocessedEvents.poll();
    }
}
