package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyInfernalDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyInfernalDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyInfernalDragonKillAnimation());
    }
}