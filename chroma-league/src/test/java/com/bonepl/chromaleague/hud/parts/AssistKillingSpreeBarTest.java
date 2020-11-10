package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.ResourceLoader;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.Test;

class AssistKillingSpreeBarTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testKillingSpreeBar() {
        new GameStateMocks("BooonE");
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();

        new AnimationTester()
                .withAfterIterationAction(i -> {
                    eventDataProcessor.processEventForEventData(ResourceLoader.eventFromJson("activePlayerAssist.json"));
                    if (i > 4) {
                        eventDataProcessor.processEventForEventData(ResourceLoader.eventFromJson("activePlayerKill.json"));
                    }
                }).testAnimation(AssistKillingSpreeBar::new, 15);
    }
}