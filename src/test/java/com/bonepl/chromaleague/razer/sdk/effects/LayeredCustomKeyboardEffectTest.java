package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.razer.sdk.effects.ProgressBarKeyboardEffectTest.BLACKWIDOW_FIRST_ROW;

class LayeredCustomKeyboardEffectTest {
    @Test
    void testLayeredKeyboardEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                LayeredCustomKeyboardEffect layeredCustomKeyboardEffect = new LayeredCustomKeyboardEffect()
                        .withCustomKeyboardEffect(new ProgressBarKeyboardEffect(BLACKWIDOW_FIRST_ROW.subList(0, 9), i, Color.GREEN))
                        .withCustomKeyboardEffect(new ProgressBarKeyboardEffect(BLACKWIDOW_FIRST_ROW.subList(9, 16), i, Color.BLUE));
                if (i % 10 == 0) {
                    layeredCustomKeyboardEffect.addCustomKeyboardEffect(new OneKeyKeyboardEffect(RzKey.RZKEY_SPACE, Color.RED));
                }
                razerSDKClient.createKeyboardEffect(layeredCustomKeyboardEffect);
                Thread.sleep(100);
            }
            Thread.sleep(2000);
        }
    }
}