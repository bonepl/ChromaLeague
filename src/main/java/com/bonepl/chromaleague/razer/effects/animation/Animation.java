package com.bonepl.chromaleague.razer.effects.animation;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation {
    private final Deque<IFrame> frames = new LinkedBlockingDeque<>();

    public void addToFront(Frame frame) {
        this.frames.addLast(frame);
    }

    public void addToFront(AnimatedFrame frame) {
        this.frames.addLast(frame);
    }

    public void addToBack(AnimatedFrame frame) {
        this.frames.addFirst(frame);
    }

    public LayeredFrame getNextAnimatedFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        if (!frames.isEmpty()) {
            final Iterator<IFrame> it = frames.iterator();
            while (it.hasNext()) {
                final IFrame frame = it.next();
                if (!frame.hasFrame()) {
                    it.remove();
                } else {
                    layeredFrame.withFrame(frame.getFrame());
                }
            }
        }
        return layeredFrame;
    }
}
