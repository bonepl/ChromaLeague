package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyInfernalDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyInfernalDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyInfernalDragonKillAnimation());
    }
}