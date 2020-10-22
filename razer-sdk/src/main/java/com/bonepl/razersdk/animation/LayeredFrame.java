package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey;

import java.util.EnumMap;
import java.util.Map;

public class LayeredFrame implements IFrame {
    private final Map<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);
    private boolean hasFrame = true;

    public void addFrame(final IFrame frame) {
        frame.getFrame().getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.NONE)
                .forEach(ktc -> keysToColors.put(ktc.getKey(), ktc.getValue()));
    }

    @Override
    public boolean hasFrame() {
        return hasFrame;
    }

    @Override
    public Frame getFrame() {
        hasFrame = false;
        return new Frame(keysToColors);
    }
}
