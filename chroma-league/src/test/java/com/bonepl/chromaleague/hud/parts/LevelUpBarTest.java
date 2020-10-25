package com.bonepl.chromaleague.hud.parts;

import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.Test;

class LevelUpBarTest {
    @Test
    void playLevelUpAnimation() throws InterruptedException {
        final LevelUpBar levelUpBar = new LevelUpBar();
        levelUpBar.levelUp();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            while (levelUpBar.hasFrame()) {
                razerSDKClient.createKeyboardEffect(levelUpBar);
                Thread.sleep(200);
            }
        }
    }
}