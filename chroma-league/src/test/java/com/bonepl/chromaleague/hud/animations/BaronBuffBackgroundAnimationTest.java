package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class BaronBuffBackgroundAnimationTest {

    @Test
    void playBaronBuffBackgroundAnimation() {
        new AnimationTester().testAnimation(new BaronBuffBackgroundAnimation(), 100);
    }
}