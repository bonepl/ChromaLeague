package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.EventAnimator;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GameState {
    private static final Logger logger = LogManager.getLogger();
    private volatile static ActivePlayer activePlayer;
    private volatile static PlayerList playerList;
    private volatile static boolean riotApiUp = false;
    private volatile static boolean runningGame = false;
    private static final EventData EVENT_DATA = new EventData();

    private GameState() {
    }

    public static String getActivePlayerName() {
        return getActivePlayer().getSummonerName();
    }

    public static void setActivePlayer(ActivePlayer activePlayer) {
        GameState.activePlayer = activePlayer;
    }

    public static ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public static boolean isActivePlayerAvailable() {
        return activePlayer != null && activePlayer.getChampionStats() != null;
    }

    public static void setPlayerList(PlayerList playerList) {
        GameState.playerList = playerList;
    }

    public static PlayerList getPlayerList() {
        return playerList;
    }

    public static boolean isPlayerListAvailable() {
        return playerList != null && playerList.getActivePlayer() != null;
    }

    public static EventData getCustomData() {
        return EVENT_DATA;
    }

    public static boolean isRunningGame() {
        return runningGame;
    }

    public static void setRunningGame(boolean runningGame) {
        if(runningGame != GameState.runningGame) {
            logger.info("Change running game to " + runningGame);
        }
        GameState.runningGame = runningGame;
    }

    public static boolean isRiotApiUp() {
        return riotApiUp;
    }

    public static void setRiotApiUp(boolean riotApiUp) {
        if (riotApiUp != GameState.riotApiUp) {
            logger.info("Change riot api up to " + riotApiUp);
        }
        GameState.riotApiUp = riotApiUp;
        if (!riotApiUp) {
            EventAnimator.stop();
            GameStateHelper.resetCustomData();
            runningGame = false;
            FetchNewEventsTask.resetProcessedEventCounter();
        }
    }
}
