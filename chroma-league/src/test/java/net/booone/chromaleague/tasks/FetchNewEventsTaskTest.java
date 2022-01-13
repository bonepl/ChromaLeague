package net.booone.chromaleague.tasks;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static net.booone.chromaleague.rest.http.handlers.EventsResponseHandlerTest.verifyStandardEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FetchNewEventsTaskTest {
    private LeagueHttpClientMock leagueHttpClientMock;

    @BeforeEach
    void setUp() {
        final GameStateMocks gameStateMocks = new GameStateMocks();
        gameStateMocks.activePlayer();
        gameStateMocks.playerList();
        when(gameStateMocks.activePlayer().level()).thenReturn(6);
        leagueHttpClientMock = new LeagueHttpClientMock();
        when(gameStateMocks.gameStats().gameTime()).thenReturn(1400.0);
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testEventParsing() {
        //given
        leagueHttpClientMock.mockEventsResponse("json/standardevent.json");

        //when
        new FetchNewEventsTask().run();

        //then
        verifyStandardEvent(RunningState.getGameState().getEventData().getProcessedEvents());
    }

    @Test
        //TODO REMOVE?
    void testEventParsingAfterReconnect() {
        //given
        leagueHttpClientMock.mockEventsResponse("json/eventdata.json");

        //when
        new FetchNewEventsTask().run();
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(30);
        events.addAll(RunningState.getGameState().getEventData().getProcessedEvents());
        assertEquals(27, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.EventID());
        assertEquals("GameStart", event.EventName());
        assertEquals(0.0563616007566452, event.EventTime());
    }

    @Test
        //TODO REMOVE?
    void testFirstEventParsing() {
        //given
        leagueHttpClientMock.mockEventsResponse("json/gamestartevent.json");

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(1);
        events.addAll(RunningState.getGameState().getEventData().getProcessedEvents());
        assertEquals(1, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.EventID());
        assertEquals("GameStart", event.EventName());
        assertEquals(0.0563616007566452, event.EventTime());
    }
}