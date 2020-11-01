package com.bonepl.chromaleague.hud.parts;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.animation.SimpleFrame;

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
