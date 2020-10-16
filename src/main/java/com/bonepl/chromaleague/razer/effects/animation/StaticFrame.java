package com.bonepl.chromaleague.razer.effects.animation;

public class StaticFrame implements Frame {
    private final FramePart framePart;

    public StaticFrame(FramePart framePart) {
        this.framePart = framePart;
    }

    @Override
    public boolean hasNextFrame() {
        return true;
    }

    @Override
    public FramePart getNextFrame() {
        return framePart;
    }
}
