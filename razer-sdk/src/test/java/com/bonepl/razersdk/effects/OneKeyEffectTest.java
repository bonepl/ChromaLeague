package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testOneKeyEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new SimpleFrame(rzKey, Color.RED));
                Thread.sleep(50);
            }
        }
    }
}