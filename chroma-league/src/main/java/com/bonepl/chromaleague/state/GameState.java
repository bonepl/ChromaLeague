package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.MainHud;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;

public final class GameState {
    private ActivePlayer activePlayer;
    private PlayerList playerList;
    private GameStats gameStats;
    private final MainHud mainHud = new MainHud();
    private final EventData eventData = new EventData();
    private String playerName;

    public void setActivePlayer(ActivePlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public MainHud getMainHud() {
        return mainHud;
    }

    public EventData getEventData() {
        return eventData;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
