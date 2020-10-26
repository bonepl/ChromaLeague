package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class StaticPartialEffectTest {
    private final List<RzKey2> smiley = Arrays.asList(RzKey2.RZKEY_W, RzKey2.RZKEY_3, RzKey2.RZKEY_4, RzKey2.RZKEY_R,
            RzKey2.RZKEY_I, RzKey2.RZKEY_9, RzKey2.RZKEY_0, RzKey2.RZKEY_P,
            RzKey2.RZKEY_X, RzKey2.RZKEY_SPACE, RzKey2.RZKEY_COMA);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testPartialStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(smiley, Color.YELLOW));
            Thread.sleep(100);
        }
    }
}