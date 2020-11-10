package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyOceanDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyOceanDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyOceanDragonKillAnimation());
    }
}