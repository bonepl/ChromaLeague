package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.PredefinedKeySets;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKeyJoiner;

import java.util.concurrent.atomic.AtomicInteger;

public class WinAnimation extends Animation {
    public WinAnimation() {
        AtomicInteger integer = new AtomicInteger(0);
        while (integer.get() < 500) {
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.GREEN));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.WHITE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.BLUE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.YELLOW));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.CYAN));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.ORANGE));
            addToFront(createUpMovingFrame(integer.getAndIncrement(), StaticColor.RED));
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
        addToBack(new SimpleFrame(StaticColor.BLACK));
        return super.getFrame();
    }
}
