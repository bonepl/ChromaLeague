package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class RespawnAnimationTest {

    @Test
    void playRespawnAnimation() {
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}