package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.color.StaticColor;

public class RedFuryBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), GameStateHelper.getResourcePercentage(), StaticColor.RED));
        return super.getFrame();
    }
}
