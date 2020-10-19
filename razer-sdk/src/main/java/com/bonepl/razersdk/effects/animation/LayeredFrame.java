package com.bonepl.razersdk.effects.animation;

import com.bonepl.razersdk.effects.Color;

public class LayeredFrame extends Frame {

    public void addFrame(final IFrame frame) {
        frame.getFrame().getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.NONE)
                .forEach(ktc -> getKeysToColors().put(ktc.getKey(), ktc.getValue()));
    }
}
