package com.bonepl.razersdk.animation;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Top level class for advanced animations.
 * It provided layering of animations as well as defining
 * frames in front or back of the animation.
 * <br><br>
 * After creating an animation with {@link #getFrame()} any {@link IFrame}
 * implementing class that is part of animation sequence but its {@link IFrame#hasFrame()}
 * returns false - <b>will be removed</b> from animation sequence.
 * <br><br>
 * That means that every {@link SimpleFrame} or {@link LayeredFrame} added to {@link Animation}
 * will be removed after the first call to {@link IFrame#getFrame()}. If you want some static
 * effect to be preserved in animation, extend this class and add your static frames in
 * overridden {@link Animation#getFrame()} like:
 * <pre>
 * {@code @Override}
 *  public Frame getFrame() {
 *      addToBack(new SimpleFrame(Color.BLUE));
 *      return super.getFrame();
 *  }
 * </pre>
 * Note the such code will make animation infinite
 * <br><br>
 * You can also call {@link #addToBack(IFrame)} or {@link #addToFront(IFrame)}
 * to modify the animation on-the-fly (for example reacting to some event)
 */
public class Animation implements IFrame {
    private final Deque<Queue<SimpleFrame>> frames = new LinkedList<>();

    /**
     * Add {@link IFrame} implementing class to the front of the animation
     *
     * @param frame animation class to be added
     */
    public final void addToFront(IFrame frame) {
        frames.addLast(convertToSimpleFrames(frame));
    }

    /**
     * Add {@link IFrame} implementing class to the back of the animation
     *
     * @param frame animation class to be added
     */
    public final void addToBack(IFrame frame) {
        frames.addFirst(convertToSimpleFrames(frame));
    }

    /**
     * Check if frame is available
     *
     * @return true if there is at least one {@link Frame} in the animation sequence,
     * false otherwise
     */
    @Override
    public boolean hasFrame() {
        return !frames.isEmpty();
    }

    /**
     * Create and return {@link LayeredFrame} by getting frames
     * from all objects available in animation sequence.
     *
     * @return {@link LayeredFrame} built from animation sequence
     * @throws NoSuchElementException when there is no frames available
     */
    @Override
    public Frame getFrame() {
        if (hasFrame()) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            frames.stream().map(Queue::remove).forEach(layeredFrame::addFrame);
            frames.removeIf(Queue::isEmpty);
            return layeredFrame.getFrame();
        } else {
            throw new NoSuchElementException("Animation does not have any frames to return");
        }
    }

    private static Queue<SimpleFrame> convertToSimpleFrames(IFrame frame) {
        final Queue<SimpleFrame> simpleFrames = new LinkedList<>();
        while (frame.hasFrame()) {
            simpleFrames.add(new SimpleFrame(frame.getFrame().getKeysToColors()));
        }
        return simpleFrames;
    }
}
