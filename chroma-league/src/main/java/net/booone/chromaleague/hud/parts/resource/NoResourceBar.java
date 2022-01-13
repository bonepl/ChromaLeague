package net.booone.chromaleague.hud.parts.resource;

import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;

public class NoResourceBar extends AnimatedFrame {

    @Override
    public Frame getFrame() {
        addAnimationFrame(new SimpleFrame());
        return super.getFrame();
    }
}
