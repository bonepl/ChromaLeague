package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class LoseAnimationTest {

    @Test
    void playLoseAnimation() {
        new AnimationTester().testAnimation(new LoseAnimation(), 100);
    }
}