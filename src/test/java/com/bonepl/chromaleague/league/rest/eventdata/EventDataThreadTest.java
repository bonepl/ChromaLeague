package com.bonepl.chromaleague.league.rest.eventdata;

import com.bonepl.chromaleague.league.rest.eventdata.model.Event;
import com.bonepl.chromaleague.league.rest.eventdata.model.Events;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDataThreadTest {

    @Test
    void testEventParsing() throws URISyntaxException, IOException {
        //given
        final String testJson = Files.readString(new File(this.getClass().getClassLoader()
                .getResource("json/eventdata.json").toURI()).toPath());

        //when
        final List<Event> events = JsonIterator.deserialize(testJson, Events.class).getEvents();

        //then
        assertEquals(27, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }
}