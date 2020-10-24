package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.EventDataProcessorTask;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class KillingSpreeBarTest {
    @Test
    void testKilledDragonBar() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 12; i++) {
                razerSDKClient.createKeyboardEffect(new KillingSpreeBar());
                new EventDataProcessorTask().processEventForCustomData(EventType.ACTIVE_PLAYER_KILL);
                Thread.sleep(100);
            }
        }
    }
}