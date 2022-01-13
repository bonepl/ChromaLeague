package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyInfernalDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyInfernalDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyInfernalDragonKillAnimation());
    }
}