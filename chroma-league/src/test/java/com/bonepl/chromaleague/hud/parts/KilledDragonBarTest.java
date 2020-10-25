package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.EventType.*;

class KilledDragonBarTest {
    private final Queue<EventType> testDrakesOrder = new LinkedList<>(Arrays.asList(ALLY_INFERNAL_DRAGON_KILL,
            ALLY_CLOUD_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));

    @Test
    void testKilledDragonBar() {
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (!testDrakesOrder.isEmpty()) {
                        new EventDataProcessorTask().processEventForEventData(testDrakesOrder.remove());
                    }
                })
                .withSleepTime(500)
                .testAnimation(KilledDragonBar::new, 5);
    }
}