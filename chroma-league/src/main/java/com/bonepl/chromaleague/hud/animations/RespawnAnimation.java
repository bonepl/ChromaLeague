package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.colors.TransitionColor;
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
    private static final List<RzKey> GLOW_AREA = BaronBuffBackgroundAnimation.buildBaronArea();

    public RespawnAnimation() {
        BreathingColor glowAreaColor = new BreathingColor(Color.YELLOW);
        BreathingColor buttonsColor = new BreathingColor(Color.YELLOW);

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(GLOW_AREA, glowAreaColor.getNextColor()));
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, Background.BACKGROUND_COLOR));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, Background.BACKGROUND_COLOR));
            addAnimationFrame(layeredFrame);
        }

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(GLOW_AREA, glowAreaColor.getNextColor()));
            final Color buttonsNextColor = buttonsColor.getNextColor();
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, buttonsNextColor));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, buttonsNextColor));
            addAnimationFrame(layeredFrame);
        }

        final TransitionColor toHpColor = new TransitionColor(Color.YELLOW, Color.GREEN, 10);

        TransitionColor toManaColor;
        if (GameStateHelper.getResourcePercentage() == 0) {
            toManaColor = new TransitionColor(Color.YELLOW, Background.BACKGROUND_COLOR, 10);
        } else {
            toManaColor = new TransitionColor(Color.YELLOW, Color.BLUE, 10);
        }

        for (int i = 0; i < 10; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(HpBar.HP_BAR_KEYS, toHpColor.getNextColor()));
            layeredFrame.addFrame(new SimpleFrame(ResourceBar.RESOURCE_BAR_KEYS, toManaColor.getNextColor()));
            addAnimationFrame(layeredFrame);
        }
    }
}
