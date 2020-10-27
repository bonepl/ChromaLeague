package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testStaticEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.WHITE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.GREEN));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.RED));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.BLACK));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.BLUE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.YELLOW));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.CYAN));
            Thread.sleep(100);
        }
    }
}