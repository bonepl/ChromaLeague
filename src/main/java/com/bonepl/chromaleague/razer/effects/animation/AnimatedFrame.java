package com.bonepl.chromaleague.razer.effects.animation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class AnimatedFrame implements IFrame {
    Logger logger = LogManager.getLogger();
    private final Queue<Frame> frames = new LinkedList<>();

    public AnimatedFrame withAnimationFrame(Frame frame) {
        return withAnimationFrame(1, frame);
    }

    public AnimatedFrame withAnimationFrame(int frameCount, Frame frame) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frames.offer(frame));
        return this;
    }

    @Override
    public synchronized boolean hasFrame() {
        return !frames.isEmpty();
    }

    @Override
    public synchronized Frame getFrame() {
        return frames.poll();
    }
}
