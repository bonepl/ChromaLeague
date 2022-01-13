package net.booone.chromaleague.tasks;

import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.rest.http.LeagueHttpClients;
import net.booone.chromaleague.rest.http.handlers.EventsResponseHandler;
import net.booone.chromaleague.state.RunningState;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FetchNewEventsTask implements Runnable {
    public static final String URL = "https://127.0.0.1:2999/liveclientdata/eventdata";
    private static final Logger LOGGER = Logger.getLogger(FetchNewEventsTask.class.getName());
    private final EventsResponseHandler eventsResponseHandler = new EventsResponseHandler();

    @Override
    public void run() {
        try {
            LeagueHttpClients.getNonBlockingResponse(URL, eventsResponseHandler)
                    .ifPresent(FetchNewEventsTask::collectUnprocessedEvents);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "ERROR", ex);
        }
    }

    static void collectUnprocessedEvents(List<Event> events) {
        if (!RunningState.getRunningGame().getValue()) {
            waitForGameStart(events);
        } else if (RunningState.getGameState().getActivePlayer() != null && RunningState.getGameState().getPlayerList() != null) {
            final List<Event> unprocessedEvents = RunningState.getGameState().getEventData().getUnprocessedEvents(events);
            if (!unprocessedEvents.isEmpty()) {
                if (hasPlayerReconnected(unprocessedEvents)) {
                    final double gameTimeForReconnection = RunningState.getGameState().getGameStats().gameTime();
                    new EventDataProcessor(gameTimeForReconnection).processNewEvents(unprocessedEvents);
                } else {
                    new EventDataProcessor().processNewEvents(unprocessedEvents);
                    EventAnimationProcessor.processNewEvents(unprocessedEvents);
                }
                RunningState.getGameState().getEventData().addProcessedEvents(unprocessedEvents);
            }
        }
    }

    private static boolean hasPlayerReconnected(List<Event> events) {
        return events.get(events.size() - 1).EventTime() - events.get(0).EventTime() > 10;
    }

    private static void waitForGameStart(List<Event> events) {
        if (!events.isEmpty()) {
            new EventDataProcessor().processNewEvents(events.subList(0, 1));
        }
    }
}
