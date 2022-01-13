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

class FireColorTest {

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void playFireColor() {
        Map<RzKey, FireColor> fireColorMap = Arrays.stream(RzKey.values())
                .collect(Collectors.toMap(Function.identity(), key -> new FireColor()));

        AnimatedFrame animatedFrame = new AnimatedFrame();
        for (int i = 0; i < 200; i++) {
            animatedFrame.addAnimationFrame(new SimpleFrame(fireColorMap));
        }

        new AnimationTester().testAnimation(animatedFrame);
    }
}