package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class ActivePlayerKillAnimation extends AnimatedFrame {
    private static final List<RzKey> KILL_CROSS_1_KEYS = Arrays.asList(RzKey.RZKEY_L,
            RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8, RzKey.RZKEY_OEM_4, RzKey.RZKEY_OEM_10);
    private static final List<RzKey> KILL_CROSS_2_KEYS = Arrays.asList(RzKey.RZKEY_OEM_9,
            RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_5, RzKey.RZKEY_P, RzKey.RZKEY_OEM_11);

    public ActivePlayerKillAnimation() {
        addAnimationFrame(new SimpleFrame(KILL_CROSS_1_KEYS, Color.YELLOW));
        addAnimationFrame(new SimpleFrame(KILL_CROSS_2_KEYS, Color.YELLOW));
    }
}
