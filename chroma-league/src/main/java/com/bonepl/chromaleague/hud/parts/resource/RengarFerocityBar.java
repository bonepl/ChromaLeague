package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.StaticColor;

public class RengarFerocityBar extends AnimatedFrame {
    private static final BreathingColor FEROCITY_FULL_COLOR = new BreathingColor(StaticColor.YELLOW, 5);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = FEROCITY_FULL_COLOR.getNextColor();
        } else {
            color = StaticColor.WHITE;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
