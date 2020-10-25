package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class EnemyHeraldKillAnimationTest {

    @Test
    void testEnemyHeraldKillAnimation() {
        new AnimationTester().testAnimation(new EnemyHeraldKillAnimation());
    }
}