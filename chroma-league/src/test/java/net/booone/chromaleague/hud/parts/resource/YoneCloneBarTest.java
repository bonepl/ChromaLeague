package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class YoneCloneBarTest extends AbstractResourceTest {

    @Test
    void testYoneCloneBar() {
        final IntSteps intSteps = new IntSteps(100, 0, 5);
        final YoneCloneBar yoneCloneBar = new YoneCloneBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(yoneCloneBar, 20);
    }
}