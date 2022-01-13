package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class LoadingAnimationTest {
    @Test
    void testLoadingAnimation() {
        final LoadingAnimation loadingAnimation = new LoadingAnimation();
        new AnimationTester()
                .testAnimation(loadingAnimation, 100);
    }
}