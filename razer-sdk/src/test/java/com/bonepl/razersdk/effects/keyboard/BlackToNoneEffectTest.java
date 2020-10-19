package com.bonepl.razersdk.effects.keyboard;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Animation;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class BlackToNoneEffectTest {
    private final List<RzKey> firstLetters = Arrays.asList(RZKEY_Q, RZKEY_W, RZKEY_E, RZKEY_R, RZKEY_T, RZKEY_Y, RZKEY_U);

    @Test
    void testNonTransparentBlack() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(Color.BLACK));
            while (animation.hasFrame()) {
                animation.addToBack(new Frame(firstLetters, Color.CYAN));
                razerSDKClient.createKeyboardEffect(animation.getFrame());
                Thread.sleep(100);
            }
        }
    }

    @Test
    void testTransparentBlack() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            Animation animation = new Animation();
            animation.addToFront(createMovingBlackPixel(Color.NONE));
            while (animation.hasFrame()) {
                animation.addToBack(new Frame(firstLetters, Color.CYAN));
                razerSDKClient.createKeyboardEffect(animation.getFrame());
                Thread.sleep(100);
            }
        }
    }

    AnimatedFrame createMovingBlackPixel(Color color) {
        AnimatedFrame movingBlackPixel = new AnimatedFrame();
        firstLetters.forEach(rzKey -> movingBlackPixel.withAnimationFrame(2, new Frame(rzKey, color)));
        return movingBlackPixel;
    }
}
