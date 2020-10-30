package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.EventType.*;

class DragonBarTest {

    @Test
    void testFullDragonBar() {
        GameStateMocks.mockActivePlayerAlive(true);
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_INFERNAL_DRAGON_KILL,
                ALLY_CLOUD_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();

        final DragonBar dragonBar = new DragonBar();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        eventDataProcessor.processEventForEventData(testDrakesOrder.remove());
                    }
                    if (i == 80) {
                        eventDataProcessor.processEventForEventData(ALLY_ELDER_DRAGON_KILL);
                    }
                })
                .testAnimation(dragonBar, 150);
    }
}