package com.bonepl.chromaleague.hud.parts;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.Color;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FIRST_ROW;

class ProgressBarTest {
    @Test
    void testProgressBarEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                razerSDKClient.createKeyboardEffect(new ProgressBar(BLACKWIDOW_FIRST_ROW, i, Color.GREEN));
                Thread.sleep(100);
            }
            for (int i = 100; i >= 0; i -= 5) {
                razerSDKClient.createKeyboardEffect(new ProgressBar(BLACKWIDOW_FIRST_ROW, i, Color.GREEN));
                Thread.sleep(100);
            }
        }
    }
}