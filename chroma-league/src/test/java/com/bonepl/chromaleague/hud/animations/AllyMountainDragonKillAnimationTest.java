package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class AllyMountainDragonKillAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playAllyMountainDragonKillAnimation() {
        new AnimationTester().testAnimation(new AllyMountainDragonKillAnimation());
    }
}