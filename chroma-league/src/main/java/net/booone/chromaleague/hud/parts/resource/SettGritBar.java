package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

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
