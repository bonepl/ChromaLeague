package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.EventData;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventDataProcessorBaronBuffTest {
    private LeagueHttpClientMock leagueHttpClientMock;
    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        leagueHttpClientMock = new LeagueHttpClientMock();
        gameStateMocks = new GameStateMocks();
        gameStateMocks.playerList();
        gameStateMocks.activePlayer();
        gameStateMocks.mockTestPlayerList();
    }

    @Test
    void testBaronBuffActive() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffActive.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(250.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

        // when
        new FetchNewEventsTask().run();

        // then
        final EventData eventData = RunningState.getGameState().getEventData();
        double gameTime = RunningState.getGameState().getGameStats().gameTime();
        assertSecondsEqualsWithMargin(GameStateHelper.BARON_TIME,
                (long) (50 + eventData.getBaronBuffEnd() - gameTime));
        assertTrue(GameStateHelper.hasBaronBuff());
    }

    @Test
    void testBaronBuffInactiveCauseExpired() {
        // given
        leagueHttpClientMock.mockEventsResponse("json/scenarios/baronBuffActive.json");
        when(gameStateMocks.gameStats().gameTime()).thenReturn(400.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

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
        when(gameStateMocks.gameStats().gameTime()).thenReturn(300.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().level()).thenReturn(6);

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
        when(gameStateMocks.gameStats().gameTime()).thenReturn(300.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);
        when(gameStateMocks.activePlayer().level()).thenReturn(6);

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
        when(gameStateMocks.gameStats().gameTime()).thenReturn(250.0);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);

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