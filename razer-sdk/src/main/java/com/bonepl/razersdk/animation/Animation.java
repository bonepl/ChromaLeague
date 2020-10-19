package com.bonepl.razersdk.animation;

import java.util.Deque;
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
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    @Override
    public Frame getFrame() {
        final LayeredFrame layeredFrame = new LayeredFrame();
        frames.stream().map(IFrame::getFrame).forEach(layeredFrame::addFrame);
        frames.removeIf(iFrame -> !iFrame.hasFrame());
        return layeredFrame;
    }
}
