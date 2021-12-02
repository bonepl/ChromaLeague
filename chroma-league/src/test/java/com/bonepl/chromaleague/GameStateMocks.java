package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class GameStateMocks {
    public static final String PLAYER_NAME = "BooonE";

    private final ActivePlayer mockActivePlayer;
    private final PlayerList mockPlayerList;
    private final GameStats mockGameStats;

    public GameStateMocks() {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
        mockActivePlayer = prepareActivePlayerMock();
        mockPlayerList = preparePlayerListMock();
        mockGameStats = mock(GameStats.class);
        RunningState.getGameState().setPlayerName(PLAYER_NAME);
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
        when(activePlayer.championStats()).thenReturn(championStats);
        return activePlayer;
    }

    public ChampionStats championStats() {
        RunningState.getGameState().setActivePlayer(mockActivePlayer);
        return mockActivePlayer.championStats();
    }

    public ActivePlayer activePlayer() {
        RunningState.getGameState().setActivePlayer(mockActivePlayer);
        return mockActivePlayer;
    }

    public PlayerList playerList() {
        RunningState.getGameState().setPlayerList(mockPlayerList);
        return mockPlayerList;
    }

    public GameStats gameStats() {
        RunningState.getGameState().setGameStats(mockGameStats);
        return mockGameStats;
    }

    public Player player() {
        RunningState.getGameState().setPlayerList(mockPlayerList);
        return mockPlayerList.getActivePlayer();
    }

    public void mockTestPlayerList() {
        when(playerList().isAlly(eq(PLAYER_NAME))).thenReturn(true);
        when(playerList().isAlly(eq("Ally"))).thenReturn(true);
        when(playerList().isAlly(eq("Enemy"))).thenReturn(false);
    }
}
