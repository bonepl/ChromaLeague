package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.DragonType;
import com.bonepl.chromaleague.hud.animations.*;
import com.bonepl.chromaleague.rest.CustomData;
import com.bonepl.chromaleague.rest.eventdata.model.Event;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.razersdk.animation.IFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventProcessor {

    private final static Logger logger = LogManager.getLogger();

    private static int lastProcessedEventId = -1;

    private EventProcessor() {
    }

    public static void processEvents() {
        while (GameState.hasUnprocessedEvents()) {
            final EventType eventType = EventType.fromEvent(GameState.pollNextUnprocessedEvent());
            processEventForCustomData(eventType);
            processEventAnimation(eventType);
        }
    }

    public static void processEventForCustomData(EventType eventType) {
        if (eventType == EventType.ALLY_BARON_KILL) {
            GameStateHelper.startBaronBuff();
        } else if (eventType == EventType.ALLY_CLOUD_DRAGON_KILL) {
            GameStateHelper.addKilledDragon(DragonType.CLOUD);
        } else if (eventType == EventType.ALLY_INFERNAL_DRAGON_KILL) {
            GameStateHelper.addKilledDragon(DragonType.INFERNAL);
        } else if (eventType == EventType.ALLY_MOUNTAIN_DRAGON_KILL) {
            GameStateHelper.addKilledDragon(DragonType.MOUNTAIN);
        } else if (eventType == EventType.ALLY_OCEAN_DRAGON_KILL) {
            GameStateHelper.addKilledDragon(DragonType.OCEAN);
        } else if (eventType == EventType.ALLY_ELDER_DRAGON_KILL) {
            GameStateHelper.addKilledElder();
            GameStateHelper.startElderBuff();
        } else if (eventType == EventType.ENEMY_ELDER_DRAGON_KILL) {
            GameStateHelper.addKilledElder();
        } else if(eventType == EventType.ACTIVE_PLAYER_DIED){
            final CustomData customData = GameState.getCustomData();
            customData.setElderBuffEnd(null);
            customData.setBaronBuffEnd(null);
        }
    }

    private static void processEventAnimation(EventType eventType) {
        IFrame animation = getEventAnimation(eventType);
        if (animation != null) {
            logger.info("Animating event: " + eventType);
            EventAnimation.addFrames(animation);
        }
    }

    private static IFrame getEventAnimation(EventType eventType) {
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
            case ACTIVE_PLAYER_DIED, UNSUPPORTED -> null;
        };
    }

    public static int getLastProcessedEventId() {
        return lastProcessedEventId;
    }

    public static void setLastProcessedEventId(int lastProcessedEventId) {
        EventProcessor.lastProcessedEventId = lastProcessedEventId;
    }
}
