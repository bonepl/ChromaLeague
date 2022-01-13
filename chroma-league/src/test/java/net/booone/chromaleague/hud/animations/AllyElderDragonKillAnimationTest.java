package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyElderDragonKillAnimationTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyElderDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyElderDragonKillAnimation());
    }
}