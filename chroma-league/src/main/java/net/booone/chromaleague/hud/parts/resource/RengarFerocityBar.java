package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

public class RengarFerocityBar extends AnimatedFrame {
    private static final BreathingColor FEROCITY_FULL_COLOR = new BackgroundBreathingColor(StaticColor.YELLOW, 5);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = FEROCITY_FULL_COLOR.getColor();
        } else {
            color = StaticColor.WHITE;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
