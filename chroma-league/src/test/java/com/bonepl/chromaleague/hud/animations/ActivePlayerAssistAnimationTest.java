package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class ActivePlayerAssistAnimationTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testActivePLayerAssistAnimation() {
        new AnimationTester().testAnimation(new ActivePlayerAssistAnimation());
    }
}