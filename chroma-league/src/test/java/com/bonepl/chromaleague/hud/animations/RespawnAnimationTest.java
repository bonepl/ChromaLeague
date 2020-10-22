package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class RespawnAnimationTest {
    @Test
    void playRespawnAnimation() throws InterruptedException {
        RespawnAnimation respawnAnimation = new RespawnAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            while (respawnAnimation.hasFrame()) {
                razerSDKClient.createKeyboardEffect(respawnAnimation);
                Thread.sleep(50);
            }
        }
    }
}