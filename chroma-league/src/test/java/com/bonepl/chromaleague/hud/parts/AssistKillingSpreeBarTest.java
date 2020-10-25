package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class AssistKillingSpreeBarTest {
    @Test
    void testKillingSpreeBar() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 15; i++) {
                razerSDKClient.createKeyboardEffect(new AssistKillingSpreeBar());
                new EventDataProcessorTask().processEventForEventData(EventType.ACTIVE_PLAYER_ASSIST);
                if (i > 4) {
                    new EventDataProcessorTask().processEventForEventData(EventType.ACTIVE_PLAYER_KILL);
                }
                Thread.sleep(100);
            }
        }
    }
}