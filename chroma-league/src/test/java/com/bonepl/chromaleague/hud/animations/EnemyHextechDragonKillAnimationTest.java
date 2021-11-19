package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyHextechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyHextechDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyHextechDragonKillAnimation());
    }
}