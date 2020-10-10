package com.bonepl.chromaleague.razer.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;
import com.bonepl.chromaleague.razer.effects.keyboard.OneKeyEffect;
import com.bonepl.chromaleague.razer.effects.keyboard.ProgressBarEffect;
import com.bonepl.chromaleague.razer.effects.keyboard.StaticEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.razer.effects.ProgressBarEffectTest.BLACKWIDOW_FIRST_ROW;

class LayeredCustomEffectTest {
    @Test
    void testLayeredKeyboardEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                LayeredCustomEffect layeredCustomKeyboardEffect = new LayeredCustomEffect()
                        .withCustomKeyboardEffect(new StaticEffect(new Color(30, 30, 0)))
                        .withCustomKeyboardEffect(new ProgressBarEffect(BLACKWIDOW_FIRST_ROW.subList(0, 9), i, Color.GREEN))
                        .withCustomKeyboardEffect(new ProgressBarEffect(BLACKWIDOW_FIRST_ROW.subList(9, 16), i, Color.BLUE));
                if (i % 10 == 0) {
                    layeredCustomKeyboardEffect.addCustomKeyboardEffect(new OneKeyEffect(RzKey.RZKEY_SPACE, Color.RED));
                }
                razerSDKClient.createKeyboardEffect(layeredCustomKeyboardEffect);
                Thread.sleep(100);
            }
        }
    }
}