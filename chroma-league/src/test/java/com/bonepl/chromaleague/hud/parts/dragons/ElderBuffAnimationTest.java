package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ElderBuffAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playElderBuffAnimation() {
        new AnimationTester().testAnimation(new ElderBuffAnimation(), 60);
    }
}