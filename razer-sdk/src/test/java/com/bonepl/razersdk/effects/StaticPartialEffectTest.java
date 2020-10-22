package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class StaticPartialEffectTest {
    private final List<RzKey> smiley = Arrays.asList(RzKey.RZKEY_W, RzKey.RZKEY_3, RzKey.RZKEY_4, RzKey.RZKEY_R,
            RzKey.RZKEY_I, RzKey.RZKEY_9, RzKey.RZKEY_0, RzKey.RZKEY_P,
            RzKey.RZKEY_X, RzKey.RZKEY_SPACE, RzKey.RZKEY_OEM_9);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testPartialStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(smiley, Color.YELLOW));
            Thread.sleep(100);
        }
    }
}