package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;

public class ActivePlayerKillAnimation extends AnimatedFrame {
    public ActivePlayerKillAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, new SimpleFrame(Arrays.asList(RzKey.RZKEY_P, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_11), Color.YELLOW));
            addAnimationFrame(2, new SimpleFrame(Arrays.asList(RzKey.RZKEY_L, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8), Color.YELLOW));
            addAnimationFrame(2, new SimpleFrame(Arrays.asList(RzKey.RZKEY_OEM_9, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_5), Color.YELLOW));
            addAnimationFrame(2, new SimpleFrame(Arrays.asList(RzKey.RZKEY_OEM_10, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_4), Color.YELLOW));
        }
    }
}
