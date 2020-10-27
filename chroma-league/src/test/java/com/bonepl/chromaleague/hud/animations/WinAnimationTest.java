package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class WinAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playWinAnimation() {
        new AnimationTester().testAnimation(new WinAnimation(), 100);
    }
}