package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.eventdata.DragonType;

import java.time.LocalTime;
import java.util.List;

public final class GameStateHelper {

    public static final int FIRST_ELDER_TIME = 150;
    public static final int NEXT_ELDER_TIME = 300;

    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        return !RunningState.getGameState().isPlayerListAvailable() || !RunningState.getGameState().getPlayerList().getActivePlayer().isDead();
    }

    public static int getHpPercentage() {
        if (isActivePlayerAlive() && RunningState.getGameState().isActivePlayerAvailable()) {
            final ChampionStats championStats = RunningState.getGameState().getActivePlayer().getChampionStats();
            return getPercentage(championStats.getCurrentHealth(), championStats.getMaxHealth());
        }
        return 0;
    }

    public static int getResourcePercentage() {
        if (isActivePlayerAlive() && RunningState.getGameState().isActivePlayerAvailable()) {
            final ChampionStats championStats = RunningState.getGameState().getActivePlayer().getChampionStats();
            return getPercentage(championStats.getResourceValue(), championStats.getResourceMax());
        }
        return 0;
    }

    public static double getGold() {
        if (RunningState.getGameState().isActivePlayerAvailable()) {
            return RunningState.getGameState().getActivePlayer().getCurrentGold();
        }
        return 0;
    }

    public static int getLevel() {
        if (RunningState.getGameState().isActivePlayerAvailable()) {
            return RunningState.getGameState().getActivePlayer().getLevel();
        }
        return 0;
    }

    public static int getGoldPercentage() {
        if (RunningState.getGameState().isActivePlayerAvailable()) {
            return getPercentage(RunningState.getGameState().getActivePlayer().getCurrentGold(), GoldBar.GOLD_FULL);
        }
        return 0;
    }

    public static double getActivePlayerRange() {
        if (RunningState.getGameState().isActivePlayerAvailable()) {
            return RunningState.getGameState().getActivePlayer().getChampionStats().getAttackRange();
        }
        return 0;
    }

    private static int getPercentage(double firstDouble, double secondDouble) {
        return Double.valueOf(firstDouble * 100 / secondDouble).intValue();
    }

    public static void startBaronBuff() {
        if (isActivePlayerAlive()) {
            RunningState.getGameState().getEventData().setBaronBuffEnd(LocalTime.now().plusMinutes(3));
        }
    }

    public static boolean hasBaronBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
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
                RunningState.getGameState().getEventData().setElderBuffEnd(LocalTime.now().plusSeconds(FIRST_ELDER_TIME));
            } else {
                RunningState.getGameState().getEventData().setElderBuffEnd(LocalTime.now().plusSeconds(NEXT_ELDER_TIME));
            }
        }
    }

    public static boolean hasElderBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
        if (eventData.getElderBuffEnd() != null && isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getElderBuffEnd());
        }
        eventData.setElderBuffEnd(null);
        return false;
    }

    public static boolean hasDragonSoul() {
        return RunningState.getGameState().getEventData().getKilledDragons().size() >= 4;
    }

    public static DragonType getDragonSoulType() {
        return RunningState.getGameState().getEventData().getKilledDragons().get(3);
    }

    public static void addKilledDragon(DragonType dragonType) {
        RunningState.getGameState().getEventData().addKilledDragon(dragonType);
    }

    public static List<DragonType> getKilledDragons() {
        return RunningState.getGameState().getEventData().getKilledDragons();
    }

    public static int getTotalEldersKilled() {
        return RunningState.getGameState().getEventData().getTotalEldersKilled();
    }

    public static void addKilledElder() {
        final EventData eventData = RunningState.getGameState().getEventData();
        eventData.setTotalEldersKilled(eventData.getTotalEldersKilled() + 1);
    }

    public static int getActivePlayerKillingSpree() {
        return RunningState.getGameState().getEventData().getActivePlayerKillingSpree();
    }

    public static void addPlayerKill() {
        RunningState.getGameState().getEventData().setActivePlayerKillingSpree(
                RunningState.getGameState().getEventData().getActivePlayerKillingSpree() + 1);
    }

    public static int getActivePlayerAssistSpree() {
        return RunningState.getGameState().getEventData().getActivePlayerAssistSpree();
    }

    public static void addPlayerAssist() {
        RunningState.getGameState().getEventData().setActivePlayerAssistSpree(
                RunningState.getGameState().getEventData().getActivePlayerAssistSpree() + 1);
    }
}
