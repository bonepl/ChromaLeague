package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.BreathingColor;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.chromaleague.hud.parts.HpBar;
import com.bonepl.chromaleague.hud.parts.ResourceBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class RespawnAnimation extends AnimatedFrame {
    private final List<RzKey> glowArea = BaronBuffBackgroundAnimation.buildBaronArea();

    public RespawnAnimation() {
        BreathingColor glowAreaColor = new BreathingColor(Color.YELLOW);
        BreathingColor buttonsColor = new BreathingColor(Color.YELLOW);

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new Frame(glowArea, glowAreaColor.getNextColor()));
            layeredFrame.addFrame(new Frame(HpBar.HP_BAR_KEYS, Background.BACKGROUND_COLOR));
            layeredFrame.addFrame(new Frame(ResourceBar.RESOURCE_BAR_KEYS, Background.BACKGROUND_COLOR));
            withAnimationFrame(layeredFrame);
        }

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new Frame(glowArea, glowAreaColor.getNextColor()));
            final Color buttonsNextColor = buttonsColor.getNextColor();
            layeredFrame.addFrame(new Frame(HpBar.HP_BAR_KEYS, buttonsNextColor));
            layeredFrame.addFrame(new Frame(ResourceBar.RESOURCE_BAR_KEYS, buttonsNextColor));
            withAnimationFrame(layeredFrame);
        }
    }
}
