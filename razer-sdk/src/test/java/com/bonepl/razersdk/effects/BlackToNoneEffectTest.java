package com.bonepl.razersdk.effects;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class BlackToNoneEffectTest {
    private static final List<RzKey> FIRST_LETTERS =
            Arrays.asList(RZKEY_Q, RZKEY_W, RZKEY_E, RZKEY_R, RZKEY_T, RZKEY_Y, RZKEY_U);

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testNonTransparentBlack() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(Color.BLACK));
            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, Color.CYAN));
                razerSDKClient.createKeyboardEffect(animation);
                Thread.sleep(100);
            }
        }
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testTransparentBlack() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(Color.NONE));
            while (animation.hasFrame()) {
                animation.addToBack(new SimpleFrame(FIRST_LETTERS, Color.CYAN));
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
