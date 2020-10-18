package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Animation;
import com.bonepl.razersdk.effects.animation.Frame;
import org.apache.commons.collections4.ListUtils;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.*;

public class WinAnimation extends Animation {
    public WinAnimation() {
        int i = 0;
        while (i < 500) {
            this.addToFront(createMovingFrame(i++, Color.GREEN));
            this.addToFront(createMovingFrame(i++, Color.WHITE));
            this.addToFront(createMovingFrame(i++, Color.BLUE));
            this.addToFront(createMovingFrame(i++, Color.YELLOW));
            this.addToFront(createMovingFrame(i++, Color.CYAN));
            this.addToFront(createMovingFrame(i++, Color.ORANGE));
            this.addToFront(createMovingFrame(i++, Color.RED));
        }
    }

    private static AnimatedFrame createMovingFrame(int delay, Color color) {
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        if (delay > 0) {
            animatedFrame.withAnimationFrame(delay * 4, new Frame());
        }
        animatedFrame.withAnimationFrame(new Frame(BLACKWIDOW_SIXTH_ROW, color));
        animatedFrame.withAnimationFrame(new Frame(ListUtils.sum(BLACKWIDOW_SIXTH_ROW, BLACKWIDOW_FIFTH_ROW), color));
        animatedFrame.withAnimationFrame(new Frame(ListUtils.sum(BLACKWIDOW_FIFTH_ROW, BLACKWIDOW_FOURTH_ROW), color));
        animatedFrame.withAnimationFrame(new Frame(ListUtils.sum(BLACKWIDOW_FOURTH_ROW, BLACKWIDOW_THIRD_ROW), color));
        animatedFrame.withAnimationFrame(new Frame(ListUtils.sum(BLACKWIDOW_THIRD_ROW, BLACKWIDOW_SECOND_ROW), color));
        animatedFrame.withAnimationFrame(new Frame(ListUtils.sum(BLACKWIDOW_SECOND_ROW, BLACKWIDOW_FIRST_ROW), color));
        animatedFrame.withAnimationFrame(new Frame(BLACKWIDOW_FIRST_ROW, color));
        return animatedFrame;
    }
}
