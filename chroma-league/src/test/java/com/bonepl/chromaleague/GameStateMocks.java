package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class GameStateMocks {
    private final ActivePlayer mockActivePlayer;
    private final PlayerList mockPlayerList;

    public GameStateMocks() {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
        mockActivePlayer = prepareActivePlayerMock();
        mockPlayerList = preparePlayerListMock();
    }

    public GameStateMocks(String playerName) {
        this();
        when(mockPlayerList.getActivePlayer().getSummonerName()).thenReturn(playerName);
    }

    private PlayerList preparePlayerListMock() {
        PlayerList playerList = mock(PlayerList.class);
        Player activePlayer = mock(Player.class);
        when(playerList.getActivePlayer()).thenReturn(activePlayer);
        return playerList;
    }

    private ActivePlayer prepareActivePlayerMock() {
        ChampionStats championStats = mock(ChampionStats.class);
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getChampionStats()).thenReturn(championStats);
        return activePlayer;
    }

    public static void mockPlayerNameAndIsAllyResponse(String playerName, boolean isAlly) {
        RunningState.setRunningGame(true);
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getSummonerName()).thenReturn(playerName);
        RunningState.getGameState().setActivePlayer(activePlayer);
        final PlayerList mock = mock(PlayerList.class);
        when(mock.isAlly(any())).thenReturn(isAlly);
        RunningState.getGameState().setPlayerList(mock);
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
}
