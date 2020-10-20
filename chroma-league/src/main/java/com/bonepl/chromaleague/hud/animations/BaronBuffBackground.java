package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.BreathingColor;
import com.bonepl.chromaleague.hud.PredefinedKeySets;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.ArrayList;
import java.util.List;

public class BaronBuffBackground extends AnimatedFrame {
    private final BreathingColor baronBuffColor = new BreathingColor(new Color(200, 0, 200));
    private static final List<RzKey> baronArea = buildBaronArea();

    @Override
    public synchronized Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            withAnimationFrame(2, new Frame(baronArea, baronBuffColor.getNextColor()));
        }
    }

    private static List<RzKey> buildBaronArea() {
        List<RzKey> baronArea = new ArrayList<>(40);
        baronArea.addAll(PredefinedKeySets.BLACKWIDOW_THIRD_ROW.subList(2, 11));
        baronArea.addAll(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW.subList(2, 10));
        baronArea.addAll(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW.subList(2, 9));
        baronArea.addAll(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW.subList(3, 5));
        return baronArea;
    }
}
