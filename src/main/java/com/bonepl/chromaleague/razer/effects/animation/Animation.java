package com.bonepl.chromaleague.razer.effects.animation;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation {
    private final Deque<AnimatedFrame> frames = new LinkedBlockingDeque<>();

    public void addToFront(AnimatedFrame frame) {
        this.frames.addLast(frame);
    }

    public void addToBack(AnimatedFrame frame) {
        this.frames.addFirst(frame);
    }

    public LayeredFrame getNextAnimatedFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        if (!frames.isEmpty()) {
            final Iterator<AnimatedFrame> it = frames.iterator();
            while (it.hasNext()) {
                final AnimatedFrame nextFrame = it.next();
                if (!nextFrame.hasNextFrame()) {
                    it.remove();
                } else {
                    layeredFrame.addFrame(nextFrame.getNextFrame());
                }
            }
        }
        return layeredFrame;
    }
}
