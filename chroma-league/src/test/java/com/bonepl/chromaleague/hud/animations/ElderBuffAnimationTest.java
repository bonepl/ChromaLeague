package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class ElderBuffAnimationTest {

    @Test
    void playElderBuffAnimation() {
        new AnimationTester().testAnimation(new ElderBuffAnimation());
    }
}