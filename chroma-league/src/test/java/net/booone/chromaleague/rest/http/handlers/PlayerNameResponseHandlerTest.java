package net.booone.chromaleague.rest.http.handlers;

import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.booone.chromaleague.rest.http.LeagueHttpClientMock.createTestResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerNameResponseHandlerTest {

    @Test
    void testPlayerNameCleanup() throws IOException, HttpException {
        //given
        final String testPlayerName = "\"BąnE \" \"";

        //when
        String actualTestPlayerName = new PlayerNameResponseHandler()
                .handleResponse(createTestResponse(HttpStatus.SC_OK, testPlayerName));

        //then
        assertEquals("BąnE \" ", actualTestPlayerName);
    }

    @Test
    void testEmptyResponse() {
        //given
        final String testPlayerName = "player";

        //then
        assertThrows(IllegalStateException.class, () -> new PlayerNameResponseHandler()
                .handleResponse(createTestResponse(HttpStatus.SC_BAD_REQUEST, testPlayerName)));
    }
}