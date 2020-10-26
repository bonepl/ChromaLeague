package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.PredefinedKeySets;
import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.ArrayList;
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
        List<RzKey> area = new ArrayList<>(40);
        area.addAll(PredefinedKeySets.BLACKWIDOW_THIRD_ROW.subList(2, 11));
        area.addAll(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW.subList(2, 10));
        area.addAll(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW.subList(2, 9));
        area.addAll(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW.subList(3, 5));
        return area;
    }
}
