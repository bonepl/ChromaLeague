package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import org.junit.jupiter.api.Test;

class StaticEffectSDKTest {
    @Test
    void testStaticEffect() {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.WHITE));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.GREEN));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.RED));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.BLACK));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.BLUE));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.YELLOW));
            Thread.sleep(1000);
            razerSDKClient.createKeyboardEffect(new StaticEffectSDK(Color.CYAN));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}