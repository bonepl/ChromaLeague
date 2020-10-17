package com.bonepl.razersdk.effects.animation;

import com.bonepl.razersdk.effects.Color;

public class LayeredFrame extends Frame {

    public LayeredFrame withFrame(final Animation animation) {
        withFrame(animation.getNextAnimatedFrame());
        return this;
    }

    public LayeredFrame withFrame(final AnimatedFrame animatedFrame) {
        withFrame(animatedFrame.getFrame());
        return this;
    }

    public LayeredFrame withFrame(final Frame frame) {
        frame.getKeysToColors().entrySet().stream()
                .filter(ktc -> ktc.getValue() != Color.BLACK)
                .forEach(ktc -> getKeysToColors().put(ktc.getKey(), ktc.getValue()));
        return this;
    }
}
