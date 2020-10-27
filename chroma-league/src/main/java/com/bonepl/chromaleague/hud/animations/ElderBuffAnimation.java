package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.KilledDragonBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKeyJoiner;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class ElderBuffAnimation extends AnimatedFrame {

    @Override
    public Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        for (int i = 0; i < 10; i++) {
            addAnimationFrame(2, new SimpleFrame(new RzKeyJoiner()
                    .with(KilledDragonBar.FIRST_DRAGON_ROW).with(RZKEY_RALT)
                    .with(RZKEY_UP, RZKEY_DOWN, RZKEY_RIGHT).join(), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_RSHIFT,
                    RZKEY_UP, RZKEY_SLASH), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_ENTER, RZKEY_APOSTROPHE), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_BACKSLASH, RZKEY_SQUARE_BRACKET_RIGHT), Color.WHITE));
            addAnimationFrame(20, new SimpleFrame());
        }
    }
}
