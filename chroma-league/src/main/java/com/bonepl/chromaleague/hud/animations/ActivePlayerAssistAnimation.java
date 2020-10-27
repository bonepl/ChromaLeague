package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.AssistKillingSpreeBar;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class ActivePlayerAssistAnimation extends AnimatedFrame {

    public ActivePlayerAssistAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_O, RzKey.RZKEY_L, RzKey.RZKEY_DOT)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_K, RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_M, RzKey.RZKEY_L, RzKey.RZKEY_SQUARE_BRACKET_LEFT)));
            addAnimationFrame(2, withBackground(Arrays.asList(RzKey.RZKEY_COMA, RzKey.RZKEY_L, RzKey.RZKEY_P)));
        }
    }

    private static LayeredFrame withBackground(List<RzKey> rzKeys) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(AssistKillingSpreeBar.getKillingSpreeBar(), Background.BACKGROUND_COLOR));
        layeredFrame.addFrame(new SimpleFrame(rzKeys, Color.YELLOW));
        return layeredFrame;
    }
}
