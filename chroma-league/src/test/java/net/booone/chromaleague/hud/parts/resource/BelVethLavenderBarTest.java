package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class BelVethLavenderBarTest extends AbstractResourceTest {

    @Test
    void testBelVethLavenderBar() {
        final IntSteps intSteps = new IntSteps(100, 0, 5);
        final BelVethLavenderBar belVethLavenderBar = new BelVethLavenderBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(belVethLavenderBar, 20);
    }
}