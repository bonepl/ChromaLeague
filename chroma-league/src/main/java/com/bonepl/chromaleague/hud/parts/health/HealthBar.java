package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.hud.colors.CLColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_ESC;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_F12;

public class HealthBar extends Animation {
    private static final List<RzKey> HP_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_ESC)
            .withColumnBetween(RZKEY_ESC, RZKEY_F12)
            .sortedByColumn()
            .asList();
    private double previousHp;

    @Override
    public Frame getFrame() {
        final double currentHp = RunningState.getGameState().getActivePlayer().championStats().currentHealth();
        addToBack(getHpBar());
        if (currentHp < previousHp) {
            addToFront(new LostHealthAnimation(previousHp, currentHp));
        }
        if (currentHp > previousHp) {
            addToFront(new GainedHealthAnimation(previousHp, currentHp));
        }
        previousHp = currentHp;
        return super.getFrame();
    }

    private static IFrame getHpBar() {
        return new ProgressBar(HP_BAR_KEYS, GameStateHelper.getHpPercentage(), CLColor.HEALTH);
    }

    public static List<RzKey> getHealthBarKeys() {
        return Collections.unmodifiableList(HP_BAR_KEYS);
    }

    public static List<RzKey> getHpBarPart(double previousHp, double currentHp) {
        final double maxHealth = RunningState.getGameState().getActivePlayer().championStats().maxHealth();
        final int from = ProgressBar.indexToFill(getHealthBarKeys(), Double.valueOf(previousHp * 100 / maxHealth).intValue());
        final int to = ProgressBar.indexToFill(getHealthBarKeys(), Double.valueOf(currentHp * 100 / maxHealth).intValue());
        return getHealthBarKeys().subList(from, to);
    }
}
