package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.sdk.RzKeyJoiner;
import com.bonepl.chromaleague.hud.PredefinedKeySets;
import com.bonepl.razersdk.animation.*;

import java.util.concurrent.atomic.AtomicInteger;

public class WinAnimation extends Animation {
    public WinAnimation() {
        AtomicInteger integer = new AtomicInteger(0);
        while (integer.get() < 500) {
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.GREEN));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.WHITE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.BLUE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.YELLOW));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.CYAN));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.ORANGE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), Color.RED));
        }
    }

    private static AnimatedFrame createUpMovingFrame(int delay, Color color) {
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        if (delay > 0) {
            animatedFrame.addAnimationFrame(delay << 2, new SimpleFrame());
        }
        animatedFrame.addAnimationFrame(new SimpleFrame(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW, color));
        animatedFrame.addAnimationFrame(new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW).with(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW).join(), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW).with(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW).join(), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW).with(PredefinedKeySets.BLACKWIDOW_THIRD_ROW).join(), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_THIRD_ROW).with(PredefinedKeySets.BLACKWIDOW_SECOND_ROW).join(), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_SECOND_ROW).with(PredefinedKeySets.BLACKWIDOW_FIRST_ROW).join(), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(PredefinedKeySets.BLACKWIDOW_FIRST_ROW, color));
        return animatedFrame;
    }

    @Override
    public Frame getFrame() {
        addToBack(new SimpleFrame(Color.BLACK));
        return super.getFrame();
    }
}
