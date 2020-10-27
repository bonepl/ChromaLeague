package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.GameState;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class GameStateMocks {
    private GameStateMocks() {
    }

    public static void setActivePlayerName(String name) {
        ActivePlayer mockedActivePlayer = mock(ActivePlayer.class);
        when(mockedActivePlayer.getSummonerName()).thenReturn(name);
        GameState.setActivePlayer(mockedActivePlayer);
    }

    public static void makePlayerListAvailable() {
        PlayerList playerList = mock(PlayerList.class);
        GameState.setPlayerList(playerList);
    }

    public static void mockActivePlayerGold(double gold) {
        final ChampionStats championStatsMock = mock(ChampionStats.class);
        final ActivePlayer apMock = mock(ActivePlayer.class);
        when(apMock.getChampionStats()).thenReturn(championStatsMock);
        when(apMock.getCurrentGold()).thenReturn(gold);
        GameState.setActivePlayer(apMock);
    }

    public static void clearActivePlayer() {
        GameState.setActivePlayer(null);
    }

    public static void clearPlayerList() {
        GameState.setPlayerList(null);
    }

}
