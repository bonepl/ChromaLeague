package net.booone.chromaleague.tasks;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.EventData;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventDataProcessorElderBuffTest {
    private LeagueHttpClientMock leagueHttpClientMock;
    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        leagueHttpClientMock = new LeagueHttpClientMock();
        gameStateMocks = new GameStateMocks();
        gameStateMocks.activePlayer();
        gameStateMocks.playerList();
        gameStateMocks.mockTestPlayerList();
    }

    @Test
    void testElderBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffActive.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(400.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        double gameTime = RunningState.getGameState().getGameStats().gameTime();
        assertSecondsEqualsWithMargin(GameStateHelper.FIRST_ELDER_TIME,
                (long) (50 + eventData.getElderBuffEnd() - gameTime));
        assertEquals(1, eventData.getTotalEldersKilled());
        assertTrue(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testSecondElderBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/secondElderBuffActive.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(750.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        double gameTime = RunningState.getGameState().getGameStats().gameTime();
        assertSecondsEqualsWithMargin(GameStateHelper.NEXT_ELDER_TIME,
                (long) (50 + eventData.getElderBuffEnd() - gameTime));
        assertEquals(2, eventData.getTotalEldersKilled());
        assertTrue(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testElderBuffInactiveCauseExpired() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffActive.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(600.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getElderBuffEnd());
        assertEquals(1, eventData.getTotalEldersKilled());
        assertFalse(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testElderBuffInactiveIfPlayerWasDead() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffInactivePlayerWasDead.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(400.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().level()).thenReturn(6);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getElderBuffEnd());
        assertEquals(1, eventData.getTotalEldersKilled());
        assertFalse(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testElderBuffInactiveIfPlayerDied() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffInactivePlayerDied.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(450.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().level()).thenReturn(6);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getElderBuffEnd());
        assertEquals(1, eventData.getTotalEldersKilled());
        assertFalse(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testElderBuffInactiveCauseEnemyKilled() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/enemyElderBuff.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(400.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getElderBuffEnd());
        assertEquals(1, eventData.getTotalEldersKilled());
        assertFalse(GameStateHelper.hasElderBuff());
        assertEquals(0, eventData.getKilledDragons().size());
    }

    private static void assertSecondsEqualsWithMargin(long expectedSeconds, long actualSeconds) {
        assertTrue(actualSeconds >= expectedSeconds - 2);
        assertTrue(actualSeconds <= expectedSeconds + 2);
    }
}