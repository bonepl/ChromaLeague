package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    void testOneKeyEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new Frame(rzKey, Color.RED));
                Thread.sleep(50);
            }
        }
    }
}