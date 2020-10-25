package com.bonepl.chromaleague.hud.parts;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;

public class EventAnimator extends AnimatedFrame {
    private static final AnimatedFrame ANIMATED_FRAME = new AnimatedFrame();

    public static void addAnimation(IFrame frame) {
        while (frame.hasFrame()) {
            ANIMATED_FRAME.addAnimationFrame(frame);
        }
    }

    @Override
    public boolean hasFrame() {
        return ANIMATED_FRAME.hasFrame();
    }

    @Override
    public Frame getFrame() {
        return ANIMATED_FRAME.getFrame();
    }

    public static void stop() {
        ANIMATED_FRAME.clearFrames();
    }
}
