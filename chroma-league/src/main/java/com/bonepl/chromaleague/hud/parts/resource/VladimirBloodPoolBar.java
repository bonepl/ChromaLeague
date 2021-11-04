package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.color.StaticColor;

public class VladimirBloodPoolBar extends AnimatedFrame {
    private int previousResourcePercentage;

    @Override
    public Frame getFrame() {
        final int currentResourcePercentage = GameStateHelper.getResourcePercentage();
        if (currentResourcePercentage > previousResourcePercentage) {
            if (currentResourcePercentage < 50) {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, StaticColor.WHITE));
            } else {
                addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, StaticColor.YELLOW));
            }
        } else {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), currentResourcePercentage, StaticColor.RED));
        }
        previousResourcePercentage = currentResourcePercentage;
        return super.getFrame();
    }
}
