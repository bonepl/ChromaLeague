package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyBaronKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyBaronKillAnimation() {
        new AnimationTester().testAnimation(new EnemyBaronKillAnimation());
    }
}