package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;

import java.util.List;

public class EventDataProcessor {

    public void processNewEvents(List<Event> events) {
        events.stream().map(EventType::fromEvent).forEach(this::processEventForEventData);
    }

    public void processEventForEventData(EventType eventType) {
        switch (eventType) {
            case GAME_START -> RunningState.setRunningGame(true);
            case ALLY_BARON_KILL -> GameStateHelper.startBaronBuff();
            case ALLY_CLOUD_DRAGON_KILL -> addKilledDragon(DragonType.CLOUD);
            case ALLY_INFERNAL_DRAGON_KILL -> addKilledDragon(DragonType.INFERNAL);
            case ALLY_MOUNTAIN_DRAGON_KILL -> addKilledDragon(DragonType.MOUNTAIN);
            case ALLY_OCEAN_DRAGON_KILL -> addKilledDragon(DragonType.OCEAN);
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

    private static void addKilledDragon(DragonType dragonType) {
        RunningState.getGameState().getEventData().addKilledDragon(dragonType);
    }

    private static void processElderKill(EventType eventType) {
        if (eventType == EventType.ALLY_ELDER_DRAGON_KILL) {
            GameStateHelper.startElderBuff();
        }
        GameStateHelper.addKilledElder();
    }

    private static void resetAlivePlayerCounters() {
        final EventData eventData = RunningState.getGameState().getEventData();
        eventData.setElderBuffEnd(null);
        eventData.setBaronBuffEnd(null);
        eventData.setActivePlayerKillingSpree(0);
        eventData.setActivePlayerAssistSpree(0);
    }
}