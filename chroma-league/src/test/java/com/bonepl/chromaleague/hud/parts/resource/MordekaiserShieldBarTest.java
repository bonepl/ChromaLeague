package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
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