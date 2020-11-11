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
import static org.mockito.Mockito.when;

class EventDataProcessorBaronBuffTest {
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
    void testBaronBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(250);
        when(gameStateMocks.player().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertSecondsEqualsWithMargin(GameStateHelper.BARON_TIME,
                50 + SECONDS.between(LocalTime.now(), eventData.getBaronBuffEnd()));
        assertTrue(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveCauseExpired() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(400);
        when(gameStateMocks.player().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getBaronBuffEnd());
        assertFalse(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveIfPlayerDead() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffActive.json");
        leagueHttpClientMock.mockGameStatsGameTime(250);
        when(gameStateMocks.player().isDead()).thenReturn(true);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getBaronBuffEnd());
        assertFalse(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveIfPlayerWasDead() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffInactivePlayerWasDead.json");
        leagueHttpClientMock.mockGameStatsGameTime(300);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().getLevel()).thenReturn(6);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getBaronBuffEnd());
        assertFalse(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveIfPlayerDied() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffInactivePlayerDied.json");
        leagueHttpClientMock.mockGameStatsGameTime(300);
        when(gameStateMocks.player().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().getLevel()).thenReturn(6);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getBaronBuffEnd());
        assertFalse(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveCauseEnemyKilled() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/enemyBaronBuff.json");
        leagueHttpClientMock.mockGameStatsGameTime(250);
        when(gameStateMocks.player().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        assertNull(eventData.getBaronBuffEnd());
        assertFalse(GameStateHelper.hasBaronBuff());
    }

    private static void assertSecondsEqualsWithMargin(long expectedSeconds, long actualSeconds) {
        assertTrue(actualSeconds >= expectedSeconds - 2);
        assertTrue(actualSeconds <= expectedSeconds + 2);
    }
}