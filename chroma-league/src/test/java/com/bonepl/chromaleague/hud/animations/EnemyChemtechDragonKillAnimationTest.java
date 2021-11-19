package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyChemtechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyChemtechDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyChemtechDragonKillAnimation());
    }
}