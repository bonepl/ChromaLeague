package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyElderDragonKillAnimationTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyElderDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyElderDragonKillAnimation());
    }
}