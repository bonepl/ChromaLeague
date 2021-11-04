package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.StaticColor;

public class YasuoWindBar extends AnimatedFrame {
    public static final BreathingColor WIND_SHIELD_READY_COLOR = new BreathingColor(StaticColor.WHITE);
    public static final Color WIND_SHIELD_COLOR = StaticColor.GRAY;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = WIND_SHIELD_READY_COLOR.getNextColor();
        } else {
            color = WIND_SHIELD_COLOR;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
