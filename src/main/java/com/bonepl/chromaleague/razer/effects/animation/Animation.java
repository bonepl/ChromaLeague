package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class Animation {
    private final Deque<Frame> frames = new LinkedBlockingDeque<>();

    public void addToFront(Frame frame) {
        this.frames.addLast(frame);
    }

    public void addToBack(Frame frame) {
        this.frames.addFirst(frame);
    }

    public LayeredCustomEffect getNextFrame() {
        final LayeredCustomEffect layeredCustomEffect = new LayeredCustomEffect();
        if (!frames.isEmpty()) {
            final Iterator<Frame> it = frames.iterator();
            while (it.hasNext()) {
                final Frame nextFrame = it.next();
                if (!nextFrame.hasNextFrame()) {
                    it.remove();
                } else {
                    layeredCustomEffect.addCustomKeyboardEffect(nextFrame.getNextFrame());
                }
                if (nextFrame instanceof StaticFrame) {
                    it.remove();
                }
            }
        }
        return layeredCustomEffect;
    }
}
