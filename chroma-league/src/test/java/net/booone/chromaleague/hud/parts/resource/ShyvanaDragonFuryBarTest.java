package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ShyvanaDragonFuryBarTest extends AbstractResourceTest {
    @Test
    void testShyvanaDragonPoolBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final ShyvanaDragonFuryBar shyvanaDragonFuryBar = new ShyvanaDragonFuryBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 50) {
                        mockResource(100, 100);
                    } else {
                        mockResource(intSteps.nextInt(), 100);
                    }
                })
                .testAnimation(shyvanaDragonFuryBar, 70);

    }
}