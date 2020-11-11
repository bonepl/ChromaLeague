package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.ExperienceUtil;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;

import java.util.List;

public class EventDataProcessor {
    private final double currentTimeForReconnection;

    public EventDataProcessor() {
        currentTimeForReconnection = 0.0;
    }

    public EventDataProcessor(double currentTimeForReconnection) {
        this.currentTimeForReconnection = currentTimeForReconnection;
    }

    public void processNewEvents(List<Event> events) {
        events.forEach(this::processEventForEventData);
    }

    public void processEventForEventData(Event event) {
        final EventType eventType = EventType.fromEvent(event);
        switch (eventType) {
            case GAME_START -> RunningState.setRunningGame(true);
            case ALLY_BARON_KILL -> GameStateHelper.startBaronBuff(event.getEventTime(), currentTimeForReconnection);
            case ALLY_CLOUD_DRAGON_KILL -> addKilledDragon(DragonType.CLOUD);
            case ALLY_INFERNAL_DRAGON_KILL -> addKilledDragon(DragonType.INFERNAL);
            case ALLY_MOUNTAIN_DRAGON_KILL -> addKilledDragon(DragonType.MOUNTAIN);
            case ALLY_OCEAN_DRAGON_KILL -> addKilledDragon(DragonType.OCEAN);
            case ALLY_ELDER_DRAGON_KILL -> processAllyElderKill(event.getEventTime());
            case ENEMY_ELDER_DRAGON_KILL -> GameStateHelper.addKilledElder();
            case ACTIVE_PLAYER_DIED -> resetAlivePlayerCounters(event);
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

    private void processAllyElderKill(double eventTime) {
        GameStateHelper.startElderBuff(eventTime, currentTimeForReconnection);
    }

    private void resetAlivePlayerCounters(Event event) {
        final EventData eventData = RunningState.getGameState().getEventData();
        eventData.setLastDeathTime(event.getEventTime());
        final int level = RunningState.getGameState().getActivePlayer().getLevel();
        if (currentTimeForReconnection == 0.0) {
            eventData.setApproxLastDeathTimer(ExperienceUtil.getApproxDeathTimeForLevel(level));
        } else {
            eventData.setApproxLastDeathTimer(ExperienceUtil.getApproxDeathTimeForEventTime(event.getEventTime(), currentTimeForReconnection));
        }

        eventData.setElderBuffEnd(null);
        eventData.setBaronBuffEnd(null);
        eventData.setActivePlayerKillingSpree(0);
        eventData.setActivePlayerAssistSpree(0);
    }
}
