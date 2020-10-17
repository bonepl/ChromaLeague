package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.effects.animation.IFrame;

public class EventAnimation extends AnimatedFrame {

    private static final AnimatedFrame animatedFrame = new AnimatedFrame();

    public synchronized static void addFrames(IFrame frame) {
        while (frame.hasFrame()) {
            animatedFrame.withAnimationFrame(frame.getFrame());
        }
    }

    @Override
    public synchronized boolean hasFrame() {
        return animatedFrame.hasFrame();
    }

    @Override
    public synchronized Frame getFrame() {
        return animatedFrame.getFrame();
    }
}
