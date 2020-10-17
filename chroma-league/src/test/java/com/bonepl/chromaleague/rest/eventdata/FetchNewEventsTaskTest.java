package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.EventProcessor;
import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.rest.eventdata.model.Event;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FetchNewEventsTaskTest {

    @Test
    void testEventParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/eventdata.json");
        GameState.setActivePlayerName("BooonE");
        EventProcessor.setLastProcessedEventId(0);

        //when
        new FetchNewEventsTask().run();

        //then
        assertTrue(GameState.hasUnprocessedEvents());
        List<Event> events = new ArrayList<>(30);
        while (GameState.hasUnprocessedEvents()) {
            events.add(GameState.pollNextUnprocessedEvent());
        }
        assertEquals(26, events.size());
        final Event event = events.get(0);
        assertEquals(1, event.getEventID());
        assertEquals("MinionsSpawning", event.getEventName());
        assertEquals(65.06690979003906, event.getEventTime());
    }

    @Test
    void testFirstEventParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/gamestartevent.json");
        GameState.setActivePlayerName("BooonE");
        EventProcessor.setLastProcessedEventId(-1);

        //when
        new FetchNewEventsTask().run();

        //then
        assertTrue(GameState.hasUnprocessedEvents());
        List<Event> events = new ArrayList<>(1);
        while (GameState.hasUnprocessedEvents()) {
            events.add(GameState.pollNextUnprocessedEvent());
        }
        assertEquals(1, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testEventsSkipAfterReconnect() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/eventdata.json");
        GameState.setActivePlayerName("BooonE");
        EventProcessor.setLastProcessedEventId(-1);

        //when
        new FetchNewEventsTask().run();

        assertFalse(GameState.hasUnprocessedEvents());
    }
}