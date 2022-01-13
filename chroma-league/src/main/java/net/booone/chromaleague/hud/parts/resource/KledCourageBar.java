package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

public class KledCourageBar extends AnimatedFrame {
    private static final BreathingColor COURAGE_BAR = new BackgroundBreathingColor(StaticColor.YELLOW, 10);

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage >= 50 && resourcePercentage < 80) {
            COURAGE_BAR.setSteps(10);
            color = COURAGE_BAR.getColor();
        } else if (resourcePercentage >= 80 && resourcePercentage < 100) {
            COURAGE_BAR.setSteps(5);
            color = COURAGE_BAR.getColor();
        } else if (resourcePercentage == 100) {
            color = StaticColor.RED;
        } else {
            color = StaticColor.WHITE;
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
