package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.eventdata.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FetchNewEventsTaskTest {

    @BeforeEach
    void setUp() {
        GameStateMocks.setActivePlayerName("BooonE");
        GameStateMocks.makePlayerListAvailable();

        EventAnimationProcessorTask.clearUnprocessedEvents();
        EventDataProcessorTask.clearUnprocessedEvents();
    }

    @AfterEach
    void tearDown() {
        GameStateMocks.clearActivePlayer();
        GameStateMocks.clearPlayerList();
        EventAnimationProcessorTask.clearUnprocessedEvents();
        EventDataProcessorTask.clearUnprocessedEvents();
    }

    @Test
    void testEventParsing() {
        //given
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/eventdata.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(30);
        events.addAll(EventDataProcessorTask.getUnprocessedEvents());
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
        events.addAll(EventDataProcessorTask.getUnprocessedEvents());
        assertEquals(1, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testEventsSkipAfterReconnect() {
        //given
        LeagueHttpClientMock.mockReturnedResponseWithResource("json/eventdata.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        assertTrue(EventAnimationProcessorTask.getUnprocessedEvents().isEmpty());
    }
}