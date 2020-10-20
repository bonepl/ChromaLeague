package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.hud.BreathingColor;
import com.bonepl.chromaleague.hud.PredefinedKeySets;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;

public class DragonSoulAnimation extends AnimatedFrame {

    private BreathingColor dragonColor;

    @Override
    public synchronized Frame getFrame() {
        if (!shouldAnimateDragonSoul()) {
            return new Frame();
        }
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private boolean shouldAnimateDragonSoul() {
        if (dragonColor == null) {
            if (GameStateHelper.hasDragonSoul()) {
                dragonColor = new BreathingColor(GameStateHelper.getDragonSoulType().getColor());
            }
        } else {
            if (!GameStateHelper.hasDragonSoul()) {
                dragonColor = null;
            }
        }
        return dragonColor != null;
    }

    private void extendAnimation() {
        for (int i = 0; i < 20; i++) {
            withAnimationFrame(2, new Frame(PredefinedKeySets.ARROWS, dragonColor.getNextColor()));
        }
    }
}
