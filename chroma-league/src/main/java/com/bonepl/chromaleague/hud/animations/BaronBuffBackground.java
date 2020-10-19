package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;

public class BaronBuffBackground extends AnimatedFrame {
    int colorValue = 0;
    int step = 20;

    public BaronBuffBackground() {
        extendAnimation();
    }

    @Override
    public synchronized Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            final int colorValue = getColorValue();
            withAnimationFrame(3, new Frame(new Color(colorValue, 0, colorValue)));
        }
    }

    public int getColorValue() {
        if ((colorValue + step) >= 200 || (colorValue + step) < 0) {
            step = Math.negateExact(step);
        }
        colorValue = colorValue + step;
        return Math.max(colorValue, 10);
    }
}
