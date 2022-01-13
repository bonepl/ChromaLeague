package net.booone.chromaleague.rest.eventdata;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {
    @Test
    void testArtificialEventRead() {
        //given
        final String json = readJsonResource("fullevent.json");

        //when
        final Event baseEvent = JsonIterator.deserialize(json, Event.class);

        //then
        assertEquals(7, baseEvent.EventID());
        assertEquals("DragonKill", baseEvent.EventName());
        assertEquals(933.6826782226563, baseEvent.EventTime());
        assertEquals("Fire", baseEvent.DragonType());
        assertEquals("BooonE", baseEvent.KillerName());
        assertEquals("Noob", baseEvent.VictimName());
        assertEquals("Win", baseEvent.Result());
        assertTrue(baseEvent.Assisters().containsAll(List.of("Teammate 1", "Teammate 2")));
    }

    private static String readJsonResource(String resourceName) {
        try {
            return Files.readString(
                    new File(Objects.requireNonNull(LeagueHttpClientMock.class.getClassLoader()
                            .getResource("json/" + resourceName)).toURI()).toPath());
        } catch (IOException | URISyntaxException e) {
            throw new AssertionError(e);
        }
    }
}