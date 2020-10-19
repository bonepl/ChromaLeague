package com.bonepl.razersdk.effects.animation;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation implements IFrame {
    private final Deque<IFrame> frames = new LinkedBlockingDeque<>();

    public void addToFront(IFrame frame) {
        this.frames.addLast(frame);
    }

    public void addToBack(IFrame frame) {
        this.frames.addFirst(frame);
    }

    @Override
    public synchronized boolean hasFrame() {
        return !frames.isEmpty();
    }

    @Override
    public synchronized Frame getFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        final Iterator<IFrame> it = frames.iterator();
        while (it.hasNext()) {
            final IFrame frame = it.next();
            layeredFrame.withFrame(frame.getFrame());
            if (!frame.hasFrame()) {
                it.remove();
            }
        }
        return layeredFrame;
    }
}
