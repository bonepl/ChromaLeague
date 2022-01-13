package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyHextechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyHextechDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyHextechDragonKillAnimation());
    }
}