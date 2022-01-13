package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.colors.CLColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;

public class ManaBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                GameStateHelper.getResourcePercentage(), CLColor.MANA));
        return super.getFrame();
    }
}
