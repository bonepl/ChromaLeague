package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EventDataProcessorKillsAssistsTest {
    private LeagueHttpClientMock leagueHttpClientMock;
    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        leagueHttpClientMock = new LeagueHttpClientMock();
        gameStateMocks = new GameStateMocks();
        gameStateMocks.activePlayer();
        gameStateMocks.mockTestPlayerList();
    }

    @Test
    void testKillsAndAssists() {
        leagueHttpClientMock.mockEventsResponse("json/scenarios/killsAssists.json");
        leagueHttpClientMock.mockGameStatsGameTime(320);
        when(gameStateMocks.activePlayer().level()).thenReturn(5);

        new FetchNewEventsTask().run();

        final EventData eventData = RunningState.getGameState().getEventData();
        assertEquals(2, eventData.getActivePlayerAssistSpree());
        assertEquals(1, eventData.getActivePlayerKillingSpree());
    }

    @Test
    void testEnemyKillsAndAssists() {
        leagueHttpClientMock.mockEventsResponse("json/scenarios/enemyKillsAssists.json");
        leagueHttpClientMock.mockGameStatsGameTime(320);
        when(gameStateMocks.activePlayer().level()).thenReturn(5);

        new FetchNewEventsTask().run();

        final EventData eventData = RunningState.getGameState().getEventData();
        assertEquals(0, eventData.getActivePlayerAssistSpree());
        assertEquals(0, eventData.getActivePlayerKillingSpree());
    }
}
