package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
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