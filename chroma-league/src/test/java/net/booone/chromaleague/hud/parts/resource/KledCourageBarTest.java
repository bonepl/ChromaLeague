package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class KledCourageBarTest extends AbstractResourceTest {

    @Test
    void testKledCourageBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final KledCourageBar kledCourageBar = new KledCourageBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 50) {
                        mockResource(100, 100);
                    } else {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .testAnimation(kledCourageBar, 70);
    }
}