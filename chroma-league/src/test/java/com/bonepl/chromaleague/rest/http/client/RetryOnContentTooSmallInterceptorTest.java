package com.bonepl.chromaleague.rest.http.client;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetryOnContentTooSmallInterceptorTest {
    @Test
    void shouldThrowIOExceptionToForceRetry() {
        //given
        HttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\"\"");
        //when
        Executable executable = () -> new RetryOnContentTooSmallInterceptor().process(testResponse, null);
        //then
        assertThrows(IOException.class, executable);
    }

    @Test
    void shouldNotThrowIOExceptionIfContentLargeEnough() {
        //given
        HttpResponse testResponse = LeagueHttpClientMock.createTestResponse(HttpStatus.SC_OK, "\"B\"");
        //when
        Executable executable = () -> new RetryOnContentTooSmallInterceptor().process(testResponse, null);
        //then
        assertDoesNotThrow(executable);
    }
}