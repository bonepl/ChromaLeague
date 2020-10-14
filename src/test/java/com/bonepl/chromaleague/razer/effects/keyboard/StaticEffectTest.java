package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    void testStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.WHITE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.GREEN));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.RED));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.BLACK));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.BLUE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.YELLOW));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new StaticEffect(Color.CYAN));
            Thread.sleep(100);
        }
    }
}