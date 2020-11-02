package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.tasks.MainTask;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class RumbleHeatBar extends AnimatedFrame {
    private static final double HEAT_THRESHOLD = 13.0 * MainTask.ACTIVE_PLAYER_FETCH_DELAY / 1000;
    private int previousResourcePercentage;

    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        Color color;
        if (resourcePercentage >= previousResourcePercentage) {
            if (resourcePercentage >= 50) {
                color = Color.YELLOW;
            } else {
                color = Color.WHITE;
            }
        } else {
            final int diff = previousResourcePercentage - resourcePercentage;
            if (diff < HEAT_THRESHOLD) {
                if (resourcePercentage >= 50) {
                    color = Color.YELLOW;
                } else {
                    color = Color.WHITE;
                }
            } else {
                color = Color.RED;
            }
        }
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), resourcePercentage, color));
        previousResourcePercentage = resourcePercentage;
        return super.getFrame();
    }
}
