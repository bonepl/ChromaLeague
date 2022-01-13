package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyInfernalDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyInfernalDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyInfernalDragonKillAnimation());
    }
}