package com.bonepl.chromaleague.hud.colors;

import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

class GoldColorTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playGoldColor() {
        AtomicInteger in = new AtomicInteger(1);
        Map<RzKey, GoldColor> mountainColorMap = Arrays.stream(RzKey.values())
                .collect(Collectors.toMap(Function.identity(), key -> new GoldColor(in.getAndIncrement())));

        AnimatedFrame animatedFrame = new AnimatedFrame();
        for (int i = 0; i < 400; i++) {
            animatedFrame.addAnimationFrame(new SimpleFrame(mountainColorMap));
        }

        new AnimationTester().testAnimation(animatedFrame);
    }
}