package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyHextechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyHextechDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyHextechDragonKillAnimation());
    }
}