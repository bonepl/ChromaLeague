package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.DragonType;
import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.CustomData;
import com.bonepl.chromaleague.rest.activeplayer.model.ChampionStats;
import com.bonepl.chromaleague.rest.activeplayer.model.ResourceType;

import java.time.LocalTime;
import java.util.List;

import static com.bonepl.chromaleague.GameState.*;

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

    public static void startBaronBuff() {
        if (GameStateHelper.isActivePlayerAlive()) {
            GameState.getCustomData().setBaronBuffEnd(LocalTime.now().plusMinutes(3));
        }
    }

    public static boolean hasBaronBuff() {
        final CustomData customData = getCustomData();
        if (customData.getBaronBuffEnd() != null && GameStateHelper.isActivePlayerAlive()) {
            return LocalTime.now().isBefore(customData.getBaronBuffEnd());
        }
        customData.setBaronBuffEnd(null);
        return false;
    }

    public static void addKilledDragon(DragonType dragonType) {
        getCustomData().getKilledDragons().add(dragonType);
    }

    public static List<DragonType> getKilledDragons() {
        return getCustomData().getKilledDragons();
    }
}
