package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.mockResource;

class YoneCloneBarTest {

    @Test
    void testYoneCloneBar() {
        final IntSteps intSteps = new IntSteps(100, 0, 5);
        final YoneCloneBar yoneCloneBar = new YoneCloneBar();

        new AnimationTester()
                .withBeforeIterationAction(i -> mockResource(intSteps.nextInt(), 100))
                .testAnimation(yoneCloneBar, 20);
    }
}