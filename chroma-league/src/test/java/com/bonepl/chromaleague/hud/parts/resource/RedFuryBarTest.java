package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

class RedFuryBarTest {
    @Test
    void testRedFuryBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final RedFuryBar yoneCloneBar = new RedFuryBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> ManaBarTest.mockResource(intSteps.nextInt(), 100))
                .testAnimation(yoneCloneBar, 40);
    }
}