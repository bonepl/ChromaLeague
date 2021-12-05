package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.color.TransitionColor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CircularRzKeySelectorTest {
    @Test
    @Disabled
    void name() throws InterruptedException {
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