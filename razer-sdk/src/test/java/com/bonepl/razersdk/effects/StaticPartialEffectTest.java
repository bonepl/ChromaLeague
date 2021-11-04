package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

class StaticPartialEffectTest {
    private final List<RzKey> smiley = List.of(RZKEY_W, RZKEY_3, RZKEY_4, RZKEY_R,
            RZKEY_I, RZKEY_9, RZKEY_0, RZKEY_P,
            RZKEY_X, RZKEY_SPACE, RZKEY_COMA);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testPartialStaticEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(smiley, StaticColor.YELLOW));
            Thread.sleep(100);
        }
    }
}