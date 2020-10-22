package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
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