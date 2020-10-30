package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.bonepl.chromaleague.rest.eventdata.EventType.*;

class KilledDragonsBarTest {

    @Test
    void testOceanDragons() {
        RunningState.getGameState().getEventData().resetCounters();
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_INFERNAL_DRAGON_KILL,
                ALLY_CLOUD_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL, ALLY_OCEAN_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testInfernalDragons() {
        RunningState.getGameState().getEventData().resetCounters();
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_OCEAN_DRAGON_KILL,
                ALLY_MOUNTAIN_DRAGON_KILL, ALLY_INFERNAL_DRAGON_KILL, ALLY_INFERNAL_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testCloudDragons() {
        RunningState.getGameState().getEventData().resetCounters();
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_MOUNTAIN_DRAGON_KILL,
                ALLY_OCEAN_DRAGON_KILL, ALLY_CLOUD_DRAGON_KILL, ALLY_CLOUD_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testMountainDragons() {
        RunningState.getGameState().getEventData().resetCounters();
        Queue<EventType> testDrakesOrder = new LinkedList<>(List.of(ALLY_CLOUD_DRAGON_KILL,
                ALLY_INFERNAL_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL, ALLY_MOUNTAIN_DRAGON_KILL));

        testDragonsWithSoul(testDrakesOrder);
    }

    private void testDragonsWithSoul(Queue<EventType> testDrakesOrder) {
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        EventDataProcessorTask.processEventForEventData(testDrakesOrder.remove());
                    }
                })
                .testAnimation(KilledDragonsBar::new, 80);
    }
}