package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BackgroundBreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.BreathingColor;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;

public class ShyvanaDragonFuryBar extends AnimatedFrame {
    public static final StaticColor DRAGON_FURY_COLOR = StaticColor.ORANGE;
    private static final BreathingColor ULTIMATE_READY_COLOR = new BackgroundBreathingColor(StaticColor.RED);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = ULTIMATE_READY_COLOR.getColor();
        } else {
            color = DRAGON_FURY_COLOR;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
