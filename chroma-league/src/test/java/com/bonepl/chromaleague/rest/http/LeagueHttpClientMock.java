package com.bonepl.chromaleague.rest.http;

import com.bonepl.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import com.bonepl.chromaleague.rest.http.client.RetryingLeagueHttpClient;
import com.bonepl.chromaleague.rest.http.handlers.ActivePlayerResponseHandler;
import com.bonepl.chromaleague.rest.http.handlers.EventsResponseHandler;
import com.bonepl.chromaleague.rest.http.handlers.GameStatsResponseHandler;
import com.bonepl.chromaleague.rest.http.handlers.PlayerListResponseHandler;
import com.bonepl.chromaleague.tasks.FetchActivePlayerTask;
import com.bonepl.chromaleague.tasks.FetchGameStatsTask;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import com.bonepl.chromaleague.tasks.FetchPlayerListTask;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public final class LeagueHttpClientMock {
    private final RetryingLeagueHttpClient retryingLeagueHttpClientMock = spy(new RetryingLeagueHttpClient());
    private final NonBlockingLeagueHttpClient nonBlockingLeagueHttpClientMock = spy(new NonBlockingLeagueHttpClient());
    private final CloseableHttpClient mock = mock(CloseableHttpClient.class);

    public void mockActivePlayerResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchActivePlayerTask.URL, jsonResourcePath, new ActivePlayerResponseHandler());
    }

    public void mockPlayerListResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchPlayerListTask.URL, jsonResourcePath, new PlayerListResponseHandler());
    }

    public void mockEventsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchNewEventsTask.URL, jsonResourcePath, new EventsResponseHandler());
    }

    public void mockGameStatsResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchGameStatsTask.URL, jsonResourcePath, new GameStatsResponseHandler());
    }

    public void mockReturnedResponseWithResource(String uri, String jsonResourcePath, ResponseHandler responseHandler) {
        try {
            BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, ""));
            basicHttpResponse.setEntity(new ByteArrayEntity(Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(LeagueHttpClientMock.class.getClassLoader()
                            .getResource(jsonResourcePath)).toURI()))));
            mockReturnedResponse(uri, responseHandler.handleResponse(basicHttpResponse));
        } catch (IOException | URISyntaxException e) {
            fail(e);
        }
    }

    private void mockReturnedResponse(String uri, Object returnedObject) {
        try {
            doReturn(returnedObject).when(mock).execute(argThat(uriRequest -> uriRequest.getURI().toString().equals(uri)), any(ResponseHandler.class));
            doReturn(mock).when(retryingLeagueHttpClientMock).getHttpClient();
            doReturn(mock).when(nonBlockingLeagueHttpClientMock).getHttpClient();
            LeagueHttpClients.setRetryingLeagueHttpClient(retryingLeagueHttpClientMock);
            LeagueHttpClients.setNonBlockingLeagueHttpClient(nonBlockingLeagueHttpClientMock);
        } catch (IOException e) {
            fail(e);
        }
    }
}
