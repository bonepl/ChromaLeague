package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.EventType.*;

class KilledDragonsBarTest {
    @BeforeEach
    void setUp() {
        RunningState.setRunningGame(true);
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testOceanDragons() {
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_INFERNAL_DRAGON_KILL,
                ALLY_CLOUD_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testInfernalDragons() {
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_OCEAN_DRAGON_KILL,
                ALLY_MOUNTAIN_DRAGON_KILL, ALLY_INFERNAL_DRAGON_KILL, ALLY_INFERNAL_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testCloudDragons() {
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_MOUNTAIN_DRAGON_KILL,
                ALLY_OCEAN_DRAGON_KILL, ALLY_CLOUD_DRAGON_KILL, ALLY_CLOUD_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testMountainDragons() {
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_CLOUD_DRAGON_KILL,
                ALLY_INFERNAL_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    private static void testDragonsWithSoul(Queue<EventType> testDrakesOrder) {
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        eventDataProcessor.processEventForEventData(testDrakesOrder.remove());
                    }
                })
                .testAnimation(KilledDragonsBar::new, 80);
    }
}