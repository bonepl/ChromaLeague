package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.EventType.*;

class DragonBarTest {

    @Test
    void testFullDragonBar() {
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_INFERNAL_DRAGON_KILL,
                ALLY_CLOUD_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));

        final DragonBar dragonBar = new DragonBar();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        EventDataProcessorTask.processEventForEventData(testDrakesOrder.remove());
                    }
                    if (i == 80) {
                        EventDataProcessorTask.processEventForEventData(ALLY_ELDER_DRAGON_KILL);
                    }
                })
                .testAnimation(dragonBar, 150);
    }
}