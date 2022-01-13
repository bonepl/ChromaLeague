package net.booone.chromaleague.hud.colors;

import net.booone.chromaleague.hud.AnimationTester;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class GoldColorTest {
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playGoldColor() {
        Map<RzKey, GoldColor> goldColorMap = Arrays.stream(RzKey.values())
                .collect(Collectors.toMap(Function.identity(), key -> new GoldColor()));

        AnimatedFrame animatedFrame = new AnimatedFrame();
        for (int i = 0; i < 400; i++) {
            animatedFrame.addAnimationFrame(new SimpleFrame(goldColorMap));
        }

        new AnimationTester().testAnimation(animatedFrame);
    }
}