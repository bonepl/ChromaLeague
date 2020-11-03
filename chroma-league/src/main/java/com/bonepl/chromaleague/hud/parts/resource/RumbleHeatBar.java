package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.tasks.MainTask;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalTime;

public class RumbleHeatBar extends AnimatedFrame {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final double HEAT_THRESHOLD = 8.0;
    private static final TransitionColor HEAT_COLOR = new TransitionColor(Color.YELLOW, Color.RED);

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
                LOGGER.debug("Calculated Rumble decay rate: {}", decayRate);
                if (decayRate > HEAT_THRESHOLD) {
                    LOGGER.debug("Rumble overheats");
                    overheating = true;
                } else {
                    LOGGER.debug("Rumble cools down normally");
                    overheating = false;
                }
            }
            if (resourcePercentage > previousResourcePercentage) {
                LOGGER.debug("Rumble is neither overheating nor cooling");
                coolingDown = false;
                overheating = false;
                lastHeatingTime = now;
                highestResourceValue = resourcePercentage;
            }
            previousResourcePercentage = resourcePercentage;
        }

        Color color;
        if (overheating) {
            color = Color.RED;
        } else {
            if (resourcePercentage >= 50) {
                color = HEAT_COLOR.getColorAtPercent(resourcePercentage - 50 << 1);
            } else {
                color = Color.WHITE;
            }
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
