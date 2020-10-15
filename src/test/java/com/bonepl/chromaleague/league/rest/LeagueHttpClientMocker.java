package com.bonepl.chromaleague.league.rest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LeagueHttpClientMocker {
    private LeagueHttpClientMocker() {
    }

    public static void mockReturnedResponseWithResource(String jsonResourcePath) {
        try {
            mockReturnedResponse(Files.readString(
                    new File(Objects.requireNonNull(LeagueHttpClientMocker.class.getClassLoader()
                            .getResource(jsonResourcePath)).toURI()).toPath()));
        } catch (IOException | URISyntaxException e) {
            fail(e);
        }
    }

    public static void mockReturnedResponse(String toReturn) {
        try {
            final CloseableHttpResponse mockedResponse = mock(CloseableHttpResponse.class);
            when(mockedResponse.getEntity()).thenReturn(new StringEntity(toReturn));
            final CloseableHttpClient mock = mock(CloseableHttpClient.class);
            when(mock.execute(any())).thenReturn(mockedResponse);
            LeagueHttpClient.setLeagueHttpClient(mock);
        } catch (IOException e) {
            fail(e);
        }
    }
}
