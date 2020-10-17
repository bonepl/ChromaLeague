package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.effects.animation.LayeredFrame;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.razer.effects.keyboard.ProgressBarTest.BLACKWIDOW_FIRST_ROW;

class LayeredCustomEffectTest {
    @Test
    void testLayeredKeyboardEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                LayeredFrame layeredFrame = new LayeredFrame()
                        .withFrame(new Frame(new Color(30, 30, 0)))
                        .withFrame(new ProgressBar(BLACKWIDOW_FIRST_ROW.subList(0, 9), i, Color.GREEN))
                        .withFrame(new ProgressBar(BLACKWIDOW_FIRST_ROW.subList(9, 16), i, Color.GREEN));
                if (i % 10 == 0) {
                    layeredFrame.withFrame(new Frame(RzKey.RZKEY_SPACE, Color.RED));
                }
                razerSDKClient.createKeyboardEffect(layeredFrame);
                Thread.sleep(100);
            }
        }
    }
}