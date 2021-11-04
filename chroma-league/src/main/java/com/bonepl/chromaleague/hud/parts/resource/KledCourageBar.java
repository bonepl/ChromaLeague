package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;

public class KledCourageBar extends AnimatedFrame {
    private static final BreathingColor COURAGE_BAR = new BreathingColor(Color.YELLOW, 10);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage >= 50 && resourcePercentage < 80) {
            COURAGE_BAR.setSteps(10);
            color = COURAGE_BAR.getNextColor();
        } else if (resourcePercentage >= 80 && resourcePercentage < 100) {
            COURAGE_BAR.setSteps(5);
            color = COURAGE_BAR.getNextColor();
        } else if (resourcePercentage == 100) {
            color = Color.RED;
        } else {
            color = Color.WHITE;
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
