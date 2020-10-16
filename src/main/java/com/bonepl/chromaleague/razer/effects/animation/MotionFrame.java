package com.bonepl.chromaleague.razer.effects.animation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class MotionFrame implements Frame {
    private final Queue<FramePart> frameParts = new LinkedList<>();

    public MotionFrame withAnimationFrame(FramePart framePart) {
        return withAnimationFrame(1, framePart);
    }

    public MotionFrame withAnimationFrame(int frameCount, FramePart framePart) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frameParts.offer(framePart));
        return this;
    }

    @Override
    public boolean hasNextFrame() {
        return !frameParts.isEmpty();
    }

    @Override
    public FramePart getNextFrame() {
        return frameParts.poll();
    }
}
