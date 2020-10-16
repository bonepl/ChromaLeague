package com.bonepl.chromaleague.razer.effects.animation;

import java.util.function.Supplier;

public class LiveFrames implements Frames {

    private final Supplier<Frame> frameSupplier;

    public LiveFrames(Supplier<Frame> frameSupplier) {
        this.frameSupplier = frameSupplier;
    }

    @Override
    public boolean hasNextFrame() {
        return true;
    }

    @Override
    public Frame getNextFrame() {
        return frameSupplier.get();
    }
}
