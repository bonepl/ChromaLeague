package com.bonepl.chromaleague.razer.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.league.hud.animations.StaticBlinkingAnimation;
import com.bonepl.chromaleague.razer.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.razer.league.json.eventdata.model.EventType;

public class EventProcessor {
    private EventProcessor() {
    }

    public static void processEvents(EventDataThread eventDataThread, RazerSDKClient razerSDKClient) {
        while (eventDataThread.hasUnprocessedEvents()) {
            final EventType eventType = EventType.fromEvent(eventDataThread.pollNextUnprocessedEvent());
            if (eventType != EventType.UNSUPPORTED) {
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
