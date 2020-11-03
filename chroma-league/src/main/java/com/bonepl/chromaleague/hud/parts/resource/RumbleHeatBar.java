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

    private static LocalTime lastTimeSeeingRise = LocalTime.now();
    private static int highestValue = 0;
    boolean falling = false;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
//        LOGGER.info("resp " + resourcePercentage);
//        LOGGER.info(diff + " to threshold " + HEAT_THRESHOLD);
        if (resourcePercentage != previousResourcePercentage) {
            if (resourcePercentage < previousResourcePercentage) {
                // we're falling
                if (!falling) {
                    // note the last time we saw an increase;
                    lastTimeSeeingRise = previousResourceChange;
                    highestValue = previousResourcePercentage;
                }
                falling = true;
            }
            if (resourcePercentage > previousResourcePercentage) {
                // we're rising
                falling = false;
            }
            // wylicz pochodną aka oblicz spadek w przedziale czasu.
            // if spadek > jakaś wartość - overheat
            // otherwise - casual cooling down.
            // wykorzystaj że w wiekszym przedziale czasu niz jeden tick wynik sie uspójni, niezaleznie od tick rate
            if (falling) {
                final LocalTime now = LocalTime.now();
                final long durationOfChange = Duration.between(lastTimeSeeingRise, now).toMillis();

                final int delta = highestValue - resourcePercentage;
                double magicNumber = delta * 1000.0 / durationOfChange;
                System.out.println(magicNumber);
                // rumble cooldown when overheat is 16, but let's have some margin
                if (magicNumber > 13.0) {
                    System.out.println("KW says - rumble overheats");
                    // color red and pulse or whatever
                } else {
                    // rumble is cooling down normally - ~10 heat per sec, 9.6 according to math in this program :D
                    System.out.println("KW says - rumble cools down normally");
                    // color according to heat status
                }
            } else {
                System.out.println("KW says - rumble dances in the fountain");
                // color according to heat status
            }

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
