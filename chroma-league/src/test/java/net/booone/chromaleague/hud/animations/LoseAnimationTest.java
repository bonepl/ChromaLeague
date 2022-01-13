package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class LoseAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playLoseAnimation() {
        new AnimationTester().testAnimation(new LoseAnimation(), 100);
    }
}