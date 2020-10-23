package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class used to simulate layering of multiple {@link Frame} classes to create one {@link SimpleFrame} to return.
 * Newest frame always overwrites previous ones. Keys with color {@link Color#NONE}
 * are treated as transparent.
 */
public class LayeredFrame implements IFrame {
    private final Map<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);
    private boolean hasFrame = true;

    /**
     * Add next frame that will be provided by {@link IFrame#getFrame()}
     * and layers is on top of the previous frames (if exist).
     *
     * @param frame object containing {@link Frame}
     */
    public void addFrame(final IFrame frame) {
        frame.getFrame().getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.NONE)
                .forEach(ktc -> keysToColors.put(ktc.getKey(), ktc.getValue()));
    }

    /**
     * @return true if {@link #getFrame} hasn't been called yet,
     * false otherwise
     */
    @Override
    public boolean hasFrame() {
        return hasFrame;
    }

    /**
     * @return the single {@link Frame}
     */
    @Override
    public Frame getFrame() {
        hasFrame = false;
        return new Frame(keysToColors);
    }
}
