package com.bonepl.chromaleague.hud.parts;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;

public class EventAnimator extends AnimatedFrame {
    private static final AnimatedFrame EVENT_ANIMATION = new AnimatedFrame();

    public static void addAnimation(IFrame frame) {
        while (frame.hasFrame()) {
            EVENT_ANIMATION.addAnimationFrame(frame);
        }
    }

    @Override
    public boolean hasFrame() {
        return EVENT_ANIMATION.hasFrame();
    }

    @Override
    public Frame getFrame() {
        return EVENT_ANIMATION.getFrame();
    }

    public static void stop() {
        EVENT_ANIMATION.clearFrames();
    }
}
