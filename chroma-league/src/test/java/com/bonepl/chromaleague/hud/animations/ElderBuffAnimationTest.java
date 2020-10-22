package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class ElderBuffAnimationTest {
    @Test
    void playElderBuffAnimation() throws InterruptedException {
        ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 60; i++) {
                razerSDKClient.createKeyboardEffect(elderBuffAnimation);
                Thread.sleep(50);
            }
        }
    }
}