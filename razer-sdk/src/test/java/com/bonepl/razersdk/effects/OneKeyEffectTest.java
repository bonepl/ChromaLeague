package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.ChromaRestSDK;
import com.bonepl.razersdk.sdk.RzKey2;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testOneKeyEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            for (RzKey2 rzKey : RzKey2.values()) {
                razerSDKClient.createKeyboardEffect(new SimpleFrame(rzKey, Color.RED));
                Thread.sleep(50);
            }
        }
    }
}