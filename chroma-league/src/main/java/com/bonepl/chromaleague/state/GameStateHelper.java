package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.tasks.FetchGameStats;

import java.time.LocalTime;
import java.util.List;

public final class GameStateHelper {

    public static final int FIRST_ELDER_TIME = 150;
    public static final int NEXT_ELDER_TIME = 300;

    private GameStateHelper() {
    }

    public static boolean isActivePlayerAlive() {
        return !RunningState.getGameState().getPlayerList().getActivePlayer().isDead();
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

    public static void startBaronBuff(Event event) {
        final GameStats gameStats = new FetchGameStats().fetchGameStats();
        double eventTime = event.getEventTime();
        if (isActivePlayerAlive() || didPlayerDieAfterKill(RunningState.getGameState().getEventData().getLastDeathTime(), eventTime)) {
            double buffDiffToCover = gameStats.gameTime - eventTime;
            long secondsToRemoveFromTimer = Math.round(buffDiffToCover);
            RunningState.getGameState().getEventData().setBaronBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusMinutes(3));
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

    public static void startElderBuff(double eventTime) {
        final GameStats gameStats = new FetchGameStats().fetchGameStats();
        if (isActivePlayerAlive() || didPlayerDieAfterKill(RunningState.getGameState().getEventData().getLastDeathTime(), eventTime)) {
            final int totalEldersKilled = getTotalEldersKilled();
            double buffDiffToCover = gameStats.gameTime - eventTime;
            long secondsToRemoveFromTimer = Math.round(buffDiffToCover);
            if (totalEldersKilled == 1) {
                RunningState.getGameState().getEventData().setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(FIRST_ELDER_TIME));
            } else {
                RunningState.getGameState().getEventData().setElderBuffEnd(LocalTime.now().minusSeconds(secondsToRemoveFromTimer).plusSeconds(NEXT_ELDER_TIME));
            }
        }
    }

    private static boolean didPlayerDieAfterKill(double lastDeathTime, double eventTime) {
        return lastDeathTime > eventTime;
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
}
