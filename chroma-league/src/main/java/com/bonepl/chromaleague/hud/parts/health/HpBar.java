package com.bonepl.chromaleague.hud.parts.health;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.Animation;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_ESC;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_F12;

public class HpBar extends Animation {
    public static final Color HP_BAR_COLOR = Color.GREEN;
    private static final List<RzKey> HP_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_ESC)
            .withColumnBetween(RZKEY_ESC, RZKEY_F12)
            .sortedByColumn()
            .asList();
    private double previousHp;

    @Override
    public Frame getFrame() {
        final double currentHp = GameState.getActivePlayer().getChampionStats().getCurrentHealth();
        addToBack(getHpBar());
        if (currentHp < previousHp) {
            addToFront(new LostHpAnimation(previousHp, currentHp));
        }
        if (currentHp > previousHp) {
            addToFront(new GainHpAnimation(previousHp, currentHp));
        }
        previousHp = currentHp;
        return super.getFrame();
    }

    private static IFrame getHpBar() {
        return new ProgressBar(HP_BAR_KEYS, GameStateHelper.getHpPercentage(), HP_BAR_COLOR);
    }

    public static List<RzKey> getHpBarKeys() {
        return Collections.unmodifiableList(HP_BAR_KEYS);
    }
}
