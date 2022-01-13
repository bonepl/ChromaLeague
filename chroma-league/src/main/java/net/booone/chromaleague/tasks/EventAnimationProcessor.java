package net.booone.chromaleague.tasks;

import net.booone.chromaleague.hud.animations.*;
import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.rest.eventdata.EventType;
import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.animation.IFrame;
import net.booone.razersdk.animation.SimpleFrame;

import java.util.List;

public class EventAnimationProcessor {
    private EventAnimationProcessor() {
    }

    public static void processNewEvents(List<Event> events) {
        events.stream().map(EventType::fromEvent)
                .forEach(EventAnimationProcessor::processEventAnimation);
    }

    private static void processEventAnimation(EventType eventType) {
        IFrame animation = getEventAnimation(eventType);
        RunningState.getGameState().getMainHud().getEventAnimation().addAnimation(animation);
    }

    private static IFrame getEventAnimation(EventType eventType) {
        return switch (eventType) {
            case ALLY_BARON_KILL -> new AllyBaronKillAnimation();
            case ALLY_HERALD_KILL -> new AllyHeraldKillAnimation();
            case ALLY_CLOUD_DRAGON_KILL -> new AllyCloudDragonKillAnimation();
            case ALLY_CHEMTECH_DRAGON_KILL -> new AllyChemtechDragonKillAnimation();
            case ALLY_HEXTECH_DRAGON_KILL -> new AllyHextechDragonKillAnimation();
            case ALLY_INFERNAL_DRAGON_KILL -> new AllyInfernalDragonKillAnimation();
            case ALLY_OCEAN_DRAGON_KILL -> new AllyOceanDragonKillAnimation();
            case ALLY_MOUNTAIN_DRAGON_KILL -> new AllyMountainDragonKillAnimation();
            case ALLY_ELDER_DRAGON_KILL -> new AllyElderDragonKillAnimation();
            case ENEMY_BARON_KILL -> new EnemyBaronKillAnimation();
            case ENEMY_HERALD_KILL -> new EnemyHeraldKillAnimation();
            case ENEMY_CLOUD_DRAGON_KILL -> new EnemyCloudDragonKillAnimation();
            case ENEMY_CHEMTECH_DRAGON_KILL -> new EnemyChemtechDragonKillAnimation();
            case ENEMY_HEXTECH_DRAGON_KILL -> new EnemyHextechDragonKillAnimation();
            case ENEMY_INFERNAL_DRAGON_KILL -> new EnemyInfernalDragonKillAnimation();
            case ENEMY_OCEAN_DRAGON_KILL -> new EnemyOceanDragonKillAnimation();
            case ENEMY_MOUNTAIN_DRAGON_KILL -> new EnemyMountainDragonKillAnimation();
            case ENEMY_ELDER_DRAGON_KILL -> new EnemyElderDragonKillAnimation();
            case GAME_END_VICTORY -> new WinAnimation();
            case GAME_END_DEFEAT -> new LoseAnimation();
            case ACTIVE_PLAYER_KILL -> new ActivePlayerKillAnimation();
            case ACTIVE_PLAYER_ASSIST -> new ActivePlayerAssistAnimation();
            case ACTIVE_PLAYER_DIED, GAME_START, UNSUPPORTED -> new SimpleFrame();
        };
    }
}
