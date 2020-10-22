package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class LevelUpAnimationTest {
    @Test
    void playLevelUpAnimation() throws InterruptedException {
        final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
        levelUpAnimation.levelUp();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            while (levelUpAnimation.hasFrame()) {
                razerSDKClient.createKeyboardEffect(levelUpAnimation);
                Thread.sleep(200);
            }
        }
    }
}