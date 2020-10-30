package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;

public final class GameState {
    private ActivePlayer activePlayer;
    private PlayerList playerList;
    private final EventData eventData = new EventData();

    public static String getActivePlayerName() {
        return RunningState.getGameState().activePlayer.getSummonerName();
    }

    public static void setActivePlayer(ActivePlayer activePlayer) {
        RunningState.getGameState().activePlayer = activePlayer;
    }

    public ActivePlayer getActivePlayer(){
        return activePlayer;
    }

    public static boolean isActivePlayerAvailable() {
        return RunningState.getGameState() != null && RunningState.getGameState().activePlayer != null;
    }

    public static void setPlayerList(PlayerList playerList) {
        RunningState.getGameState().playerList = playerList;
    }

    public static PlayerList getPlayerList() {
        return RunningState.getGameState().playerList;
    }

    public static boolean isPlayerListAvailable() {
        return RunningState.getGameState() != null && RunningState.getGameState().playerList != null;
    }

    public static EventData getEventData() {
        return RunningState.getGameState().eventData;
    }
}
