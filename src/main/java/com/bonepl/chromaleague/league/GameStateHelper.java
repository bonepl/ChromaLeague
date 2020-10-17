package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.league.hud.parts.GoldBar;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ChampionStats;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ResourceType;

import static com.bonepl.chromaleague.league.GameState.*;

public final class GameStateHelper {
    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        if (isPlayerListAvailable()) {
            return !getPlayerList().getActivePlayer().isDead();
        }
        return true;
    }

    public static int getHpPercentage() {
        if (isActivePlayerAlive() && isActivePlayerAvailable()) {
            final ChampionStats championStats = getActivePlayer().getChampionStats();
            return getPercentage(championStats.getCurrentHealth(), championStats.getMaxHealth());
        }
        return 0;
    }

    public static int getResourcePercentage() {
        if (isActivePlayerAlive() && isActivePlayerAvailable()) {
            final ChampionStats championStats = getActivePlayer().getChampionStats();
            return getPercentage(championStats.getResourceValue(), championStats.getResourceMax());
        }
        return 0;
    }

    public static ResourceType getResourceType() {
        if (isActivePlayerAvailable()) {
            return ResourceType.from(getActivePlayer().getChampionStats().getResourceType());
        }
        return ResourceType.MANA;
    }

    public static double getGold() {
        if (isActivePlayerAvailable()) {
            return getActivePlayer().getCurrentGold();
        }
        return 0;
    }

    public static int getLevel() {
        if (isActivePlayerAvailable()) {
            return getActivePlayer().getLevel();
        }
        return 0;
    }

    public static int getGoldPercentage() {
        if (isActivePlayerAvailable()) {
            return getPercentage(getActivePlayer().getCurrentGold(), GoldBar.GOLD_FULL);
        }
        return 0;
    }

    private static int getPercentage(double firstDouble, double secondDouble) {
        return (int) Math.floor((firstDouble / secondDouble) * 100);
    }
}
