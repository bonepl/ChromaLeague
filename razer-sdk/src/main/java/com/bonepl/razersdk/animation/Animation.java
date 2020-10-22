package com.bonepl.razersdk.animation;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation implements IFrame {
    private final Deque<Queue<SimpleFrame>> frames = new LinkedBlockingDeque<>();

    public void addToFront(IFrame frame) {
        frames.addLast(convertToSimpleFrames(frame));
    }

    public void addToBack(IFrame frame) {
        frames.addFirst(convertToSimpleFrames(frame));
    }

    @Override
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    @Override
    public Frame getFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        frames.stream().map(Queue::remove).forEach(layeredFrame::addFrame);
        frames.removeIf(Queue::isEmpty);
        return layeredFrame.getFrame();
    }

    private static Queue<SimpleFrame> convertToSimpleFrames(IFrame frame) {
        final Queue<SimpleFrame> simpleFrames = new LinkedList<>();
        while (frame.hasFrame()) {
            simpleFrames.add(new SimpleFrame(frame.getFrame().getKeysToColors()));
        }
        return simpleFrames;
    }
}
