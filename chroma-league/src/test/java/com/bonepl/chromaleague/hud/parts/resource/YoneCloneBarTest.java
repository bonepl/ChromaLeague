package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
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