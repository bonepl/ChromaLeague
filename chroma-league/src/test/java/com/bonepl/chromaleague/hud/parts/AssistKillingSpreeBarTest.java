package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.EventType;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import org.junit.jupiter.api.Test;

class AssistKillingSpreeBarTest {

    @Test
    void testKillingSpreeBar() {
        final AnimationTester animationTester = new AnimationTester()
                .withAfterIterationAction(i -> {
                    EventDataProcessorTask.processEventForEventData(EventType.ACTIVE_PLAYER_ASSIST);
                    if (i > 4) {
                        EventDataProcessorTask.processEventForEventData(EventType.ACTIVE_PLAYER_KILL);
                    }
                });

        animationTester.testAnimation(AssistKillingSpreeBar::new, 15);
    }
}