package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.colors.TransitionColor;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.razersdk.animation.*;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.List;
import java.util.stream.IntStream;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_ESC;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_F12;

public class HpBarAnimation extends Animation {
    private static final List<RzKey> HP_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_ESC)
            .withColumnBetween(RZKEY_ESC, RZKEY_F12)
            .sortedByColumn()
            .asList();
    private double previousHp;
    private static final int HEALTH_CHANGE_STEPS = 5;

    @Override
    public Frame getFrame() {
        final double currentHealth = GameState.getActivePlayer().getChampionStats().getCurrentHealth();
        addToBack(new HpBar());
        if (currentHealth < previousHp) {
            addToFront(getLostHealthBar(currentHealth));
        }
        if (currentHealth > previousHp) {
            addToFront(getGainedHealthBar(currentHealth));
        }
        previousHp = currentHealth;
        return super.getFrame();
    }

    private IFrame getLostHealthBar(double currentHealth) {
        final List<RzKey> rzKeys = countLostHealth(currentHealth);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(Color.RED, Background.BACKGROUND_COLOR, HEALTH_CHANGE_STEPS);
            final AnimatedFrame lostHealthFrame = new AnimatedFrame();
            IntStream.range(0, HEALTH_CHANGE_STEPS).forEach(i -> lostHealthFrame.addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getNextColor())));
            return lostHealthFrame;
        }
        return new SimpleFrame();
    }

    private IFrame getGainedHealthBar(double currentHealth) {
        final List<RzKey> rzKeys = countGainedHealth(currentHealth);
        if (!rzKeys.isEmpty()) {
            final TransitionColor transitionColor = new TransitionColor(Color.WHITE, HpBar.HP_BAR_COLOR, HEALTH_CHANGE_STEPS);
            final AnimatedFrame lostHealthFrame = new AnimatedFrame();
            IntStream.range(0, HEALTH_CHANGE_STEPS).forEach(i -> lostHealthFrame.addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getNextColor())));
            return lostHealthFrame;
        }
        return new SimpleFrame();
    }

    private List<RzKey> countGainedHealth(double currentHealth) {
        final double maxHealth = GameState.getActivePlayer().getChampionStats().getMaxHealth();
        final int from = ProgressBar.indexToFill(HP_BAR_KEYS, Double.valueOf(previousHp * 100 / maxHealth).intValue());
        final int to = ProgressBar.indexToFill(HP_BAR_KEYS, Double.valueOf(currentHealth * 100 / maxHealth).intValue());
        return HP_BAR_KEYS.subList(from, to);
    }

    private List<RzKey> countLostHealth(double currentHealth) {
        final double maxHealth = GameState.getActivePlayer().getChampionStats().getMaxHealth();
        final int from = ProgressBar.indexToFill(HP_BAR_KEYS, Double.valueOf(currentHealth * 100 / maxHealth).intValue());
        final int to = ProgressBar.indexToFill(HP_BAR_KEYS, Double.valueOf(previousHp * 100 / maxHealth).intValue());
        return HP_BAR_KEYS.subList(from, to);
    }
}
