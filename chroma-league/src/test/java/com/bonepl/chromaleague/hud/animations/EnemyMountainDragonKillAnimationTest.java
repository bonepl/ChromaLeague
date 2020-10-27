package com.bonepl.chromaleague.hud.animations;

import org.junit.jupiter.api.Test;

class EnemyMountainDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testEnemyMountainDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyMountainDragonKillAnimation());
    }
}