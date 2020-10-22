package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.BreathingColor;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.chromaleague.hud.parts.HpBar;
import com.bonepl.chromaleague.hud.parts.ResourceBar;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class RespawnAnimation extends AnimatedFrame {
    private final List<RzKey> glowArea = BaronBuffBackgroundAnimation.buildBaronArea();

    public RespawnAnimation() {
        BreathingColor glowAreaColor = new BreathingColor(Color.YELLOW);
        BreathingColor buttonsColor = new BreathingColor(Color.YELLOW);

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(glowArea, glowAreaColor.getNextColor()));
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, Background.BACKGROUND_COLOR));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, Background.BACKGROUND_COLOR));
            withAnimationFrame(layeredFrame);
        }

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(glowArea, glowAreaColor.getNextColor()));
            final Color buttonsNextColor = buttonsColor.getNextColor();
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, buttonsNextColor));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, buttonsNextColor));
            withAnimationFrame(layeredFrame);
        }

        for (int i = 0; i < 10; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            final Color buttonsNextColor = buttonsColor.getNextColor();
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, buttonsNextColor));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, buttonsNextColor));
            withAnimationFrame(layeredFrame);
        }
    }
}
