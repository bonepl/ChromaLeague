package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testActivePlayerKillAnimation() {
        new AnimationTester().testAnimation(new ActivePlayerKillAnimation());
    }
}