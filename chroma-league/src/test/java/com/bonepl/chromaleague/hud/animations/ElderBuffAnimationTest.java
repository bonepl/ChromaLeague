package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class ElderBuffAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playElderBuffAnimation() {
        new AnimationTester().testAnimation(new ElderBuffAnimation(), 60);
    }
}