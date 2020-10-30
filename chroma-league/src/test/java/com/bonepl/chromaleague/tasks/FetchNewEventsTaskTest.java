package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FetchNewEventsTaskTest {

    @BeforeEach
    void setUp() {
        RunningState.setRunningGame(true);
        GameStateMocks.setActivePlayerName("BooonE");
        GameStateMocks.makePlayerListAvailable();
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testEventParsing() {
        //given
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/standardevent.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(3);
        events.addAll(RunningState.getGameState().getEventData().getProcessedEvents());
        assertEquals(3, events.size());
        final Event event = events.get(2);
        assertEquals(2, event.getEventID());
        assertEquals("ChampionKill", event.getEventName());
        assertEquals(7.1595916748047, event.getEventTime());
    }

    @Test
    void testEventParsingAfterReconnect() {
        //given
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/eventdata.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(30);
        events.addAll(RunningState.getGameState().getEventData().getProcessedEvents());
        assertEquals(27, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testFirstEventParsing() {
        //given
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/gamestartevent.json");

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(1);
        events.addAll(RunningState.getGameState().getEventData().getProcessedEvents());
        assertEquals(1, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }
}