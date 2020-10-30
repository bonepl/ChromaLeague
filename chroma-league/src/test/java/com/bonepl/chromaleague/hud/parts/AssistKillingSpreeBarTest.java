package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssistKillingSpreeBarTest {

    @BeforeEach
    void setUp() {
        RunningState.setRunningGame(true);
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testKillingSpreeBar() {
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    eventDataProcessor.processEventForEventData(EventType.ACTIVE_PLAYER_ASSIST);
                    if (i > 4) {
                        eventDataProcessor.processEventForEventData(EventType.ACTIVE_PLAYER_KILL);
                    }
                }).testAnimation(AssistKillingSpreeBar::new, 15);
    }
}