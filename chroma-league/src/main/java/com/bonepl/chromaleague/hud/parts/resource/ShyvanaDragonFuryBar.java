package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class ShyvanaDragonFuryBar extends AnimatedFrame {
    public static final Color DRAGON_FURY_COLOR = Color.RED;
    private static final BreathingColor ULTI_READY_COLOR = new BreathingColor(Color.RED);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = ULTI_READY_COLOR.getNextColor();
        } else {
            color = DRAGON_FURY_COLOR;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
