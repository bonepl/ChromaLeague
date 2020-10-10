package com.bonepl.chromaleague.razer.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.OneKeyEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    void testOneKeyEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new OneKeyEffect(rzKey, Color.RED));
                Thread.sleep(50);
            }
        }
    }
}