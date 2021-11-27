package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.*;

/**
 * Single animation frame with one {@link Frame}
 */
public class SimpleFrame implements IFrame {
    private final Map<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);
    private boolean hasFrame = true;

    /**
     * Create an empty frame
     */
    public SimpleFrame() {
    }

    /**
     * Create a frame with all keys in the same color
     *
     * @param color {@link Color} of the fill
     */
    public SimpleFrame(Color color) {
        Arrays.stream(RzKey.values()).forEach(rzKey -> keysToColors.put(rzKey, color));
    }

    /**
     * Create a frame with one key in provided color
     *
     * @param rzKey {@link RzKey} to color
     * @param color {@link Color} of the fill
     */
    public SimpleFrame(RzKey rzKey, Color color) {
        keysToColors.put(rzKey, color);
    }

    /**
     * Create a frame with all provided keys in the same color
     *
     * @param rzKeys collection of {@link RzKey} to color
     * @param color  {@link Color} of the fill
     */
    public SimpleFrame(Collection<RzKey> rzKeys, Color color) {
        StaticColor nextColor = color.getColor();
        rzKeys.forEach(rzKey -> keysToColors.put(rzKey, nextColor));
    }

    /**
     * Create a frame from {@link RzKey} to {@link Color} map
     *
     * @param keysToColors {@link Map} of keys and corresponding fill color
     */
    public SimpleFrame(Map<RzKey, ? extends Color> keysToColors) {
        this.keysToColors.putAll(keysToColors);
    }

    /**
     * Check if frame is available
     *
     * @return true if {@link #getFrame} hasn't been called yet,
     * false otherwise
     */
    @Override
    public boolean hasFrame() {
        return hasFrame;
    }

    /**
     * Return the frame
     *
     * @return the single {@link Frame}
     * @throws NoSuchElementException when there is no frames available
     */
    @Override
    public Frame getFrame() {
        if (hasFrame) {
            hasFrame = false;
            return new Frame(keysToColors);
        } else {
            throw new NoSuchElementException("Animation does not have any frames to return");
        }
    }
}
