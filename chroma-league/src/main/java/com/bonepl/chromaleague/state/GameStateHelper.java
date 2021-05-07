package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.tasks.FetchGameStats;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class GameStateHelper {

    public static final long FIRST_ELDER_TIME = 150L;
    public static final long NEXT_ELDER_TIME = 300L;
    public static final long BARON_TIME = 180L;

    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        return !RunningState.getGameState().getPlayerList().getActivePlayer().isDead();
    }

    public static int getHpPercentage() {
        final ChampionStats championStats = RunningState.getGameState().getActivePlayer().championStats();
        return getPercentage(championStats.currentHealth(), championStats.maxHealth());
    }

    public static int getResourcePercentage() {
        final ChampionStats championStats = RunningState.getGameState().getActivePlayer().championStats();
        return getPercentage(championStats.resourceValue(), championStats.resourceMax());
    }

    public static double getGold() {
        return RunningState.getGameState().getActivePlayer().currentGold();
    }

    public static int getLevel() {
        return RunningState.getGameState().getActivePlayer().level();
    }

    /**
     * Future's market rune can make percentage negative
     */
    public static int getGoldPercentage() {
        final int percentage = getPercentage(RunningState.getGameState().getActivePlayer().currentGold(), GoldBar.GOLD_FULL);
        return Math.max(percentage, 0);
    }

    public static double getActivePlayerRange() {
        return RunningState.getGameState().getActivePlayer().championStats().attackRange();
    }

    private static int getPercentage(double firstDouble, double secondDouble) {
        return Double.valueOf(firstDouble * 100 / secondDouble).intValue();
    }

    public static void startBaronBuff(double eventTime, double currentTimeForReconnection) {
        if (playerAliveAtEventTime(eventTime, currentTimeForReconnection)) {
            double buffDiffToCover = getCurrentTimeOrReconnectionTime(currentTimeForReconnection) - eventTime;
            long secondsToRemoveFromTimer = Math.round(buffDiffToCover);
            if (secondsToRemoveFromTimer < BARON_TIME) {
                RunningState.getGameState().getEventData().setBaronBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(BARON_TIME));
            }
        }
    }

    private static double getCurrentTimeOrReconnectionTime(double currentTimeForReconnection) {
        if (currentTimeForReconnection == 0.0) {
            return FetchGameStats.fetchGameStats().gameTime();
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
        final double currentTimeOrReconnectionTime = getCurrentTimeOrReconnectionTime(currentTimeForReconnection);
        if (playerAliveAtEventTime(eventTime, currentTimeOrReconnectionTime)) {
            final int totalEldersKilled = getTotalEldersKilled();
            long secondsToRemoveFromTimer = Math.round(currentTimeOrReconnectionTime - eventTime);
            if (totalEldersKilled == 1 && secondsToRemoveFromTimer < FIRST_ELDER_TIME) {
                RunningState.getGameState().getEventData()
                        .setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(FIRST_ELDER_TIME));
            } else if (totalEldersKilled > 1 && secondsToRemoveFromTimer < NEXT_ELDER_TIME) {
                RunningState.getGameState().getEventData()
                        .setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(NEXT_ELDER_TIME));
            }
        }
    }

    private static boolean playerAliveAtEventTime(double eventTime, double currentTimeOrReconnectionTime) {
        final EventData eventData = RunningState.getGameState().getEventData();
        if (eventData.getDeathTime() == null) {
            return true;
        }
        final LocalTime eventLocalTime = LocalTime.now().minus(millisDuration(currentTimeOrReconnectionTime - eventTime));
        return !(eventLocalTime.isAfter(eventData.getDeathTime()) && eventLocalTime.isBefore(eventData.getRespawnTime()));
    }

    public static Duration millisDuration(double gameTime) {
        return Duration.ofMillis(Double.valueOf(gameTime * 1000).longValue());
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
        final EventData eventData = RunningState.getGameState().getEventData();
        final LocalTime respawnTime = eventData.getRespawnTime();
        if (respawnTime != null) {
            final LocalTime now = LocalTime.now();
            if (now.isAfter(respawnTime)
                    || eventData.getRespawnIndicator() == RespawnIndicator.IDLE) {
                return false;
            }
            if (eventData.getRespawnIndicator() == RespawnIndicator.CHARGING) {
                if (ChronoUnit.SECONDS.between(now, respawnTime) <= 1L) {
                    eventData.setRespawnIndicator(RespawnIndicator.IDLE);
                    return true;
                }
            }
        }
        return false;
    }
}
