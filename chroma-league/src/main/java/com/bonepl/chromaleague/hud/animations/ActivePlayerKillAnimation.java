package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class ActivePlayerKillAnimation extends AnimatedFrame {
    private static final List<RzKey> BACKGROUND_KEYS = List.of(
            RZKEY_P, RZKEY_SQUARE_BRACKET_LEFT, RZKEY_SQUARE_BRACKET_RIGHT,
            RZKEY_L, RZKEY_SEMICOLON, RZKEY_APOSTROPHE,
            RZKEY_COMA, RZKEY_DOT, RZKEY_SLASH);

    public ActivePlayerKillAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, withBackground(List.of(RZKEY_O, RZKEY_L, RZKEY_DOT)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_K, RZKEY_L, RZKEY_SEMICOLON)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_M, RZKEY_L, RZKEY_SQUARE_BRACKET_LEFT)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_COMA, RZKEY_L, RZKEY_P)));
        }
    }

    private static LayeredFrame withBackground(List<RzKey> rzKeys) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(BACKGROUND_KEYS, Background.BACKGROUND_COLOR));
        layeredFrame.addFrame(new SimpleFrame(rzKeys, Color.RED));
        return layeredFrame;
    }
}
