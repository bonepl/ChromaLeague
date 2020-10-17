package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.razer.effects.Color;

public class LayeredFrame extends Frame {

    public void addFrame(final Animation animation) {
        addFrame(animation.getNextAnimatedFrame());
    }

    public void addFrame(final AnimatedFrame animatedFrame) {
        addFrame(animatedFrame.getFrame());
    }

    public void addFrame(final Frame frame) {
        frame.getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.BLACK)
                .forEach(ktc -> getKeysToColors().put(ktc.getKey(), ktc.getValue()));
    }
}
