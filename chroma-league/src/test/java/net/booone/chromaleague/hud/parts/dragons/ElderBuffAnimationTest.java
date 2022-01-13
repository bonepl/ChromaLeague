package net.booone.chromaleague.hud.parts.dragons;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ElderBuffAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playElderBuffAnimation() {
        new AnimationTester().testAnimation(new ElderBuffAnimation(), 60);
    }
}