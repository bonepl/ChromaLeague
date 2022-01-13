package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.List;

class StaticPartialEffectTest {
    private final List<RzKey> smiley = List.of(RzKey.RZKEY_W, RzKey.RZKEY_3, RzKey.RZKEY_4, RzKey.RZKEY_R,
            RzKey.RZKEY_I, RzKey.RZKEY_9, RzKey.RZKEY_0, RzKey.RZKEY_P,
            RzKey.RZKEY_X, RzKey.RZKEY_SPACE, RzKey.RZKEY_COMA);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testPartialStaticEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            razerSDKClient.createKeyboardEffect(new SimpleFrame(smiley, StaticColor.YELLOW));
            Thread.sleep(100);
        }
    }
}