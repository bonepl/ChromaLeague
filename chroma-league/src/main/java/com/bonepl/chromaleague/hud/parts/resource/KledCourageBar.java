package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BackgroundBreathingColor;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.StaticColor;

public class KledCourageBar extends AnimatedFrame {
    private static final BreathingColor COURAGE_BAR = new BackgroundBreathingColor(StaticColor.YELLOW, 10);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage >= 50 && resourcePercentage < 80) {
            COURAGE_BAR.setSteps(10);
            color = COURAGE_BAR.getColor();
        } else if (resourcePercentage >= 80 && resourcePercentage < 100) {
            COURAGE_BAR.setSteps(5);
            color = COURAGE_BAR.getColor();
        } else if (resourcePercentage == 100) {
            color = StaticColor.RED;
        } else {
            color = StaticColor.WHITE;
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
