package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.*;
import org.apache.commons.collections4.ListUtils;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.*;

public class WinAnimation extends Animation {
    public WinAnimation() {
        int i = 0;
        while (i < 500) {
            this.addToFront(createUpMovingFrame(i++, Color.GREEN));
            this.addToFront(createUpMovingFrame(i++, Color.WHITE));
            this.addToFront(createUpMovingFrame(i++, Color.BLUE));
            this.addToFront(createUpMovingFrame(i++, Color.YELLOW));
            this.addToFront(createUpMovingFrame(i++, Color.CYAN));
            this.addToFront(createUpMovingFrame(i++, Color.ORANGE));
            this.addToFront(createUpMovingFrame(i++, Color.RED));
        }
    }

    private static AnimatedFrame createUpMovingFrame(int delay, Color color) {
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        if (delay > 0) {
            animatedFrame.addAnimationFrame(delay * 4, new SimpleFrame());
        }
        animatedFrame.addAnimationFrame(new SimpleFrame(BLACKWIDOW_SIXTH_ROW, color));
        animatedFrame.addAnimationFrame(new SimpleFrame(ListUtils.sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(ListUtils.sum(BLACKWIDOW_FIFTH_ROW, BLACKWIDOW_FOURTH_ROW), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(ListUtils.sum(BLACKWIDOW_FOURTH_ROW, BLACKWIDOW_THIRD_ROW), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(ListUtils.sum(BLACKWIDOW_THIRD_ROW, BLACKWIDOW_SECOND_ROW), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(ListUtils.sum(BLACKWIDOW_SECOND_ROW, BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.addAnimationFrame(new SimpleFrame(BLACKWIDOW_FIRST_ROW, color));
        return animatedFrame;
    }

    @Override
    public synchronized Frame getFrame() {
        addToBack(new SimpleFrame(Color.BLACK));
        return super.getFrame();
    }
}
