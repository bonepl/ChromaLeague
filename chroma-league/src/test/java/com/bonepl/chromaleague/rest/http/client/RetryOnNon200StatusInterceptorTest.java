package com.bonepl.chromaleague.rest.http.client;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryOnNon200StatusInterceptorTest {
    @Test
    void shouldThrowIOExceptionToForceRetry() {
        //given
        HttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, "test");
        //when
        Executable executable = () -> new RetryOnNon200StatusInterceptor().process(testResponse, null);
        //then
        assertThrows(IOException.class, executable);
    }

    @Test
    void shouldNotThrowIOExceptionIfStatus200() {
        //given
        HttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\test");
        //when
        Executable executable = () -> new RetryOnNon200StatusInterceptor().process(testResponse, null);
        //then
        assertDoesNotThrow(executable);
    }
}