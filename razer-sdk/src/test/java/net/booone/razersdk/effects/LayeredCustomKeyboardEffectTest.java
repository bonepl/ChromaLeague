package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.LayeredFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class LayeredCustomKeyboardEffectTest {

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    void testLayeredKeyboardEffect() throws InterruptedException {
        try (ChromaRestSDK razerSDKClient = new ChromaRestSDK()) {
            final AnimatedFrame firstAnimatedFrame = createFramesFromEnum(7, 18, StaticColor.GREEN);
            final AnimatedFrame secondAnimatedFrame = createFramesFromEnum(0, 11, StaticColor.BLUE);
            for (int i = 0; i <= 100; i += 5) {
                LayeredFrame layeredFrame = new LayeredFrame();
                layeredFrame.addFrame(new SimpleFrame(new StaticColor(30, 30, 0)));
                layeredFrame.addFrame(firstAnimatedFrame);
                layeredFrame.addFrame(secondAnimatedFrame);
                if (i % 10 == 0) {
                    layeredFrame.addFrame(new SimpleFrame(RzKey.RZKEY_SPACE, StaticColor.RED));
                }
                razerSDKClient.createKeyboardEffect(layeredFrame);
                Thread.sleep(100);
            }
        }
    }

    private static AnimatedFrame createFramesFromEnum(int from, int to, Color color) {
        final List<RzKey> keys = Arrays.stream(RzKey.values())
                .skip(from).limit(to)
                .collect(Collectors.toList());
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        for (int i = 0; i < to; i++) {
            animatedFrame.addAnimationFrame(2, new SimpleFrame(keys.subList(0, i), color));
        }
        return animatedFrame;
    }
}