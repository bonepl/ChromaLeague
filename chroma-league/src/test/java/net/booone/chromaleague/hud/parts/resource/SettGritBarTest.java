package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class SettGritBarTest extends AbstractResourceTest {
    @Test
    void testSettGritBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final SettGritBar settGritBar = new SettGritBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 50) {
                        mockResource(100, 100);
                    } else {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .withSleepTime(150)
                .testAnimation(settGritBar, 70);

    }
}