package net.booone.chromaleague.hud.parts.health;

import net.booone.chromaleague.hud.colors.CLColor;
import net.booone.chromaleague.hud.parts.ProgressBar;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.IFrame;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static net.booone.razersdk.sdk.RzKey.RZKEY_ESC;
import static net.booone.razersdk.sdk.RzKey.RZKEY_F12;

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
        final int from = ProgressBar.indexToFill(getHealthBarKeys().size(), Double.valueOf(previousHp * 100 / maxHealth).intValue());
        final int to = ProgressBar.indexToFill(getHealthBarKeys().size(), Double.valueOf(currentHp * 100 / maxHealth).intValue());
        return getHealthBarKeys().subList(from, to);
    }
}
