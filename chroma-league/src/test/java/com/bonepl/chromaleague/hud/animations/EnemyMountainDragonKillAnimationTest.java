package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyMountainDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyMountainDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyMountainDragonKillAnimation());
    }
}