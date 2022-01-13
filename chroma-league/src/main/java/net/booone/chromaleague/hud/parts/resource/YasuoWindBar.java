package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.BackgroundBreathingColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;

public class YasuoWindBar extends AnimatedFrame {
    public static final BreathingColor WIND_SHIELD_READY_COLOR = new BackgroundBreathingColor(StaticColor.WHITE);
    public static final StaticColor WIND_SHIELD_COLOR = StaticColor.GRAY;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage == 100) {
            color = WIND_SHIELD_READY_COLOR.getColor();
        } else {
            color = WIND_SHIELD_COLOR;
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
