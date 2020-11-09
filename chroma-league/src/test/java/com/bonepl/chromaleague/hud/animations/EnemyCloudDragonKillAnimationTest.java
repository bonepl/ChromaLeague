package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyCloudDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testEnemyCloudDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyCloudDragonKillAnimation());
    }
}