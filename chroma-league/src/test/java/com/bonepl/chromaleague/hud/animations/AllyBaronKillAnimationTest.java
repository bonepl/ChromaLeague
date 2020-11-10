package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyBaronKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyBaronKillAnimation() {
        new AnimationTester().testAnimation(new AllyBaronKillAnimation());
    }
}