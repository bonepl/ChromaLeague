package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;

public class RenektonFuryBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        if (resourcePercentage < 50) {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                    resourcePercentage, Color.WHITE));
        } else {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                    resourcePercentage, Color.RED));
        }
        return super.getFrame();
    }
}
