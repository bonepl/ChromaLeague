package com.bonepl.chromaleague.razer.effects.animation;

public class StaticFrames implements Frames {
    private final Frame frame;

    public StaticFrames(Frame frame) {
        this.frame = frame;
    }

    @Override
    public boolean hasNextFrame() {
        return true;
    }

    @Override
    public Frame getNextFrame() {
        return frame;
    }
}
