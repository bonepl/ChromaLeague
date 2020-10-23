package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.*;

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
            animatedFrame.addAnimationFrame(delay * 13, new SimpleFrame());
        }
        animatedFrame.addAnimationFrame(3, new SimpleFrame(BLACKWIDOW_FIRST_ROW, color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(BLACKWIDOW_SECOND_ROW, BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(sum(BLACKWIDOW_THIRD_ROW, BLACKWIDOW_SECOND_ROW), BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(sum(BLACKWIDOW_FOURTH_ROW, BLACKWIDOW_THIRD_ROW), BLACKWIDOW_SECOND_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(sum(BLACKWIDOW_FIFTH_ROW, BLACKWIDOW_FOURTH_ROW), BLACKWIDOW_THIRD_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), BLACKWIDOW_FOURTH_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), color));
        animatedFrame.addAnimationFrame(3, new SimpleFrame(BLACKWIDOW_SIXTH_ROW, color));
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
        addToBack(new SimpleFrame(Color.BLACK));
        return super.getFrame();
    }
}
