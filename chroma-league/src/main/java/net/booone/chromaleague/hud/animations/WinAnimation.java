package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.PredefinedKeySets;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.ProgressiveRzKeySelector;

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
        ProgressiveRzKeySelector progressiveRzKeySelector = buildMovingFrame();
        for (int i = 0; i < progressiveRzKeySelector.getTotalSteps(); i++) {
            animatedFrame.addAnimationFrame(new SimpleFrame(progressiveRzKeySelector.getNextPart(), color));

        }
        return animatedFrame;
    }

    private static ProgressiveRzKeySelector buildMovingFrame() {
        return new ProgressiveRzKeySelector.Builder()
                .addPart(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW)
                .addPart(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW)
                .addPart(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW)
                .addPart(PredefinedKeySets.BLACKWIDOW_THIRD_ROW)
                .addPart(PredefinedKeySets.BLACKWIDOW_SECOND_ROW)
                .addPart(PredefinedKeySets.BLACKWIDOW_FIRST_ROW)
                .withLength(2)
                .build();
    }

    @Override
    public Frame getFrame() {
        addToBack(new SimpleFrame(StaticColor.BLACK));
        return super.getFrame();
    }
}
