package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.IntSteps;
import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class SettGritBarTest {
    @Test
    void testSettGritBar() {
        final IntSteps intSteps = new IntSteps(0, 100, 5);
        final SettGritBar settGritBar = new SettGritBar();
        new AnimationTester()
                .withBeforeIterationAction(i -> {
                    if (i >= 20 && i <= 50) {
                        ManaBarTest.mockResource(100, 100);
                    } else {
                        ManaBarTest.mockResource(intSteps.nextInt(), 100);
                    }
                })
                .withSleepTime(150)
                .testAnimation(settGritBar, 70);

    }
}