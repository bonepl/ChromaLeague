package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyHeraldKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyHeraldKillAnimation() {
        new AnimationTester().testAnimation(new EnemyHeraldKillAnimation());
    }
}