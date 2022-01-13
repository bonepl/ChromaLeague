package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyElderDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyElderDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyElderDragonKillAnimation());
    }
}