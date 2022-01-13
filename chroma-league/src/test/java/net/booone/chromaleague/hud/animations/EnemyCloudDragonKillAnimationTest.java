package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyCloudDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyCloudDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyCloudDragonKillAnimation());
    }
}