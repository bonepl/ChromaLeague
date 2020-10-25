package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class ActivePlayerAssistAnimation extends AnimatedFrame {
    private static final List<RzKey> BACKGROUND_KEYS = Arrays.asList(
            RzKey.RZKEY_P, RzKey.RZKEY_OEM_4, RzKey.RZKEY_OEM_5,
            RzKey.RZKEY_L, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8,
            RzKey.RZKEY_OEM_9, RzKey.RZKEY_OEM_10, RzKey.RZKEY_OEM_11);

    public ActivePlayerAssistAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_P, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_11)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_L, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_OEM_9, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_5)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_OEM_10, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_4)));
        }
    }

    private static LayeredFrame withBackground(List<RzKey> rzKeys) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(BACKGROUND_KEYS, Background.BACKGROUND_COLOR));
        layeredFrame.addFrame(new SimpleFrame(rzKeys, Color.YELLOW));
        return layeredFrame;
    }
}
