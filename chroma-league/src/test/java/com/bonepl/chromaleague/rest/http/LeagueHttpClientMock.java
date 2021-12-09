package com.bonepl.chromaleague.rest.http;

import com.bonepl.chromaleague.rest.http.client.BlockingLeagueHttpClient;
import com.bonepl.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import com.bonepl.chromaleague.tasks.FetchGameStatsTask;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.tasks.FetchPlayerListTask;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public final class LeagueHttpClientMock {
    private final BlockingLeagueHttpClient blockingLeagueHttpClientMock = mock(BlockingLeagueHttpClient.class);
    private final NonBlockingLeagueHttpClient nonBlockingLeagueHttpClientMock = mock(NonBlockingLeagueHttpClient.class);
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
        mockReturnedResponseWithResource(FetchGameStatsTask.URL, jsonResourcePath);
    }

    private final DecimalFormat gameTimeFormat =
            new DecimalFormat("0.0###############", DecimalFormatSymbols.getInstance(Locale.US));

    public void mockReturnedResponseWithResource(String uri, String jsonResourcePath) {
        try {
            mockReturnedResponse(uri, Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(LeagueHttpClientMock.class.getClassLoader()
                            .getResource(jsonResourcePath)).toURI())));
        } catch (IOException | URISyntaxException e) {
            fail(e);
        }
    }

    private void mockReturnedResponse(String uri, byte... toReturn) {
        try {
            final CloseableHttpResponse mockedResponse = mock(CloseableHttpResponse.class);
            when(mockedResponse.getEntity()).thenReturn(new ByteArrayEntity(toReturn));
            doReturn(mockedResponse).when(mock).execute(argThat(uriRequest -> uriRequest.getURI().toString().equals(uri)));
            doReturn(mock).when(blockingLeagueHttpClientMock).getHttpClient();
            doReturn(mock).when(nonBlockingLeagueHttpClientMock).getHttpClient();
            LeagueHttpClients.setBlockingLeagueHttpClient(blockingLeagueHttpClientMock);
            LeagueHttpClients.setNonBlockingLeagueHttpClient(nonBlockingLeagueHttpClientMock);
        } catch (IOException e) {
            fail(e);
        }
    }
}
