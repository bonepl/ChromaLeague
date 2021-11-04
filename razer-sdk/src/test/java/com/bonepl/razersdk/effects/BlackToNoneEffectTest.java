package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class BlackToNoneEffectTest {
    private static final List<RzKey> FIRST_LETTERS =
            List.of(RZKEY_Q, RZKEY_W, RZKEY_E, RZKEY_R, RZKEY_T, RZKEY_Y, RZKEY_U);

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
