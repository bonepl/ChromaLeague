package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.ProgressiveRzKeySelector;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class ProgressiveRzKeySelectorEffectTest {
    @Test
    void testProgressiveRzKeySelectorAnimation() throws InterruptedException {
        List<Set<RzKey>> parts = IntStream.range(RzKey.RZKEY_TILDE.getColumn(), RzKey.RZKEY_BACKSLASH.getColumn() + 1)
                .mapToObj(col -> new RzKeySelector().withColumn(col).withRowBetween(RzKey.RZKEY_TILDE, RzKey.RZKEY_LCTRL).asSet())
                .toList();
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector(parts, 3);

            for (int i = 0; i < 60; i++) {
                chromaRestSDK.createKeyboardEffect(new SimpleFrame(progressiveRzKeySelector.getNextPart(), StaticColor.WHITE));
                Thread.sleep(100);
            }
        }
    }
}
