package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playActivePlayerKillAnimation() {
        new AnimationTester().testAnimation(new ActivePlayerKillAnimation());
    }
}