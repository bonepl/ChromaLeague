package com.bonepl.chromaleague.razer.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyKeyboardEffectTest {

    @Test
    void testOneKeyEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new OneKeyKeyboardEffect(rzKey, Color.RED));
                Thread.sleep(50);
            }
        }
    }
}