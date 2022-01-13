package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyHeraldKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyHeraldKillAnimation() {
        new AnimationTester().testAnimation(new EnemyHeraldKillAnimation());
    }
}