package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeyJoiner;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.List;

public class BaronBuffBackgroundAnimation extends AnimatedFrame {
    private final BreathingColor baronBuffColor = new BreathingColor(new Color(200, 0, 200));
    private static final List<RzKey> BARON_AREA = buildBaronArea();

    @Override
    public Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            addAnimationFrame(2, new SimpleFrame(BARON_AREA, baronBuffColor.getNextColor()));
        }
    }

    public static List<RzKey> buildBaronArea() {
        return new RzKeyJoiner()
                .with(new RzKeySelector().withRowOf(RzKey.RZKEY_Q).withColumnBetween(RzKey.RZKEY_Q, RzKey.RZKEY_I))
                .with(new RzKeySelector().withRowOf(RzKey.RZKEY_A).withColumnBetween(RzKey.RZKEY_A, RzKey.RZKEY_J))
                .with(new RzKeySelector().withRowOf(RzKey.RZKEY_Z).withColumnBetween(RzKey.RZKEY_Z, RzKey.RZKEY_N))
                .with(new RzKeySelector().withRowOf(RzKey.RZKEY_LALT).withColumnBetween(RzKey.RZKEY_LALT, RzKey.RZKEY_SPACE))
                .join();
    }
}
