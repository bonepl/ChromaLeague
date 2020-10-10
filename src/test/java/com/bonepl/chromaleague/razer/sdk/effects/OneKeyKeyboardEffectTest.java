package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyKeyboardEffectTest {

    @Test
    void testOneKeyEffect() {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new OneKeyKeyboardEffect(rzKey, Color.RED));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}