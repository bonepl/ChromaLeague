package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class GameStateMocks {
    public static final String PLAYER_NAME = "BooonE";

    private final ActivePlayer mockActivePlayer;
    private final PlayerList mockPlayerList;

    public GameStateMocks() {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
        mockActivePlayer = prepareActivePlayerMock();
        mockPlayerList = preparePlayerListMock();
        when(activePlayer().getSummonerName()).thenReturn(PLAYER_NAME);
    }

    private static PlayerList preparePlayerListMock() {
        PlayerList playerList = mock(PlayerList.class);
        Player activePlayer = mock(Player.class);
        when(playerList.getActivePlayer()).thenReturn(activePlayer);
        return playerList;
    }

    private static ActivePlayer prepareActivePlayerMock() {
        ChampionStats championStats = mock(ChampionStats.class);
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getChampionStats()).thenReturn(championStats);
        return activePlayer;
    }

    public ChampionStats championStats() {
        RunningState.getGameState().setActivePlayer(mockActivePlayer);
        return mockActivePlayer.getChampionStats();
    }

    public ActivePlayer activePlayer() {
        RunningState.getGameState().setActivePlayer(mockActivePlayer);
        return mockActivePlayer;
    }

    public PlayerList playerList() {
        RunningState.getGameState().setPlayerList(mockPlayerList);
        return mockPlayerList;
    }

    public Player player() {
        RunningState.getGameState().setPlayerList(mockPlayerList);
        return mockPlayerList.getActivePlayer();
    }
}
