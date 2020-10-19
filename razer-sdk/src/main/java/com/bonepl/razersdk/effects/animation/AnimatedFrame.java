package com.bonepl.razersdk.effects.animation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class AnimatedFrame implements IFrame {
    private final Queue<Frame> frames = new LinkedList<>();

    public AnimatedFrame withAnimationFrame(Frame frame) {
        return withAnimationFrame(1, frame);
    }

    public AnimatedFrame withAnimationFrame(int frameCount, Frame frame) {
        addAnimationFrame(frameCount, frame);
        return this;
    }

    public void addAnimationFrame(Frame frame) {
        addAnimationFrame(1, frame);
    }

    public void addAnimationFrame(int frameCount, Frame frame) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frames.offer(frame));
    }

    @Override
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    @Override
    public Frame getFrame() {
        return frames.poll();
    }
}
