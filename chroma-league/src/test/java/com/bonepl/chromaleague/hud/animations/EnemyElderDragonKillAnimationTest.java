package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class EnemyElderDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testEnemyElderDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyElderDragonKillAnimation());
    }
}