package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class BaronBuffBackgroundAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playBaronBuffBackgroundAnimation() {
        new AnimationTester().testAnimation(new BaronBuffBackgroundAnimation(), 100);
    }
}