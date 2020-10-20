package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.EventProcessor;
import com.bonepl.chromaleague.rest.eventdata.model.EventType;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.model.EventType.*;

class KilledDragonBarTest {
    private final Queue<EventType> testDrakesOrder = new LinkedList<>(Arrays.asList(ALLY_INFERNAL_DRAGON_KILL,
            ALLY_CLOUD_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));

    @Test
    void testKilledDragonBar() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 5; i++) {
                razerSDKClient.createKeyboardEffect(new KilledDragonBar());
                EventProcessor.processEventForCustomData(testDrakesOrder.poll());
                Thread.sleep(500);
            }
            Thread.sleep(5000);
        }
    }
}