package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.KilledDragonBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;
import org.apache.commons.collections4.ListUtils;

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
            withAnimationFrame(2, new Frame(ListUtils.sum(KilledDragonBar.FIRST_DRAGON_ROW,
                    Arrays.asList(RzKey.RZKEY_UP, RzKey.RZKEY_DOWN, RzKey.RZKEY_RIGHT)), Color.WHITE));
            withAnimationFrame(2, new Frame(Arrays.asList(RzKey.RZKEY_RSHIFT,
                    RzKey.RZKEY_UP), Color.WHITE));
            withAnimationFrame(2, new Frame(RzKey.RZKEY_ENTER, Color.WHITE));
            withAnimationFrame(2, new Frame(RzKey.RZKEY_OEM_6, Color.WHITE));
            withAnimationFrame(20, new Frame());
        }
    }
}
