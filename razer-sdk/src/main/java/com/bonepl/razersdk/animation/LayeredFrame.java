package com.bonepl.razersdk.animation;

public class LayeredFrame extends Frame {

    public void addFrame(final IFrame frame) {
        frame.getFrame().getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.NONE)
                .forEach(ktc -> getKeysToColors().put(ktc.getKey(), ktc.getValue()));
    }
}
