package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.StaticColor;

public class RenektonFuryBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        final int resourcePercentage = GameStateHelper.getResourcePercentage();
        if (resourcePercentage < 50) {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                    resourcePercentage, StaticColor.WHITE));
        } else {
            addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                    resourcePercentage, StaticColor.RED));
        }
        return super.getFrame();
    }
}
