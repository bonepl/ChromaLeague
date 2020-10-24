package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.CustomData;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventDataProcessorTask implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final Queue<Event> unprocessedEvents = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        while (!unprocessedEvents.isEmpty()) {
            final Event nextEvent = unprocessedEvents.remove();
            processEventForCustomData(EventType.fromEvent(nextEvent));
        }
    }

    public void processEventForCustomData(EventType eventType) {
        switch (eventType) {
            case GAME_START -> GameState.setRunningGame(true);
            case ALLY_BARON_KILL -> GameStateHelper.startBaronBuff();
            case ALLY_CLOUD_DRAGON_KILL -> GameStateHelper.addKilledDragon(DragonType.CLOUD);
            case ALLY_INFERNAL_DRAGON_KILL -> GameStateHelper.addKilledDragon(DragonType.INFERNAL);
            case ALLY_MOUNTAIN_DRAGON_KILL -> GameStateHelper.addKilledDragon(DragonType.MOUNTAIN);
            case ALLY_OCEAN_DRAGON_KILL -> GameStateHelper.addKilledDragon(DragonType.OCEAN);
            case ALLY_ELDER_DRAGON_KILL, ENEMY_ELDER_DRAGON_KILL -> processElderKill(eventType);
            case ACTIVE_PLAYER_DIED -> resetAlivePlayerCounters();
            case ACTIVE_PLAYER_KILL -> GameStateHelper.addPlayerKill();
        }
    }

    private void processElderKill(EventType eventType) {
        if (eventType == EventType.ALLY_ELDER_DRAGON_KILL) {
            GameStateHelper.startElderBuff();
        }
        GameStateHelper.addKilledElder();
    }

    private void resetAlivePlayerCounters() {
        final CustomData customData = GameState.getCustomData();
        customData.setElderBuffEnd(null);
        customData.setBaronBuffEnd(null);
        customData.setActivePlayerKillingSpree(0);
    }

    public static void addEvents(List<Event> events) {
        logger.info("Added events for animation: " + Arrays.toString(events.toArray()));
        unprocessedEvents.addAll(events);
    }

    //TEST ONLY
    public static Queue<Event> getUnprocessedEvents() {
        return unprocessedEvents;
    }
}
