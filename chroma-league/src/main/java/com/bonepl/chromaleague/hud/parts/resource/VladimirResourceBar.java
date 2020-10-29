package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class VladimirResourceBar extends AnimatedFrame {
    private int previousResourcePercentage;

    @Override
    public Frame getFrame() {
        final int currentResourcePercentage = GameStateHelper.getResourcePercentage();
        if (currentResourcePercentage > previousResourcePercentage) {
            if (currentResourcePercentage < 50) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, Color.WHITE));
            } else {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, Color.YELLOW));
            }
        } else {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, Color.RED));
        }
        previousResourcePercentage = currentResourcePercentage;
        return super.getFrame();
    }
}
