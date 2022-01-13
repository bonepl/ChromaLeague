package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.IntSteps;
import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class MordekaiserShieldBarTest extends AbstractResourceTest {
    @Test
    void testMordekaiserShieldBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final MordekaiserShieldBar mordekaiserShieldBar = new MordekaiserShieldBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(mordekaiserShieldBar, 40);
    }
}