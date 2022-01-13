package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class EnemyChemtechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playEnemyChemtechDragonKillAnimation() {
        new AnimationTester().testAnimation(new EnemyChemtechDragonKillAnimation());
    }
}