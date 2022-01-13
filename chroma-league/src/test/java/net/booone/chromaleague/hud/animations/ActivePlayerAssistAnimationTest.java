package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerAssistAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playActivePlayerAssistAnimation() {
        new AnimationTester().testAnimation(new ActivePlayerAssistAnimation());
    }
}