package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyOceanDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyOceanDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyOceanDragonKillAnimation());
    }
}