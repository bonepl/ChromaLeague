package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation {
    private final Deque<Frames> frames = new LinkedBlockingDeque<>();

    public void addToFront(Frames frames) {
        this.frames.addLast(frames);
    }

    public void addToBack(Frames frames) {
        this.frames.addFirst(frames);
    }

    public LayeredCustomEffect getNextFrame() {
        final LayeredCustomEffect layeredCustomEffect = new LayeredCustomEffect();
        final Iterator<Frames> it = frames.iterator();
        while (it.hasNext()) {
            final Frames nextFrames = it.next();
            if (!nextFrames.hasNextFrame()) {
                it.remove();
            } else {
                layeredCustomEffect.addCustomKeyboardEffect(nextFrames.getNextFrame());
            }
        }
        return layeredCustomEffect;
    }
}
