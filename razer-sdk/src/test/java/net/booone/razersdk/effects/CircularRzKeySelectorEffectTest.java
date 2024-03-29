package net.booone.razersdk.effects;

import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.color.TransitionColor;
import net.booone.razersdk.sdk.CircularRzKeySelector;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

class CircularRzKeySelectorEffectTest {
    @Test
    void testCircularRzKeySelectorAnimation() throws InterruptedException {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            CircularRzKeySelector circularRzKeySelector = new CircularRzKeySelector(RzKey.RZKEY_H, 1);
            TransitionColor transitionColor = new TransitionColor(StaticColor.RED, StaticColor.BLACK, 20);
            for (int i = 0; i < 30; i++) {
                chromaRestSDK.createKeyboardEffect(new SimpleFrame(circularRzKeySelector.getNextLayer(), transitionColor));
                Thread.sleep(200);
            }
        }
    }
}