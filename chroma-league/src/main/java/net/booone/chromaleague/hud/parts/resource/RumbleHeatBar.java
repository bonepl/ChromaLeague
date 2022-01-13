package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.color.TransitionColor;

import java.time.Duration;
import java.time.LocalTime;

public class RumbleHeatBar extends AnimatedFrame {
    private static final double HEAT_THRESHOLD = 8.0;
    private static final TransitionColor HEAT_COLOR = new TransitionColor(StaticColor.YELLOW, StaticColor.RED);

    private LocalTime lastHeatingTime = LocalTime.now();
    private int previousResourcePercentage;
    private int highestResourceValue;
    private boolean overheating;
    private boolean coolingDown;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        if (resourcePercentage != previousResourcePercentage) {
            final LocalTime now = LocalTime.now();
            if (resourcePercentage < previousResourcePercentage) {
                if (!coolingDown) {
                    highestResourceValue = previousResourcePercentage;
                }
                coolingDown = true;
                final long durationOfChange = Duration.between(lastHeatingTime, now).toMillis();
                final int delta = highestResourceValue - resourcePercentage;
                double decayRate = delta * 1000.0 / durationOfChange;
                overheating = decayRate > HEAT_THRESHOLD;
            }
            if (resourcePercentage > previousResourcePercentage) {
                coolingDown = false;
                overheating = false;
                lastHeatingTime = now;
                highestResourceValue = resourcePercentage;
            }
            previousResourcePercentage = resourcePercentage;
        }

        Color color;
        if (overheating) {
            color = StaticColor.RED;
        } else {
            if (resourcePercentage >= 50) {
                color = HEAT_COLOR.getColorAtPercent(resourcePercentage - 50 << 1);
            } else {
                color = StaticColor.WHITE;
            }
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
