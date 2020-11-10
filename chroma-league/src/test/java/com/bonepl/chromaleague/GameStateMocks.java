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
    private GameStateMocks() {
    }

    public static void clearActivePlayer() {
        RunningState.setRunningGame(true);
        RunningState.getGameState().setActivePlayer(null);
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

    public static ChampionStats mockChampionStats() {
        RunningState.setRunningGame(true);
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        RunningState.getGameState().setActivePlayer(apMock);
        return championStatsMock;
    }

    public static ActivePlayer mockActivePlayer(String playerName) {
        RunningState.setRunningGame(true);
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getSummonerName()).thenReturn(playerName);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        RunningState.getGameState().setActivePlayer(apMock);
        return apMock;
    }

    public static PlayerList mockPlayerList() {
        RunningState.setRunningGame(true);
        final PlayerList playerListMock = mock(PlayerList.class);
        Player activePlayer = mock(Player.class);
        when(playerListMock.getActivePlayer()).thenReturn(activePlayer);
        RunningState.getGameState().setPlayerList(playerListMock);
        return playerListMock;
    }
}
