package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class BaronBuffBackgroundAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playBaronBuffBackgroundAnimation() {
        new AnimationTester().testAnimation(new BaronBuffBackgroundAnimation(), 100);
    }
}