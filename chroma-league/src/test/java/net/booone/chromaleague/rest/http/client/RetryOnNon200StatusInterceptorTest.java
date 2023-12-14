package net.booone.chromaleague.rest.http.client;

import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryOnNon200StatusInterceptorTest {
    @Test
    void shouldThrowIOExceptionToForceRetry() {
        //given
        BasicClassicHttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, "test");
        //when
        Executable executable = () -> new RetryOnNon200StatusInterceptor().process(testResponse, testResponse.getEntity(), null);
        //then
        assertThrows(IOException.class, executable);
    }

    @Test
    void shouldNotThrowIOExceptionIfStatus200() {
        //given
        BasicClassicHttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\test");
        //when
        Executable executable = () -> new RetryOnNon200StatusInterceptor().process(testResponse, testResponse.getEntity(), null);
        //then
        assertDoesNotThrow(executable);
    }
}