package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.BreathingColor;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class BaronBuffBackground extends AnimatedFrame {
    private final BreathingColor baronBuffColor = new BreathingColor(new Color(200, 0, 200));

    @Override
    public synchronized Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            withAnimationFrame(2, new Frame(baronBuffColor.getNextColor()));
        }
    }
}
