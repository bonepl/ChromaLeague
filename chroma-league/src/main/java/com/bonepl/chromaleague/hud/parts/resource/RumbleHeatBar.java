package com.bonepl.chromaleague.hud.parts.resource;

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
    private static final double HEAT_THRESHOLD = 13.0 * MainTask.ACTIVE_PLAYER_FETCH_DELAY / 1000;

    // previous values, to determine if a change occured
    private static int previousResourcePercentage = 0;

    private static boolean overheating = false;

    private static LocalTime lastTimeSeeingRise = LocalTime.now();
    private static int highestValue = 0;
    private boolean falling = false;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        final LocalTime now = LocalTime.now();
        if (resourcePercentage != previousResourcePercentage) {
            if (resourcePercentage < previousResourcePercentage) {
                // we're falling
                if (!falling) {
                    // note the last time we saw an increase;
                    highestValue = previousResourcePercentage;
                }
                falling = true;
            }
            if (resourcePercentage > previousResourcePercentage) {
                // we're rising
                falling = false;
                overheating = false;
                lastTimeSeeingRise = now;
                highestValue = resourcePercentage;
            }
            if (falling) {
                final long durationOfChange = Duration.between(lastTimeSeeingRise, now).toMillis();

                final int delta = highestValue - resourcePercentage;
                double decayRate = delta * 1000.0 / durationOfChange;
                LOGGER.debug("Calculated Rumble decay rate: {}", decayRate);
                // in game - 14 is the lowest I got for overheating, but I got one false positive so it must've reached <13 once.
                // while passively cooling down the rate is insanely low (2-7)
                if (decayRate > 10.0) {
                    LOGGER.debug("Rumble overheats");
                    overheating = true;
                } else {
                    // rumble is cooling down normally - ~10 heat per sec, 9.6 according to math in this program :D
                    LOGGER.debug("Rumble cools down normally");
                    overheating = false;
                }
            } else {
                LOGGER.debug("Rumble is neither overheating nor cooling");
                overheating = false;
            }
            previousResourcePercentage = resourcePercentage;
        }

        Color color;
        if (overheating) {
            color = Color.RED;
        } else {
            if (resourcePercentage >= 50) {
                color = Color.YELLOW;
            } else {
                color = Color.WHITE;
            }
        }

        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
