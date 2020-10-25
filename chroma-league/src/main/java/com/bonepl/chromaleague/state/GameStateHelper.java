package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.activeplayer.ResourceType;
import com.bonepl.chromaleague.rest.eventdata.DragonType;

import java.time.LocalTime;
import java.util.List;

public final class GameStateHelper {

    public static final int FIRST_ELDER_TIME = 150;
    public static final int NEXT_ELDER_TIME = 300;

    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        return !GameState.isPlayerListAvailable() || !GameState.getPlayerList().getActivePlayer().isDead();
    }

    public static int getHpPercentage() {
        if (isActivePlayerAlive() && GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            return getPercentage(championStats.getCurrentHealth(), championStats.getMaxHealth());
        }
        return 0;
    }

    public static int getResourcePercentage() {
        if (isActivePlayerAlive() && GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            return getPercentage(championStats.getResourceValue(), championStats.getResourceMax());
        }
        return 0;
    }

    public static ResourceType getResourceType() {
        if (GameState.isActivePlayerAvailable()) {
            return ResourceType.from(GameState.getActivePlayer().getChampionStats().getResourceType());
        }
        return ResourceType.MANA;
    }

    public static double getGold() {
        if (GameState.isActivePlayerAvailable()) {
            return GameState.getActivePlayer().getCurrentGold();
        }
        return 0;
    }

    public static int getLevel() {
        if (GameState.isActivePlayerAvailable()) {
            return GameState.getActivePlayer().getLevel();
        }
        return 0;
    }

    public static int getGoldPercentage() {
        if (GameState.isActivePlayerAvailable()) {
            return getPercentage(GameState.getActivePlayer().getCurrentGold(), GoldBar.GOLD_FULL);
        }
        return 0;
    }

    private static int getPercentage(double firstDouble, double secondDouble) {
        return (int) Math.floor(firstDouble * 100 / secondDouble);
    }

    public static void startBaronBuff() {
        if (isActivePlayerAlive()) {
            GameState.getEventData().setBaronBuffEnd(LocalTime.now().plusMinutes(3));
        }
    }

    public static boolean hasBaronBuff() {
        final EventData eventData = GameState.getEventData();
        if (eventData.getBaronBuffEnd() != null && isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getBaronBuffEnd());
        }
        eventData.setBaronBuffEnd(null);
        return false;
    }

    public static void startElderBuff() {
        if (isActivePlayerAlive()) {
            final int totalEldersKilled = getTotalEldersKilled();
            if (totalEldersKilled == 1) {
                GameState.getEventData().setElderBuffEnd(LocalTime.now().plusSeconds(FIRST_ELDER_TIME));
            } else {
                GameState.getEventData().setElderBuffEnd(LocalTime.now().plusSeconds(NEXT_ELDER_TIME));
            }
        }
    }

    public static boolean hasElderBuff() {
        final EventData eventData = GameState.getEventData();
        if (eventData.getElderBuffEnd() != null && isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getElderBuffEnd());
        }
        eventData.setElderBuffEnd(null);
        return false;
    }

    public static boolean hasDragonSoul() {
        return GameState.getEventData().getKilledDragons().size() >= 4;
    }

    public static DragonType getDragonSoulType() {
        return GameState.getEventData().getKilledDragons().get(3);
    }

    public static void addKilledDragon(DragonType dragonType) {
        GameState.getEventData().addKilledDragon(dragonType);
    }

    public static List<DragonType> getKilledDragons() {
        return GameState.getEventData().getKilledDragons();
    }

    public static int getTotalEldersKilled() {
        return GameState.getEventData().getTotalEldersKilled();
    }

    public static void addKilledElder() {
        final EventData eventData = GameState.getEventData();
        eventData.setTotalEldersKilled(eventData.getTotalEldersKilled() + 1);
    }

    public static int getActivePlayerKillingSpree() {
        return GameState.getEventData().getActivePlayerKillingSpree();
    }

    public static void addPlayerKill() {
        GameState.getEventData().setActivePlayerKillingSpree(
                GameState.getEventData().getActivePlayerKillingSpree() + 1);
    }

    public static int getActivePlayerAssistSpree() {
        return GameState.getEventData().getActivePlayerAssistSpree();
    }

    public static void addPlayerAssist() {
        GameState.getEventData().setActivePlayerAssistSpree(
                GameState.getEventData().getActivePlayerAssistSpree() + 1);
    }

    public static void resetCustomData() {
        GameState.getEventData().resetCounters();
    }

}
