package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyChemtechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyChemtechDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyChemtechDragonKillAnimation());
    }
}