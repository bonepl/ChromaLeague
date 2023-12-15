package net.booone.chromaleague.rest.http.handlers;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static net.booone.chromaleague.rest.http.LeagueHttpClientMock.createTestResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpOkBytesHandlerTest {
    private final String testString = "test";

    @Test
    void shouldAcceptOkStatus() throws IOException {
        //given
        ClassicHttpResponse testResponse = createTestResponse(HttpStatus.SC_OK, testString);

        //when
        Optional<byte[]> bytes = new HttpOkBytesHandler().fetchBytesResponse(testResponse);

        //then
        assertTrue(bytes.isPresent());
        assertEquals(testString, new String(bytes.get()));
    }

    @Test
    void shouldReturnEmptyOptionalOnOtherStatus() throws IOException {
        //given
        ClassicHttpResponse testResponse = createTestResponse(HttpStatus.SC_BAD_GATEWAY, testString);

        //when
        Optional<byte[]> bytes = new HttpOkBytesHandler().fetchBytesResponse(testResponse);

        //then
        assertTrue(bytes.isEmpty());
    }
}