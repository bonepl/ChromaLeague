package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;

public class SettGritBar extends AnimatedFrame {
    private static final BreathingColor GRIT_BAR = new BreathingColor(Color.WHITE, 10);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        if (resourcePercentage >= 90) {
            GRIT_BAR.setUpColor(Color.YELLOW);
        } else {
            GRIT_BAR.setUpColor(Color.WHITE);
        }
        Color color;
        if (resourcePercentage >= 40 && resourcePercentage < 80) {
            GRIT_BAR.setSteps(10);
            color = GRIT_BAR.getNextColor();
        } else if (resourcePercentage >= 80) {
            GRIT_BAR.setSteps(5);
            color = GRIT_BAR.getNextColor();
        } else {
            color = Color.WHITE;
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
