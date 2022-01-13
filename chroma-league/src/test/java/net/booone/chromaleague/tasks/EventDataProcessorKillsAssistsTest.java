package net.booone.chromaleague.tasks;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.EventData;
import net.booone.chromaleague.state.RunningState;
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
        when(gameStateMocks.gameStats().gameTime()).thenReturn(320.0);
        when(gameStateMocks.activePlayer().level()).thenReturn(5);

        new FetchNewEventsTask().run();

        final EventData eventData = RunningState.getGameState().getEventData();
        assertEquals(2, eventData.getActivePlayerAssistSpree());
        assertEquals(1, eventData.getActivePlayerKillingSpree());
    }

    @Test
    void testEnemyKillsAndAssists() {
        leagueHttpClientMock.mockEventsResponse("json/scenarios/enemyKillsAssists.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(320.0);
        when(gameStateMocks.activePlayer().level()).thenReturn(5);

        new FetchNewEventsTask().run();

        final EventData eventData = RunningState.getGameState().getEventData();
        assertEquals(0, eventData.getActivePlayerAssistSpree());
        assertEquals(0, eventData.getActivePlayerKillingSpree());
    }
}
