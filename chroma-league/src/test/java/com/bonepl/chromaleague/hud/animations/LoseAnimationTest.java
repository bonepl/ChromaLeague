package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class LoseAnimationTest {
    @Test
    void playLoseAnimation() throws InterruptedException {
        final LoseAnimation loseAnimation = new LoseAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 1000; i++) {
                razerSDKClient.createKeyboardEffect(loseAnimation.getFrame());
                Thread.sleep(50);
            }
        }
    }
}