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

public class LoseAnimation extends Animation {
    private int red;
    private int redStep = 50;

    public LoseAnimation() {
        AtomicInteger integer = new AtomicInteger(0);
        while (integer.get() < 500) {
            addToFront(createDownMovingFrame(integer.getAndIncrement(), new StaticColor(getNewRedValue(), 0, 0)));
        }
    }

    private static AnimatedFrame createDownMovingFrame(int delay, Color color) {
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        if (delay > 0) {
            animatedFrame.addAnimationFrame(delay * 13, new SimpleFrame());
        }
        animatedFrame.addAnimationFrame(3, new SimpleFrame(PredefinedKeySets.BLACKWIDOW_FIRST_ROW, color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_SECOND_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_FIRST_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_THIRD_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_SECOND_ROW).with(PredefinedKeySets.BLACKWIDOW_FIRST_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_THIRD_ROW).with(PredefinedKeySets.BLACKWIDOW_SECOND_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW).with(PredefinedKeySets.BLACKWIDOW_THIRD_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW).with(PredefinedKeySets.BLACKWIDOW_FOURTH_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(new RzKeyJoiner().with(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW)
                .with(PredefinedKeySets.BLACKWIDOW_FIFTH_ROW).join(), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(PredefinedKeySets.BLACKWIDOW_SIXTH_ROW, color));
        return animatedFrame;
    }

    public final int getNewRedValue() {
        if (red + redStep >= 200 || red + redStep < 0) {
            redStep = Math.negateExact(redStep);
        }
        red += redStep;
        return red;
    }

    @Override
    public Frame getFrame() {
        addToBack(new SimpleFrame(StaticColor.BLACK));
        return super.getFrame();
    }
}
