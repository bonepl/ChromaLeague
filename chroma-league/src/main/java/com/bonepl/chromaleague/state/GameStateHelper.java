package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.activeplayer.ResourceType;
import com.bonepl.chromaleague.rest.eventdata.DragonType;

import java.time.LocalTime;
import java.util.List;

import static com.bonepl.chromaleague.state.GameState.*;

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
        final EventData eventData = getCustomData();
        if (eventData.getBaronBuffEnd() != null && GameStateHelper.isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getBaronBuffEnd());
        }
        eventData.setBaronBuffEnd(null);
        return false;
    }

    public static void startElderBuff() {
        if (GameStateHelper.isActivePlayerAlive()) {
            final int totalEldersKilled = GameStateHelper.getTotalEldersKilled();
            if (totalEldersKilled == 1) {
                GameState.getCustomData().setElderBuffEnd(LocalTime.now().plusSeconds(150));
            } else {
                GameState.getCustomData().setElderBuffEnd(LocalTime.now().plusSeconds(300));
            }
        }
    }

    public static boolean hasElderBuff() {
        final EventData eventData = getCustomData();
        if (eventData.getElderBuffEnd() != null && GameStateHelper.isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getElderBuffEnd());
        }
        eventData.setElderBuffEnd(null);
        return false;
    }

    public static boolean hasDragonSoul() {
        return getCustomData().getKilledDragons().size() >= 4;
    }

    public static DragonType getDragonSoulType() {
        return getCustomData().getKilledDragons().get(3);
    }

    public static void addKilledDragon(DragonType dragonType) {
        getCustomData().getKilledDragons().add(dragonType);
    }

    public static List<DragonType> getKilledDragons() {
        return getCustomData().getKilledDragons();
    }

    public static int getTotalEldersKilled() {
        return getCustomData().getTotalEldersKilled();
    }

    public static void addKilledElder() {
        final EventData eventData = getCustomData();
        eventData.setTotalEldersKilled(eventData.getTotalEldersKilled() + 1);
    }

    public static int getActivePlayerKillingSpree() {
        final EventData eventData = getCustomData();
        return eventData.getActivePlayerKillingSpree();
    }

    public static void addPlayerKill() {
        final EventData eventData = getCustomData();
        eventData.setActivePlayerKillingSpree(eventData.getActivePlayerKillingSpree() + 1);
    }

    public static int getActivePlayerAssistSpree() {
        final EventData eventData = getCustomData();
        return eventData.getActivePlayerAssistSpree();
    }

    public static void addPlayerAssist() {
        final EventData eventData = getCustomData();
        eventData.setActivePlayerAssistSpree(eventData.getActivePlayerAssistSpree() + 1);
    }

    public static void resetCustomData() {
        getCustomData().resetCounters();
    }

}
