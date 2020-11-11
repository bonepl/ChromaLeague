package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    }

    @Test
    void testElderBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(400);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertSecondsEqualsWithMargin(GameStateHelper.FIRST_ELDER_TIME,
                50 + SECONDS.between(LocalTime.now(), eventData.getElderBuffEnd()));
        assertEquals(1, eventData.getTotalEldersKilled());
        assertTrue(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testSecondElderBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/secondElderBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(750);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertSecondsEqualsWithMargin(GameStateHelper.NEXT_ELDER_TIME,
                50 + SECONDS.between(LocalTime.now(), eventData.getElderBuffEnd()));
        assertEquals(2, eventData.getTotalEldersKilled());
        assertTrue(GameStateHelper.hasElderBuff());
        assertEquals(4, eventData.getKilledDragons().size());
    }

    @Test
    void testElderBuffInactiveCauseExpired() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(600);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

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
    void testElderBuffInactiveIfPlayerDead() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/elderBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(400);
        when(gameStateMocks.player().isDead()).thenReturn(true);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

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
        leagueHttpClientMock.mockGameStatsGameTime(400);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

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
        leagueHttpClientMock.mockGameStatsGameTime(450);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);

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
        leagueHttpClientMock.mockGameStatsGameTime(400);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);

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