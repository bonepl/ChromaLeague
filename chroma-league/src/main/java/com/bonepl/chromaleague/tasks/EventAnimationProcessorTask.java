package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.animations.*;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventAnimationProcessorTask implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final Queue<Event> unprocessedEvents = new LinkedList<>();

    @Override
    public void run() {
        while (!unprocessedEvents.isEmpty()) {
            final Event nextEvent = unprocessedEvents.remove();
            processEventAnimation(EventType.fromEvent(nextEvent));
        }
    }

    private void processEventAnimation(EventType eventType) {
        IFrame animation = getEventAnimation(eventType);
        EventAnimation.addFrames(animation);
    }

    private IFrame getEventAnimation(EventType eventType) {
        return switch (eventType) {
            case ALLY_BARON_KILL -> new AllyBaronKillAnimation();
            case ALLY_HERALD_KILL -> new AllyHeraldKillAnimation();
            case ALLY_CLOUD_DRAGON_KILL -> new AllyCloudDragonKillAnimation();
            case ALLY_INFERNAL_DRAGON_KILL -> new AllyInfernalDragonKillAnimation();
            case ALLY_OCEAN_DRAGON_KILL -> new AllyOceanDragonKillAnimation();
            case ALLY_MOUNTAIN_DRAGON_KILL -> new AllyMountainDragonKillAnimation();
            case ALLY_ELDER_DRAGON_KILL -> new AllyElderDragonKillAnimation();
            case ENEMY_BARON_KILL -> new EnemyBaronKillAnimation();
            case ENEMY_HERALD_KILL -> new EnemyHeraldKillAnimation();
            case ENEMY_CLOUD_DRAGON_KILL -> new EnemyCloudDragonKillAnimation();
            case ENEMY_INFERNAL_DRAGON_KILL -> new EnemyInfernalDragonKillAnimation();
            case ENEMY_OCEAN_DRAGON_KILL -> new EnemyOceanDragonKillAnimation();
            case ENEMY_MOUNTAIN_DRAGON_KILL -> new EnemyMountainDragonKillAnimation();
            case ENEMY_ELDER_DRAGON_KILL -> new EnemyElderDragonKillAnimation();
            case GAME_END_VICTORY -> new WinAnimation();
            case GAME_END_DEFEAT -> new LoseAnimation();
            case ACTIVE_PLAYER_KILL -> new ActivePlayerKillAnimation();
            case ACTIVE_PLAYER_DIED, GAME_START, UNSUPPORTED -> new SimpleFrame();
        };
    }

    public static void addEvents(List<Event> events) {
        unprocessedEvents.addAll(events);
    }

    //TEST ONLY
    public static Queue<Event> getUnprocessedEvents() {
        return unprocessedEvents;
    }
}
