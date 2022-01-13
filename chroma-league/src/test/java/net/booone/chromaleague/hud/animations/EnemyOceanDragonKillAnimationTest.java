package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyOceanDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyOceanDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyOceanDragonKillAnimation());
    }
}