package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class ActivePlayerKillAnimation extends AnimatedFrame {
    private static final List<RzKey> BACKGROUND_KEYS = Arrays.asList(
            RzKey.RZKEY_P, RzKey.RZKEY_SQUARE_BRACKET_LEFT, RzKey.RZKEY_SQUARE_BRACKET_RIGHT,
            RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON, RzKey.RZKEY_APOSTROPHE,
            RzKey.RZKEY_COMA, RzKey.RZKEY_DOT, RzKey.RZKEY_SLASH);

    public ActivePlayerKillAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_P, RzKey.RZKEY_SEMICOLON, RzKey.RZKEY_SLASH)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON, RzKey.RZKEY_APOSTROPHE)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_COMA, RzKey.RZKEY_SEMICOLON, RzKey.RZKEY_SQUARE_BRACKET_RIGHT)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_DOT, RzKey.RZKEY_SEMICOLON, RzKey.RZKEY_SQUARE_BRACKET_LEFT)));
        }
    }

    private static LayeredFrame withBackground(List<RzKey> rzKeys) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(BACKGROUND_KEYS, Background.BACKGROUND_COLOR));
        layeredFrame.addFrame(new SimpleFrame(rzKeys, Color.RED));
        return layeredFrame;
    }
}
