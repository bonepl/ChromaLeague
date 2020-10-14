package com.bonepl.chromaleague.razer.league.json.eventdata;

import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.eventdata.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventDataThreadTest {

    private EventDataThread eventDataThread;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/eventdata.json").toURI()).toPath());
        LeagueHttpClient mockedLeagueHttpClient = mock(LeagueHttpClient.class);
        when(mockedLeagueHttpClient.fetchData(any())).thenReturn(testJson);

        eventDataThread = new EventDataThread(mockedLeagueHttpClient);
    }

    @Test
    void testEventParsing() {
        //when
        final List<Event> events = eventDataThread.fetchData();

        //then
        assertEquals(27, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testEventStackingProtection() {
        //when
        final List<Event> events = eventDataThread.fetchData();
        final List<Event> unprocessedEvents = eventDataThread.collectUnprocessedEvents(events);

        //then
        assertTrue(unprocessedEvents.isEmpty());
    }
}