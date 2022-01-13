package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.StaticColor;

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
