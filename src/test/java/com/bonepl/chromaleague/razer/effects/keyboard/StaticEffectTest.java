package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    void testStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new Frame(Color.WHITE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.GREEN));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.RED));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.BLACK));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.BLUE));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.YELLOW));
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.CYAN));
            Thread.sleep(100);
        }
    }
}