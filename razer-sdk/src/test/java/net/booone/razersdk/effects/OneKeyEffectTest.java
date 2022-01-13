package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

class OneKeyEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testOneKeyEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            for (RzKey rzKey : RzKey.values()) {
                razerSDKClient.createKeyboardEffect(new SimpleFrame(rzKey, StaticColor.RED));
                Thread.sleep(50);
            }
        }
    }
}