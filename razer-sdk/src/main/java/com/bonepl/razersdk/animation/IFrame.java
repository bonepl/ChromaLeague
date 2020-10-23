package com.bonepl.razersdk.animation;

/**
 * Single animation frame interface
 */
public interface IFrame {
    /**
     * @return true if object has an animation frame to draw
     */
    boolean hasFrame();

    /**
     * @return next animation {@link Frame} to draw
     */
    Frame getFrame();
}
