package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import org.junit.jupiter.api.Test;

class StaticEffectTest {
    @Test
    void testStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.WHITE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.GREEN));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.RED));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.BLACK));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.BLUE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.YELLOW));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticKeyboardEffect(Color.CYAN));
            Thread.sleep(100);
        }
    }
}