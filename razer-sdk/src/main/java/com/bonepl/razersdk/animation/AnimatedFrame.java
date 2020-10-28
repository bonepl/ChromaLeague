package com.bonepl.razersdk.animation;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * Object containing a sequence of {@link SimpleFrame} that build the animation.
 * Each following {@link Frame} is polled by calling {@link #getFrame()}.
 * Speed of the animation will depend on the external scheduler calling {@link #getFrame()}.
 */
public class AnimatedFrame implements IFrame {
    public final Queue<Frame> frames = new LinkedList<>();

    /**
     * Add single animation frame delivered by calling {@link IFrame#getFrame()}
     *
     * @param frame frame added to animation sequence
     */
    public final void addAnimationFrame(IFrame frame) {
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
    public final void addAnimationFrame(int frameCount, IFrame frame) {
        if (frameCount > 0) {
            final Frame animationFrame = frame.getFrame();
            IntStream.range(0, frameCount).forEach(i -> frames.offer(animationFrame));
        }
    }

    /**
     * Check if frame is available
     *
     * @return true if there are still frames in the animation sequence,
     * false otherwise
     */
    @Override
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    /**
     * Return the frame
     *
     * @return next {@link Frame} of the animation
     * @throws NoSuchElementException when there is no frames available
     */
    @Override
    public Frame getFrame() {
        if (hasFrame()) {
            return frames.remove();
        } else {
            throw new NoSuchElementException("AnimatedFrame does not have any frames to return");
        }
    }

    /**
     * Clear the frame sequence, and stop the animation
     */
    public final void clearFrames() {
        frames.clear();
    }
}
