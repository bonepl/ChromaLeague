package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyHextechDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyHextechDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyHextechDragonKillAnimation());
    }
}