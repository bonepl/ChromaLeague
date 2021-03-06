package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class RengarFerocityBar extends AnimatedFrame {
    private static final BreathingColor FEROCITY_FULL_COLOR = new BreathingColor(Color.YELLOW, 5);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = FEROCITY_FULL_COLOR.getNextColor();
        } else {
            color = Color.WHITE;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
