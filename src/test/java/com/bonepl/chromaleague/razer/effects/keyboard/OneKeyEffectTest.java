package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    void testOneKeyEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new Frame(rzKey, Color.RED).toCustomEffect());
                Thread.sleep(50);
            }
        }
    }
}