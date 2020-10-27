package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.KilledDragonBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeyJoiner;

import java.util.List;

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
            addAnimationFrame(2, new SimpleFrame(new RzKeyJoiner().with(KilledDragonBar.FIRST_DRAGON_ROW)
                    .with(List.of(RzKey.RZKEY_UP, RzKey.RZKEY_DOWN, RzKey.RZKEY_RIGHT)).with(RzKey.RZKEY_RALT).join(), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RzKey.RZKEY_RSHIFT,
                    RzKey.RZKEY_UP, RzKey.RZKEY_SLASH), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RzKey.RZKEY_ENTER, RzKey.RZKEY_APOSTROPHE), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(List.of(RzKey.RZKEY_BACKSLASH, RzKey.RZKEY_SQUARE_BRACKET_RIGHT), Color.WHITE));
            addAnimationFrame(20, new SimpleFrame());
        }
    }
}
