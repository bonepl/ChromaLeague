package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.animations.EventAnimation;
import com.bonepl.chromaleague.hud.animations.StaticBlinkingAnimation;
import com.bonepl.chromaleague.hud.animations.StaticPartialBlinkingAnimation;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.IFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class EventProcessor {
    private static final List<RzKey> ENEMY_KILLED_MOB_KEYS
            = Arrays.asList(RZKEY_PRINTSCREEN, RZKEY_SCROLL, RZKEY_PAUSE,
            RZKEY_INSERT, RZKEY_HOME, RZKEY_PAGEUP,
            RZKEY_DELETE, RZKEY_END, RZKEY_PAGEDOWN);
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
                case ENEMY_BARON_KILL, ENEMY_HERALD_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.PURPLE);
                case ENEMY_CLOUD_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.CYAN);
                case ENEMY_INFERNAL_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.RED);
                case ENEMY_OCEAN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.BLUE);
                case ENEMY_MOUNTAIN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.ORANGE);
                case ENEMY_ELDER_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.WHITE);
                case UNSUPPORTED -> null;
            };

            if (animation != null) {
                logger.info("Processing event: " + eventType);
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
