package com.bonepl.chromaleague.razer.effects.animation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class MotionFrames implements Frames {
    private final Queue<Frame> frames = new LinkedList<>();

    public MotionFrames withAnimationFrame(Frame frame) {
        return withAnimationFrame(1, frame);
    }

    public MotionFrames withAnimationFrame(int frameCount, Frame frame) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frames.offer(frame));
        return this;
    }

    @Override
    public boolean hasNextFrame() {
        return !frames.isEmpty();
    }

    @Override
    public Frame getNextFrame() {
        return frames.poll();
    }
}
