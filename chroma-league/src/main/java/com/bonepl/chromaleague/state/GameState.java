package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.hud.MainHud;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;

public final class GameState {
    private ActivePlayer activePlayer;
    private PlayerList playerList;
    private final MainHud mainHud = new MainHud();
    private final EventData eventData = new EventData();

    public void setActivePlayer(ActivePlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public boolean isActivePlayerAvailable() {
        return activePlayer != null;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public boolean isPlayerListAvailable() {
        return playerList != null;
    }

    public EventData getEventData() {
        return eventData;
    }

    public MainHud getMainHud() {
        return mainHud;
    }
}
