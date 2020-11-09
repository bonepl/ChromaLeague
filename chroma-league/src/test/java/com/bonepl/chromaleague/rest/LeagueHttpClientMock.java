package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import com.bonepl.chromaleague.tasks.FetchGameStats;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.tasks.FetchPlayerListTask;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class LeagueHttpClientMock {
    private final CloseableHttpClient mock = mock(CloseableHttpClient.class);

    public void mockActivePlayerResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchActivePlayerTask.URL, jsonResourcePath);
    }

    public void mockPlayerListResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchPlayerListTask.URL, jsonResourcePath);
    }

    public void mockEventsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchNewEventsTask.URL, jsonResourcePath);
    }

    public void mockGameStatsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchGameStats.URL, jsonResourcePath);
    }

    public void mockReturnedResponseWithResource(String uri, String jsonResourcePath) {
        try {
            mockReturnedResponse(uri, Files.readAllBytes(
                    Paths.get(LeagueHttpClientMock.class.getClassLoader()
                            .getResource(jsonResourcePath).toURI())));
        } catch (IOException | URISyntaxException e) {
            fail(e);
        }
    }

    private void mockReturnedResponse(String uri, byte... toReturn) {
        try {
            final CloseableHttpResponse mockedResponse = mock(CloseableHttpResponse.class);
            when(mockedResponse.getEntity()).thenReturn(new ByteArrayEntity(toReturn));
            when(mock.execute(argThat(uriRequest -> {
                return uriRequest.getURI().toString().equals(uri);
            }))).thenReturn(mockedResponse);
            LeagueHttpClient.setLeagueHttpClient(mock);
        } catch (IOException e) {
            fail(e);
        }
    }
}
