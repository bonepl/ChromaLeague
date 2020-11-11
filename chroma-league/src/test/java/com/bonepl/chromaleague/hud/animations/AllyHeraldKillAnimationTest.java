package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyHeraldKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyHeraldKillAnimation() {
        new AnimationTester().testAnimation(new AllyHeraldKillAnimation());
    }
}