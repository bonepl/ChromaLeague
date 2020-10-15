package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.league.hud.animations.StaticBlinkingAnimation;
import com.bonepl.chromaleague.league.hud.animations.StaticPartialBlinkingAnimation;
import com.bonepl.chromaleague.league.rest.eventdata.model.EventType;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

public class EventProcessor {
    private static final List<RzKey> ENEMY_KILLED_MOB_KEYS
            = Arrays.asList(RZKEY_PRINTSCREEN, RZKEY_SCROLL, RZKEY_PAUSE,
            RZKEY_INSERT, RZKEY_HOME, RZKEY_PAGEUP,
            RZKEY_DELETE, RZKEY_END, RZKEY_PAGEDOWN);
    private final static Logger logger = LogManager.getLogger();

    private static int lastProcessedEventId = -1;

    private EventProcessor() {
    }

    public static void processEvents(RazerSDKClient razerSDKClient) {
        while (GameState.hasUnprocessedEvents()) {
            final EventType eventType = EventType.fromEvent(GameState.pollNextUnprocessedEvent());
            if (eventType != EventType.UNSUPPORTED) {
                logger.info("Processing event: " + eventType);
                switch (eventType) {
                    case ALLY_BARON_KILL, ALLY_HERALD_KILL -> new StaticBlinkingAnimation(8, Color.PURPLE).runEffect(razerSDKClient);
                    case ALLY_CLOUD_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.CYAN).runEffect(razerSDKClient);
                    case ALLY_INFERNAL_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.RED).runEffect(razerSDKClient);
                    case ALLY_OCEAN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.BLUE).runEffect(razerSDKClient);
                    case ALLY_MOUNTAIN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.ORANGE).runEffect(razerSDKClient);
                    case ALLY_ELDER_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.WHITE).runEffect(razerSDKClient);
                    case ENEMY_BARON_KILL, ENEMY_HERALD_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.PURPLE).runEffect(razerSDKClient);
                    case ENEMY_CLOUD_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.CYAN).runEffect(razerSDKClient);
                    case ENEMY_INFERNAL_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.RED).runEffect(razerSDKClient);
                    case ENEMY_OCEAN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.BLUE).runEffect(razerSDKClient);
                    case ENEMY_MOUNTAIN_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.ORANGE).runEffect(razerSDKClient);
                    case ENEMY_ELDER_DRAGON_KILL -> new StaticPartialBlinkingAnimation(ENEMY_KILLED_MOB_KEYS, 8, Color.WHITE).runEffect(razerSDKClient);
                }
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
