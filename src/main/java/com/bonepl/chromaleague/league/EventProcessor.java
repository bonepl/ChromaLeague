package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.league.hud.animations.StaticBlinkingAnimation;
import com.bonepl.chromaleague.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.league.json.eventdata.model.EventType;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventProcessor {
    private final static Logger logger = LogManager.getLogger();

    private EventProcessor() {
    }

    public static void processEvents(EventDataThread eventDataThread, RazerSDKClient razerSDKClient) {
        while (eventDataThread.hasUnprocessedEvents()) {
            final EventType eventType = EventType.fromEvent(eventDataThread.pollNextUnprocessedEvent());
            if (eventType != EventType.UNSUPPORTED) {
                logger.info("Processing event: " + eventType);
                switch (eventType) {
                    case BARON_KILL, HERALD_KILL -> new StaticBlinkingAnimation(8, Color.PURPLE).runEffect(razerSDKClient);
                    case CLOUD_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.CYAN).runEffect(razerSDKClient);
                    case INFERNAL_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.RED).runEffect(razerSDKClient);
                    case OCEAN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.BLUE).runEffect(razerSDKClient);
                    case MOUNTAIN_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.ORANGE).runEffect(razerSDKClient);
                    case ELDER_DRAGON_KILL -> new StaticBlinkingAnimation(8, Color.WHITE).runEffect(razerSDKClient);
                }
            }
        }

    }
}
