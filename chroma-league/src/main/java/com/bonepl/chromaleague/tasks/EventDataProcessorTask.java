package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.GameStateHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventDataProcessorTask implements Runnable {
    private static final Queue<Event> UNPROCESSED_EVENTS = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        while (!UNPROCESSED_EVENTS.isEmpty()) {
            processEventForEventData(EventType.fromEvent(UNPROCESSED_EVENTS.remove()));
        }
    }

    public static void processEventForEventData(EventType eventType) {
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
            case ACTIVE_PLAYER_ASSIST -> GameStateHelper.addPlayerAssist();
            case GAME_END_DEFEAT, GAME_END_VICTORY, ENEMY_OCEAN_DRAGON_KILL, ENEMY_MOUNTAIN_DRAGON_KILL,
                    ENEMY_INFERNAL_DRAGON_KILL, ENEMY_CLOUD_DRAGON_KILL, ENEMY_HERALD_KILL,
                    ENEMY_BARON_KILL, ALLY_HERALD_KILL, UNSUPPORTED -> {
            }
        }
    }

    private static void processElderKill(EventType eventType) {
        if (eventType == EventType.ALLY_ELDER_DRAGON_KILL) {
            GameStateHelper.startElderBuff();
        }
        GameStateHelper.addKilledElder();
    }

    private static void resetAlivePlayerCounters() {
        final EventData eventData = GameState.getEventData();
        eventData.setElderBuffEnd(null);
        eventData.setBaronBuffEnd(null);
        eventData.setActivePlayerKillingSpree(0);
        eventData.setActivePlayerAssistSpree(0);
    }

    public static void addEvents(List<Event> events) {
        UNPROCESSED_EVENTS.addAll(events);
    }

    //TEST ONLY
    public static Collection<Event> getUnprocessedEvents() {
        return Collections.unmodifiableCollection(UNPROCESSED_EVENTS);
    }

    //TEST ONLY
    public static void clearUnprocessedEvents() {
        UNPROCESSED_EVENTS.clear();
    }
}
