package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class AllyBaronKillAnimationTest {

    @Test
    void testAllyBaronKillAnimation() {
        new AnimationTester().testAnimation(new AllyBaronKillAnimation());
    }
}