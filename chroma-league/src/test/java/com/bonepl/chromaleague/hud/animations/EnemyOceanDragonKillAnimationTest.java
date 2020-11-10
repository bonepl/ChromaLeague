package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyOceanDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyOceanDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyOceanDragonKillAnimation());
    }
}