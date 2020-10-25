package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.parts.EventAnimator;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;

public final class GameState {
    private static volatile ActivePlayer activePlayer;
    private static volatile PlayerList playerList;
    private static volatile boolean riotApiUp;
    private static volatile boolean runningGame;
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

    public static boolean isRunningGame() {
        return runningGame;
    }

    public static void setRunningGame(boolean runningGame) {
        GameState.runningGame = runningGame;
    }

    public static boolean isRiotApiUp() {
        return riotApiUp;
    }

    public static void setRiotApiUp(boolean riotApiUp) {
        GameState.riotApiUp = riotApiUp;
        if (!riotApiUp) {
            GameStateHelper.resetCustomData();
            EventAnimator.stop();
            FetchNewEventsTask.resetProcessedEventCounter();
            runningGame = false;
        }
    }

    private static boolean changedRiotApiUP(boolean newValue) {
        return newValue != riotApiUp;
    }
}
