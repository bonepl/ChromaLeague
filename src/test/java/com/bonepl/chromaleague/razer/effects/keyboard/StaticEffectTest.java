package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import org.junit.jupiter.api.Test;

class StaticEffectTest {

    @Test
    void testStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new Frame(Color.WHITE).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.GREEN).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.RED).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.BLACK).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.BLUE).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.YELLOW).getFrame().toCustomEffect());
            Thread.sleep(100);
            razerSDKClient.createKeyboardEffect(new Frame(Color.CYAN).getFrame().toCustomEffect());
            Thread.sleep(100);
        }
    }
}