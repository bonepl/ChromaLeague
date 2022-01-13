package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyChemtechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyChemtechDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyChemtechDragonKillAnimation());
    }
}