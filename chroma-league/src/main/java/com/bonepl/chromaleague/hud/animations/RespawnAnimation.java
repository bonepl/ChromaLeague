package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.hud.parts.health.HpBar;
import com.bonepl.chromaleague.hud.parts.resource.ManaBar;
import com.bonepl.chromaleague.hud.parts.resource.ResourceBars;
import com.bonepl.chromaleague.hud.parts.resource.ShyvanaDragonFuryBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

public class RespawnAnimation extends AnimatedFrame {
    private static final List<RzKey> GLOW_AREA = BaronBuffBackgroundAnimation.buildBaronArea();

    public RespawnAnimation() {
        BreathingColor glowAreaColor = new BreathingColor(Color.YELLOW, 20, true);
        BreathingColor buttonsColor = new BreathingColor(Color.YELLOW, 20, true);

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(GLOW_AREA, glowAreaColor.getNextColor()));
            layeredFrame.addFrame(new SimpleFrame(HpBar.getHpBarKeys(), Background.BACKGROUND_COLOR));
            layeredFrame.addFrame(new SimpleFrame(ResourceBars.getResourceBarKeys(), Background.BACKGROUND_COLOR));
            addAnimationFrame(layeredFrame);
        }

        for (int i = 0; i < 20; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(GLOW_AREA, glowAreaColor.getNextColor()));
            final Color buttonsNextColor = buttonsColor.getNextColor();
            layeredFrame.addFrame(new SimpleFrame(HpBar.getHpBarKeys(), buttonsNextColor));
            layeredFrame.addFrame(new SimpleFrame(ResourceBars.getResourceBarKeys(), buttonsNextColor));
            addAnimationFrame(layeredFrame);
        }

        final TransitionColor toHpColor = new TransitionColor(Color.YELLOW, Color.GREEN, 10);

        TransitionColor toResourceColor;
        if (GameStateHelper.getResourcePercentage() == 0) {
            toResourceColor = new TransitionColor(Color.YELLOW, Background.BACKGROUND_COLOR, 10);
        } else {
            toResourceColor = new TransitionColor(Color.YELLOW, getPlayerResourceToTransitionColor(), 10);
        }

        for (int i = 0; i < 10; i++) {
            final LayeredFrame layeredFrame = new LayeredFrame();
            layeredFrame.addFrame(new SimpleFrame(HpBar.getHpBarKeys(), toHpColor.getNextColor()));
            layeredFrame.addFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), GameStateHelper.getResourcePercentage(), toResourceColor.getNextColor()));
            addAnimationFrame(layeredFrame);
        }
    }

    private static Color getPlayerResourceToTransitionColor() {
        String activePlayerChampionName = RunningState.getGameState().getPlayerList().getActivePlayer().getChampionName();
        if (ResourceBars.getEnergyBarChampions().contains(activePlayerChampionName)) {
            return Color.YELLOW;
        }
        if ("Shyvana".equals(activePlayerChampionName)) {
            return ShyvanaDragonFuryBar.DRAGON_FURY_COLOR;
        }
        return ManaBar.MANA_COLOR;
    }
}
