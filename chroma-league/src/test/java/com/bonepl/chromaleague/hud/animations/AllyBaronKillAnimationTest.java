package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class AllyBaronKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testAllyBaronKillAnimation() {
        new AnimationTester().testAnimation(new AllyBaronKillAnimation());
    }
}