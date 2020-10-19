package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class WinAnimationTest {
    @Test
    void playWinAnimation() throws InterruptedException {
        final WinAnimation winAnimation = new WinAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 120; i++) {
                razerSDKClient.createKeyboardEffect(winAnimation.getFrame());
                Thread.sleep(50);
            }
        }
    }
}