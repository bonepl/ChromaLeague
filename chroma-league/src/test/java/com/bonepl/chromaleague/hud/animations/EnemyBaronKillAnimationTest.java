package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class EnemyBaronKillAnimationTest {

    @Test
    void testEnemyBaronKillAnimation() {
        new AnimationTester().testAnimation(new EnemyBaronKillAnimation());
    }
}