package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElderBuffAnimationTest {
    @Test
    void playElderBuffAnimation() throws InterruptedException {
        ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 1000; i++) {
                razerSDKClient.createKeyboardEffect(elderBuffAnimation.getFrame());
                Thread.sleep(50);
            }
        }
    }
}