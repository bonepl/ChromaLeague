package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import com.bonepl.chromaleague.tasks.FetchGameStats;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.tasks.FetchPlayerList;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public final class LeagueHttpClientMock {
    private final CloseableHttpClient mock = mock(CloseableHttpClient.class);

    public void mockActivePlayerResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchActivePlayerTask.URL, jsonResourcePath);
    }

    public void mockPlayerListResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchPlayerList.URL, jsonResourcePath);
    }

    public void mockEventsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchNewEventsTask.URL, jsonResourcePath);
    }

    public void mockGameStatsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchGameStats.URL, jsonResourcePath);
    }

    private final DecimalFormat gameTimeFormat =
            new DecimalFormat("0.0###############", DecimalFormatSymbols.getInstance(Locale.US));

    public void mockGameStatsGameTime(double gameTime) {
        mockReturnedResponse(FetchGameStats.URL, String.format("{\"gameMode\": \"CLASSIC\", \"gameTime\": %s}", gameTimeFormat.format(gameTime))
                .getBytes(StandardCharsets.UTF_8));
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
            doReturn(mockedResponse).when(mock).execute(argThat(uriRequest -> uriRequest.getURI().toString().equals(uri)));
            LeagueHttpClient.setLeagueHttpClient(mock);
        } catch (IOException e) {
            fail(e);
        }
    }
}
