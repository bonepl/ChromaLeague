package net.booone.chromaleague.hud.parts;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.ResourceLoader;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.Test;

class AssistKillingSpreeBarTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testKillingSpreeBar() {
        new GameStateMocks();
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