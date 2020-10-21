package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Frame;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.*;
import static org.apache.commons.collections4.ListUtils.sum;

public class LoseAnimation extends Animation {
    private int red = 0;
    private int redStep = 50;

    public LoseAnimation() {
        int i = 0;
        while (i < 500) {
            this.addToFront(createDownMovingFrame(i++, new Color(getRed(), 0, 0)));
        }
    }

    private static AnimatedFrame createDownMovingFrame(int delay, Color color) {
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        if (delay > 0) {
            animatedFrame.withAnimationFrame(delay * 13, new Frame());
        }
        animatedFrame.withAnimationFrame(3, new Frame(BLACKWIDOW_FIRST_ROW, color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(BLACKWIDOW_SECOND_ROW, BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(sum(BLACKWIDOW_THIRD_ROW, BLACKWIDOW_SECOND_ROW), BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(sum(BLACKWIDOW_FOURTH_ROW, BLACKWIDOW_THIRD_ROW), BLACKWIDOW_SECOND_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(sum(BLACKWIDOW_FIFTH_ROW, BLACKWIDOW_FOURTH_ROW), BLACKWIDOW_THIRD_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), BLACKWIDOW_FOURTH_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), color));
        animatedFrame.withAnimationFrame(3, new Frame(BLACKWIDOW_SIXTH_ROW, color));
        return animatedFrame;
    }

    public int getRed() {
        if ((red + redStep) >= 200 || (red + redStep) < 0) {
            redStep = Math.negateExact(redStep);
        }
        red = red + redStep;
        return red;
    }

    @Override
    public synchronized Frame getFrame() {
        addToBack(new Frame(Color.BLACK));
        return super.getFrame();
    }
}
