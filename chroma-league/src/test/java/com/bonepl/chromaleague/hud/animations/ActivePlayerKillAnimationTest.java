package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class ActivePlayerKillAnimationTest {

    @Test
    void testActivePlayerKillAnimation() {
        new AnimationTester().testAnimation(new ActivePlayerKillAnimation());
    }
}