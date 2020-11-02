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
    //    private static Lo
    private static int previousResourcePercentage = 0;
    private static LocalTime previousResourceChange = LocalTime.now();
    private static boolean overheating = false;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
//        LOGGER.info("resp " + resourcePercentage);
//        LOGGER.info(diff + " to threshold " + HEAT_THRESHOLD);
        if (resourcePercentage != previousResourcePercentage) {
            final LocalTime now = LocalTime.now();
            if (resourcePercentage == 100) {
                overheating = true;
            } else if (resourcePercentage < previousResourcePercentage) {
                final long durationOfChange = Duration.between(previousResourceChange, now).toMillis();
                overheating = durationOfChange < 650;
                LOGGER.info("From " + previousResourcePercentage + " to " + resourcePercentage + " Duration: " + durationOfChange + " Overheating: " + overheating);
            } else {
                overheating = false;
            }
            previousResourceChange = now;
        }

        Color color;
        if (resourcePercentage > previousResourcePercentage) {
            if (resourcePercentage >= 50) {
                color = Color.YELLOW;
            } else {
                color = Color.WHITE;
            }
        } else {
            if (overheating) {
                color = Color.RED;
            } else {
                if (resourcePercentage >= 50) {
                    color = Color.YELLOW;
                } else {
                    color = Color.WHITE;
                }
            }
        }
        previousResourcePercentage = resourcePercentage;
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        return super.getFrame();
    }
}
