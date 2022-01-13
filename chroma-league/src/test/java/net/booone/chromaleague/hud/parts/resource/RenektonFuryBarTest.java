package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class RenektonFuryBarTest extends AbstractResourceTest {

    @Test
    void testRenektonFuryBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final RenektonFuryBar vladimirBloodPoolBar = new RenektonFuryBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 30) {
                        mockResource(100, 100);
                    } else {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .testAnimation(vladimirBloodPoolBar, 50);

    }
}