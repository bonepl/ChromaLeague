package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testStaticEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.WHITE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.GREEN));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.RED));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.BLACK));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.BLUE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.YELLOW));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new SimpleFrame(StaticColor.CYAN));
            Thread.sleep(100);
        }
    }
}