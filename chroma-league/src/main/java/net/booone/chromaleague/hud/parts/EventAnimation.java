package net.booone.chromaleague.hud.parts;

import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.IFrame;
import net.booone.razersdk.animation.SimpleFrame;

public class EventAnimation extends AnimatedFrame {
    public void addAnimation(IFrame frame) {
        while (frame.hasFrame()) {
            addAnimationFrame(frame);
        }
    }

    @Override
    public Frame getFrame() {
        if (hasFrame()) {
            return super.getFrame();
        }
        return new SimpleFrame().getFrame();
    }
}
