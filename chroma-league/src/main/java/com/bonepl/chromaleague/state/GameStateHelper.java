package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.tasks.FetchGameStats;

import java.time.LocalTime;
import java.util.List;

public final class GameStateHelper {

    public static final int FIRST_ELDER_TIME = 150;
    public static final int NEXT_ELDER_TIME = 300;
    public static final int BARON_TIME = 180;

    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        return !RunningState.getGameState().getActivePlayer().getChampionStats().isDead();
    }

    public static int getHpPercentage() {
        final ChampionStats championStats = RunningState.getGameState().getActivePlayer().getChampionStats();
        return getPercentage(championStats.getCurrentHealth(), championStats.getMaxHealth());
    }

    public static int getResourcePercentage() {
        final ChampionStats championStats = RunningState.getGameState().getActivePlayer().getChampionStats();
        return getPercentage(championStats.getResourceValue(), championStats.getResourceMax());
    }

    public static double getGold() {
        return RunningState.getGameState().getActivePlayer().getCurrentGold();
    }

    public static int getLevel() {
        return RunningState.getGameState().getActivePlayer().getLevel();
    }

    /**
     * Future's market rune can make percentage negative
     */
    public static int getGoldPercentage() {
        final int percentage = getPercentage(RunningState.getGameState().getActivePlayer().getCurrentGold(), GoldBar.GOLD_FULL);
        return Math.max(percentage, 0);
    }

    public static double getActivePlayerRange() {
        return RunningState.getGameState().getActivePlayer().getChampionStats().getAttackRange();
    }

    private static int getPercentage(double firstDouble, double secondDouble) {
        return Double.valueOf(firstDouble * 100 / secondDouble).intValue();
    }

    public static void startBaronBuff(double eventTime, double currentTimeForReconnection) {
        if (wasPlayerAlive(eventTime) && isActivePlayerAlive()) {
            double buffDiffToCover = getCurrentTimeOrReconnectionTime(currentTimeForReconnection) - eventTime;
            long secondsToRemoveFromTimer = Math.round(buffDiffToCover);
            if (secondsToRemoveFromTimer < BARON_TIME) {
                RunningState.getGameState().getEventData().setBaronBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(BARON_TIME));
            }
        }
    }

    private static double getCurrentTimeOrReconnectionTime(double currentTimeForReconnection) {
        if (currentTimeForReconnection == 0.0) {
            return new FetchGameStats().fetchGameStats().getGameTime();
        }
        return currentTimeForReconnection;
    }

    public static boolean hasBaronBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
        if (eventData.getBaronBuffEnd() != null && isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getBaronBuffEnd());
        }
        eventData.setBaronBuffEnd(null);
        return false;
    }

    public static void startElderBuff(double eventTime, double currentTimeForReconnection) {
        addKilledElder();
        if (wasPlayerAlive(eventTime) && isActivePlayerAlive()) {
            final int totalEldersKilled = getTotalEldersKilled();
            double buffDiffToCover = getCurrentTimeOrReconnectionTime(currentTimeForReconnection) - eventTime;
            long secondsToRemoveFromTimer = Math.round(buffDiffToCover);
            if (totalEldersKilled == 1 && secondsToRemoveFromTimer < FIRST_ELDER_TIME) {
                RunningState.getGameState().getEventData()
                        .setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(FIRST_ELDER_TIME));
            } else if (totalEldersKilled > 1 && secondsToRemoveFromTimer < NEXT_ELDER_TIME) {
                RunningState.getGameState().getEventData()
                        .setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(NEXT_ELDER_TIME));
            }
        }
    }

    private static boolean wasPlayerAlive(double eventTime) {
        final EventData eventData = RunningState.getGameState().getEventData();
        return eventTime > eventData.getLastDeathTime() + eventData.getApproxLastDeathTimer();
    }

    public static boolean hasElderBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
        if (eventData.getElderBuffEnd() != null && isActivePlayerAlive()) {
            return LocalTime.now().isBefore(eventData.getElderBuffEnd());
        }
        eventData.setElderBuffEnd(null);
        return false;
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

    public static boolean shouldPlayRespawnAnimation() {
        final RespawnIndicator respawnIndicator = RunningState.getGameState().getEventData().getRespawnIndicator();
        final double respawnTimer = RunningState.getGameState().getPlayerList().getActivePlayer().getRespawnTimer();
        if (respawnIndicator == RespawnIndicator.IDLE) {
            if (isActivePlayerAlive() && respawnTimer > 2.0) {
                RunningState.getGameState().getEventData().setRespawnIndicator(RespawnIndicator.IDLE);
            }
            return false;
        }
        if (respawnIndicator == RespawnIndicator.READY) {
            RunningState.getGameState().getEventData().setRespawnIndicator(RespawnIndicator.IDLE);
            return true;
        }
        if (respawnIndicator == RespawnIndicator.CHARGING) {
            if (respawnTimer <= 2.0) {
                RunningState.getGameState().getEventData().setRespawnIndicator(RespawnIndicator.READY);
            }
        }
        return false;
    }
}
