package com.bonepl.chromaleague.hud.colors;

import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class WindColorTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playFireColor() {
        Map<RzKey, WindColor> windColorMap = Arrays.stream(RzKey.values())
                .collect(Collectors.toMap(Function.identity(), key -> new WindColor()));

        AnimatedFrame animatedFrame = new AnimatedFrame();
        for (int i = 0; i < 200; i++) {
            animatedFrame.addAnimationFrame(new SimpleFrame(windColorMap));
        }

        new AnimationTester().testAnimation(animatedFrame);
    }
}