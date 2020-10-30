package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateMocks;
import org.junit.jupiter.api.Test;

class RespawnAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playRespawnAnimation() {
        GameStateMocks.mockActivePlayerResource(0, 100);
        new AnimationTester().testAnimation(new RespawnAnimation());
    }
}