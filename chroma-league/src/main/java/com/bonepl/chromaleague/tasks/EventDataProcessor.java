package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventDataProcessor {
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
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
            case GAME_START -> RunningState.setRunningGame(Boolean.TRUE);
            case ALLY_BARON_KILL -> GameStateHelper.startBaronBuff(event.EventTime(), currentTimeForReconnection);
            case ALLY_CLOUD_DRAGON_KILL -> addKilledDragon(DragonType.CLOUD);
            case ALLY_CHEMTECH_DRAGON_KILL -> addKilledDragon(DragonType.CHEMTECH);
            case ALLY_HEXTECH_DRAGON_KILL -> addKilledDragon(DragonType.HEXTECH);
            case ALLY_INFERNAL_DRAGON_KILL -> addKilledDragon(DragonType.INFERNAL);
            case ALLY_MOUNTAIN_DRAGON_KILL -> addKilledDragon(DragonType.MOUNTAIN);
            case ALLY_OCEAN_DRAGON_KILL -> addKilledDragon(DragonType.OCEAN);
            case ALLY_ELDER_DRAGON_KILL -> processAllyElderKill(event.EventTime());
            case ENEMY_ELDER_DRAGON_KILL -> GameStateHelper.addKilledElder();
            case ACTIVE_PLAYER_DIED -> resetAlivePlayerCounters(event);
            case ACTIVE_PLAYER_KILL -> GameStateHelper.addPlayerKill();
            case ACTIVE_PLAYER_ASSIST -> GameStateHelper.addPlayerAssist();
            case GAME_END_DEFEAT, GAME_END_VICTORY -> finishGame();
            case ENEMY_OCEAN_DRAGON_KILL, ENEMY_CHEMTECH_DRAGON_KILL, ENEMY_HEXTECH_DRAGON_KILL, ENEMY_MOUNTAIN_DRAGON_KILL,
                    ENEMY_INFERNAL_DRAGON_KILL, ENEMY_CLOUD_DRAGON_KILL, ENEMY_HERALD_KILL,
                    ENEMY_BARON_KILL, ALLY_HERALD_KILL, UNSUPPORTED -> {
            }
        }
    }

    private static void finishGame() {
        scheduledExecutorService.schedule(() -> RunningState.setRunningGame(false), 8, TimeUnit.SECONDS);
    }

    private static void addKilledDragon(DragonType dragonType) {
        RunningState.getGameState().getEventData().addKilledDragon(dragonType);
    }

    private void processAllyElderKill(double eventTime) {
        GameStateHelper.startElderBuff(eventTime, currentTimeForReconnection);
    }

    private void resetAlivePlayerCounters(Event deathEvent) {
        final EventData eventData = RunningState.getGameState().getEventData();
        eventData.setDeathTime(deathEvent.EventTime());
        if (currentTimeForReconnection == 0.0) {
            final double respawnTimer = GameStateHelper.getPlayerRespawnTime();
            eventData.setRespawnTime(deathEvent.EventTime() + respawnTimer);
            eventData.setRespawnIndicator(RespawnIndicator.CHARGING);
        } else {
            final double approxDeathTimeForEventTime = ExperienceUtil.getApproxDeathTimeForEventTime(deathEvent.EventTime(), currentTimeForReconnection);
            eventData.setRespawnTime(eventData.getDeathTime() + approxDeathTimeForEventTime);
        }

        eventData.setElderBuffEnd(null);
        eventData.setBaronBuffEnd(null);
        eventData.setActivePlayerKillingSpree(0);
        eventData.setActivePlayerAssistSpree(0);
    }
}
