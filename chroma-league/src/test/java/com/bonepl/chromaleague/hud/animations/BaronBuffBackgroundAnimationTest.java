package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class BaronBuffBackgroundAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playBaronBuffBackgroundAnimation() {
        new AnimationTester().testAnimation(new BaronBuffBackgroundAnimation(), 100);
    }
}