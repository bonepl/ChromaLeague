package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BlackToNoneEffectTest {
    private static final List<RzKey> FIRST_LETTERS =
            List.of(RzKey.RZKEY_Q, RzKey.RZKEY_W, RzKey.RZKEY_E, RzKey.RZKEY_R, RzKey.RZKEY_T, RzKey.RZKEY_Y, RzKey.RZKEY_U);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testNonTransparentBlack() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(StaticColor.BLACK));
            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, StaticColor.CYAN));
                razerSDKClient.createKeyboardEffect(animation);
                Thread.sleep(100);
            }
        }
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testTransparentBlack() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(StaticColor.NONE));
            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, StaticColor.CYAN));
                razerSDKClient.createKeyboardEffect(animation);
                Thread.sleep(100);
            }
        }
    }

    private static AnimatedFrame createMovingBlackPixel(Color color) {
        AnimatedFrame movingBlackPixel = new AnimatedFrame();
        FIRST_LETTERS.forEach(rzKey -> movingBlackPixel.addAnimationFrame(2, new SimpleFrame(rzKey, color)));
        return movingBlackPixel;
    }
}
