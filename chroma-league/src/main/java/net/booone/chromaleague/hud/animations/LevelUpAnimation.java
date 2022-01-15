package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.ProgressiveRzKeySelector;

import static net.booone.razersdk.sdk.RzKey.*;

public class LevelUpAnimation extends Animation {
    private int previousLevel;

    @Override
    public Frame getFrame() {
        addLevelUpAnimationIfNeeded();
        if (hasFrame()) {
            return super.getFrame();
        }
        return new SimpleFrame().getFrame();
    }

    private void addLevelUpAnimationIfNeeded() {
        if (GameStateHelper.getLevel() > previousLevel) {
            levelUp();
            previousLevel = GameStateHelper.getLevel();
        }
    }

    void levelUp() {
        ProgressiveRzKeySelector levelUpSelector = new ProgressiveRzKeySelector.Builder()
                .addPart(RZKEY_MACRO5, RZKEY_LCTRL, RZKEY_LWIN)
                .addPart(RZKEY_MACRO4, RZKEY_LSHIFT)
                .addPart(RZKEY_MACRO3, RZKEY_CAPSLOCK)
                .addPart(RZKEY_MACRO2, RZKEY_TAB)
                .addPart(RZKEY_MACRO1, RZKEY_TILDE)
                .addPart(RZKEY_ESC)
                .withLength(3)
                .build();
        final AnimatedFrame frame = new AnimatedFrame();
        for (int i = 0; i < levelUpSelector.getTotalSteps(); i++) {
            frame.addAnimationFrame(2, new SimpleFrame(levelUpSelector.getNextPart(), StaticColor.YELLOW));
        }
        addToFront(frame);
    }
}
