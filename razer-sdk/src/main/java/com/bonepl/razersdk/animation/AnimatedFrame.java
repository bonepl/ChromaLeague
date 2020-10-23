package com.bonepl.razersdk.animation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * Object containing a sequence of {@link SimpleFrame} that build the animation.
 * Each following {@link Frame} is polled by calling {@link #getFrame()}.
 * Speed of the animation will depend on the external scheduler calling {@link #getFrame()}.
 */
public class AnimatedFrame implements IFrame {
    private final Queue<Frame> frames = new LinkedList<>();

    /**
     * Add single animation frame delivered by calling {@link IFrame#getFrame()}
     *
     * @param frame frame added to animation sequence
     */
    public void addAnimationFrame(IFrame frame) {
        addAnimationFrame(1, frame);
    }

    /**
     * Add single animation frame extended by frames count.
     * Having external animation scheduler, this is used to set the length of current's
     * frame display. For example if you get animation frame every 50ms and you want
     * the frame to last 200ms set frameCount to 4.
     *
     * @param frameCount how long should provided frame last (be returned by {@link #getFrame()}
     * @param frame      frame to be added to animation sequence
     */
    public void addAnimationFrame(int frameCount, IFrame frame) {
        if (frameCount < 1) {
            throw new IllegalArgumentException("AnimationFrame requires at least 1 frameCount, was " + frameCount);
        }
        IntStream.range(0, frameCount).forEach(i -> frames.offer(frame.getFrame()));
    }

    /**
     * @return true if there are still frames in the animation sequence,
     * false otherwise
     */
    @Override
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    /**
     * @return next {@link Frame} of the animation
     */
    @Override
    public Frame getFrame() {
        return frames.remove();
    }

    /**
     * Clear the frame sequence, and stop the animation
     */
    public void clearFrames() {
        frames.clear();
    }
}
