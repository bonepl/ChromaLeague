package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class BaronBuffBackgroundTest {
    @Test
    void playBaronBuffBackgroundAnimation() throws InterruptedException {
        BaronBuffBackground baronBuffBackground = new BaronBuffBackground();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 300; i++) {
                razerSDKClient.createKeyboardEffect(baronBuffBackground.getFrame());
                Thread.sleep(50);
            }
        }
    }
}