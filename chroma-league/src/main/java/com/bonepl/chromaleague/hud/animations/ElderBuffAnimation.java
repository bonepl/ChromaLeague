package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.KeysJoiner;
import com.bonepl.chromaleague.hud.parts.KilledDragonBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;

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
            addAnimationFrame(2, new SimpleFrame(new KeysJoiner().with(KilledDragonBar.FIRST_DRAGON_ROW)
                    .with(Arrays.asList(RzKey.RZKEY_UP, RzKey.RZKEY_DOWN, RzKey.RZKEY_RIGHT)).join(), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(Arrays.asList(RzKey.RZKEY_RSHIFT,
                    RzKey.RZKEY_UP), Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(RzKey.RZKEY_ENTER, Color.WHITE));
            addAnimationFrame(2, new SimpleFrame(RzKey.RZKEY_BACKSLASH, Color.WHITE));
            addAnimationFrame(20, new SimpleFrame());
        }
    }
}
