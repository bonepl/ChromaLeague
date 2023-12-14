package net.booone.chromaleague.rest.http.handlers;

import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsResponseHandlerTest {

    @Test
    void testEventParsing() throws IOException, URISyntaxException, HttpException {
        //given
        ClassicHttpResponse testResponseFromJSON = LeagueHttpClientMock.createTestResponseFromJSON("json/standardevent.json");

        //when
        Optional<List<Event>> events = new EventsResponseHandler().handleResponse(testResponseFromJSON);

        //then
        assertTrue(events.isPresent());
        verifyStandardEvent(events.get());
    }

    public static void verifyStandardEvent(List<Event> events) {
        assertEquals(3, events.size());

        final Event event0 = events.get(0);
        assertEquals(0, event0.EventID());
        assertEquals("GameStart", event0.EventName());
        assertEquals(0.0563616007566452, event0.EventTime());

        final Event event1 = events.get(1);
        assertEquals(1, event1.EventID());
        assertEquals("MinionsSpawning", event1.EventName());
        assertEquals(5.06690979003906, event1.EventTime());

        final Event event2 = events.get(2);
        assertEquals(2, event2.EventID());
        assertEquals("ChampionKill", event2.EventName());
        assertEquals(7.1595916748047, event2.EventTime());
    }
}