package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

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
