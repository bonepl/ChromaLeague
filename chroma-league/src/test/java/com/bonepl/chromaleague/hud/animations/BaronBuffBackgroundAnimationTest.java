package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class BaronBuffBackgroundAnimationTest {
    @Test
    void playBaronBuffBackgroundAnimation() throws InterruptedException {
        BaronBuffBackgroundAnimation baronBuffBackgroundAnimation = new BaronBuffBackgroundAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 300; i++) {
                razerSDKClient.createKeyboardEffect(baronBuffBackgroundAnimation.getFrame());
                Thread.sleep(50);
            }
        }
    }
}