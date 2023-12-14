package net.booone.chromaleague.rest.http.client;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryOnContentTooSmallInterceptorTest {
    @Test
    void shouldThrowIOExceptionToForceRetry() throws IOException {
        //given
        Executable executable;
        try (BasicClassicHttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\"\"")) {
            //when
            executable = () -> new RetryOnContentTooSmallInterceptor().process(testResponse, testResponse.getEntity(), null);
        }

        //then
        assertThrows(IOException.class, executable);
    }

    @Test
    void shouldNotThrowIOExceptionIfContentLargeEnough() throws IOException {
        //given
        Executable executable;
        try (BasicClassicHttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\"B\"")) {
            //when
            executable = () -> new RetryOnContentTooSmallInterceptor().process(testResponse, testResponse.getEntity(), null);
        }

        //then
        assertDoesNotThrow(executable);
    }
}