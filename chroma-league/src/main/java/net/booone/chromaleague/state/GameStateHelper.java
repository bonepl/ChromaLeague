package net.booone.chromaleague.state;

import net.booone.chromaleague.hud.parts.GoldBar;
import net.booone.chromaleague.rest.activeplayer.ChampionStats;
import net.booone.chromaleague.rest.eventdata.DragonType;
import net.booone.chromaleague.rest.gamestats.GameStats;
import net.booone.chromaleague.tasks.FetchGameStatsTask;

import java.util.List;
import java.util.Objects;

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
            if (hasExpired(eventTime, currentTimeForReconnection, BARON_TIME)) {
                RunningState.getGameState().getEventData().setBaronBuffEnd(null);
            } else {
                RunningState.getGameState().getEventData().setBaronBuffEnd(eventTime + BARON_TIME);
            }
        }
    }

    private static boolean hasExpired(double eventTime, double currentTimeForReconnection, long expirationTime) {
        if (currentTimeForReconnection == 0d) {
            double gameTime = RunningState.getGameState().getGameStats().gameTime();
            return gameTime - eventTime > expirationTime;
        }
        return currentTimeForReconnection - eventTime > expirationTime;
    }

    public static boolean hasBaronBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
        GameStats gameStats = RunningState.getGameState().getGameStats();
        if (gameStats != null && eventData.getBaronBuffEnd() != null && isActivePlayerAlive()) {
            return gameStats.gameTime() < eventData.getBaronBuffEnd();
        }
        eventData.setBaronBuffEnd(null);
        return false;
    }

    public static void startElderBuff(double eventTime, double currentTimeForReconnection) {
        addKilledElder();
        if (playerAliveAtEventTime(eventTime, currentTimeForReconnection)) {
            final int totalEldersKilled = getTotalEldersKilled();
            if (totalEldersKilled == 1) {
                computeElderBuffTimes(eventTime, currentTimeForReconnection, FIRST_ELDER_TIME);
            } else if (totalEldersKilled > 1) {
                computeElderBuffTimes(eventTime, currentTimeForReconnection, NEXT_ELDER_TIME);
            }
        }
    }

    private static void computeElderBuffTimes(double eventTime, double currentTimeForReconnection, long elderBuffTime) {
        if (hasExpired(eventTime, currentTimeForReconnection, elderBuffTime)) {
            RunningState.getGameState().getEventData().setElderBuffEnd(null);
        } else {
            RunningState.getGameState().getEventData().setElderBuffEnd(eventTime + elderBuffTime);
        }
    }

    private static boolean playerAliveAtEventTime(double eventTime, double currentTimeOrReconnectionTime) {
        final EventData eventData = RunningState.getGameState().getEventData();
        double gameTime = RunningState.getGameState().getGameStats().gameTime();
        if (eventData.getDeathTime() == null) {
            return true;
        }
        double eventLocalTime = gameTime - (currentTimeOrReconnectionTime - eventTime);
        return !(eventLocalTime > eventData.getDeathTime() && eventLocalTime < eventData.getRespawnTime());
    }

    public static boolean hasElderBuff() {
        final EventData eventData = RunningState.getGameState().getEventData();
        GameStats gameStats = RunningState.getGameState().getGameStats();
        if (gameStats != null && eventData.getElderBuffEnd() != null && isActivePlayerAlive()) {
            return gameStats.gameTime() < eventData.getElderBuffEnd();
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
        double respawnTimer = RunningState.getGameState().getPlayerList().getActivePlayer().respawnTimer();
        if (respawnTimer > 0.0 && respawnTimer < 3.0) {
            if (eventData.getRespawnIndicator() == RespawnIndicator.IDLE) {
                eventData.setRespawnIndicator(RespawnIndicator.CHARGING);
                return true;
            }
        } else if (eventData.getRespawnIndicator() == RespawnIndicator.CHARGING) {
            eventData.setRespawnIndicator(RespawnIndicator.IDLE);
        }
        return false;
    }

    public static boolean shouldPlayRiftAnimation() {
        EventData eventData = RunningState.getGameState().getEventData();
        if (Objects.equals(FetchGameStatsTask.DEFAULT_MAP_TERRAIN, RunningState.getGameState().getGameStats().mapTerrain())
                || eventData.didRiftAnimationPlay()) {
            return false;
        }
        eventData.setRiftAnimationPlayed(true);
        return true;
    }

}
