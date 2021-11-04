package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BackgroundBreathingColor;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.StaticColor;

public class SettGritBar extends AnimatedFrame {
    private static final BreathingColor GRIT_BAR = new BackgroundBreathingColor(StaticColor.WHITE, 10);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        if (resourcePercentage >= 90) {
            GRIT_BAR.setUpColor(StaticColor.YELLOW);
        } else {
            GRIT_BAR.setUpColor(StaticColor.WHITE);
        }
        Color color;
        if (resourcePercentage >= 40 && resourcePercentage < 80) {
            GRIT_BAR.setSteps(10);
            color = GRIT_BAR.getColor();
        } else if (resourcePercentage >= 80) {
            GRIT_BAR.setSteps(5);
            color = GRIT_BAR.getColor();
        } else {
            color = StaticColor.WHITE;
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
