package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyMountainDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyMountainDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyMountainDragonKillAnimation());
    }
}