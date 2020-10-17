package com.bonepl.chromaleague.razer.effects.animation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class AnimatedFrame {
    private final Queue<FramePart> frameParts = new LinkedList<>();

    public AnimatedFrame withAnimationFrame(FramePart framePart) {
        return withAnimationFrame(1, framePart);
    }

    public AnimatedFrame withAnimationFrame(int frameCount, FramePart framePart) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frameParts.offer(framePart));
        return this;
    }

    public boolean hasNextFrame() {
        return !frameParts.isEmpty();
    }

    public FramePart getNextFrame() {
        return frameParts.poll();
    }
}
