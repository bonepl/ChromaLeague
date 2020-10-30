package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class GameStateMocks {
    private GameStateMocks() {
    }

    public static void setActivePlayerName(String name) {
        ActivePlayer mockedActivePlayer = mock(ActivePlayer.class);
        when(mockedActivePlayer.getSummonerName()).thenReturn(name);
        RunningState.getGameState().setActivePlayer(mockedActivePlayer);
    }

    public static void makePlayerListAvailable() {
        PlayerList playerList = mock(PlayerList.class);
        RunningState.getGameState().setPlayerList(playerList);
    }

    public static void mockActivePlayerGold(double gold) {
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        when(apMock.getCurrentGold()).thenReturn(gold);
        RunningState.getGameState().setActivePlayer(apMock);
    }

    public static void clearActivePlayer() {
        RunningState.getGameState().setActivePlayer(null);
    }

    public static void clearPlayerList() {
        RunningState.getGameState().setPlayerList(null);
    }

    public static void mockActivePlayerHealth(double currentHealth, double maxHealth) {
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        when(championStatsMock.getCurrentHealth()).thenReturn(currentHealth);
        when(championStatsMock.getMaxHealth()).thenReturn(maxHealth);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        RunningState.getGameState().setActivePlayer(apMock);
    }

    public static ChampionStats getMockedChampionStats() {
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        RunningState.getGameState().setActivePlayer(apMock);
        return championStatsMock;
    }
}
