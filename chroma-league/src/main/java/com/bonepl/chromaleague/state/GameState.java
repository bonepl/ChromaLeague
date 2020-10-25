package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.EventAnimator;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GameState {
    private static volatile ActivePlayer activePlayer;
    private static volatile PlayerList playerList;
    private static boolean runningGameChanged = false;
    private static volatile boolean riotApiUp = false;
    private static volatile boolean runningGame = false;
    private static final EventData EVENT_DATA = new EventData();

    private GameState() {
    }

    public static String getActivePlayerName() {
        return activePlayer.getSummonerName();
    }

    public static void setActivePlayer(ActivePlayer activePlayer) {
        GameState.activePlayer = activePlayer;
    }

    public static ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public static boolean isActivePlayerAvailable() {
        return activePlayer != null;
    }

    public static void setPlayerList(PlayerList playerList) {
        GameState.playerList = playerList;
    }

    public static PlayerList getPlayerList() {
        return playerList;
    }

    public static boolean isPlayerListAvailable() {
        return playerList != null;
    }

    public static EventData getEventData() {
        return EVENT_DATA;
    }

    public static boolean isRunningGameChanged() {
        return runningGameChanged;
    }

    public static boolean isRunningGame() {
        GameState.runningGameChanged = false;
        return runningGame;
    }

    public static void setRunningGame(boolean runningGame) {
        if (GameState.runningGame != runningGame) {
            GameState.runningGameChanged = true;
            GameState.runningGame = runningGame;
        }
    }

    public static boolean isRiotApiUp() {
        return riotApiUp;
    }

    public static void setRiotApiUp(boolean riotApiUp) {
        if (GameState.riotApiUp != riotApiUp) {
            GameState.riotApiUp = riotApiUp;
            if (!riotApiUp) {
                GameStateHelper.resetCustomData();
                EventAnimator.stop();
                FetchNewEventsTask.resetProcessedEventCounter();
                runningGame = false;
            }
        }
    }


}
