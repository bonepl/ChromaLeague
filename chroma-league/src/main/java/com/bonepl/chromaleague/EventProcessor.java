package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.animations.*;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.IFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public class EventProcessor {

    private final static Logger logger = LogManager.getLogger();

    private static int lastProcessedEventId = -1;

    private EventProcessor() {
    }

    public static void processEvents() {
        while (GameState.hasUnprocessedEvents()) {
            final EventType eventType = EventType.fromEvent(GameState.pollNextUnprocessedEvent());
            IFrame animation = switch (eventType) {
                case ALLY_BARON_KILL, ALLY_HERALD_KILL -> new StaticBlinkingAnimation(8, Color.PURPLE);
                case ALLY_CLOUD_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.CYAN);
                case ALLY_INFERNAL_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.RED);
                case ALLY_OCEAN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.BLUE);
                case ALLY_MOUNTAIN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.ORANGE);
                case ALLY_ELDER_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.WHITE);
                case ENEMY_BARON_KILL, ENEMY_HERALD_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.PURPLE);
                case ENEMY_CLOUD_DRAGON_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.CYAN);
                case ENEMY_INFERNAL_DRAGON_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.RED);
                case ENEMY_OCEAN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.BLUE);
                case ENEMY_MOUNTAIN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.ORANGE);
                case ENEMY_ELDER_DRAGON_KILL -> new StaticPartialBlinkingAnimation(BLACKWIDOW_FUNCTIONAL, 8, Color.WHITE);
                case GAME_END_VICTORY -> new WinAnimation();
                case GAME_END_DEFEAT -> new LoseAnimation();
                case UNSUPPORTED -> null;
            };

            if (animation != null) {
                logger.info("Processing event: " + eventType);
                if (eventType == EventType.ALLY_BARON_KILL) {
                    GameState.startBaronBuff();
                }
                EventAnimation.addFrames(animation);
            }
        }

    }

    public static int getLastProcessedEventId() {
        return lastProcessedEventId;
    }

    public static void setLastProcessedEventId(int lastProcessedEventId) {
        EventProcessor.lastProcessedEventId = lastProcessedEventId;
    }
}
