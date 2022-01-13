package net.booone.chromaleague.rest.http;

import net.booone.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import net.booone.chromaleague.rest.http.client.RetryingLeagueHttpClient;
import net.booone.chromaleague.rest.http.handlers.*;
import net.booone.chromaleague.tasks.*;
import org.apache.http.HttpResponse;
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

    public void mockPlayerNameResponse(String jsonResourcePath) {
        mockReturnedResponseWithResource(FetchPlayerNameTask.URL, jsonResourcePath, new PlayerNameResponseHandler());
    }

    public void mockReturnedResponseWithResource(String uri, String jsonResourcePath, ResponseHandler responseHandler) {
        try {
            mockReturnedResponse(uri, responseHandler.handleResponse(createTestResponseFromJSON(jsonResourcePath)));
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

    public static HttpResponse createTestResponse(int statusCode, String testString) {
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, ""));
        basicHttpResponse.setEntity(new ByteArrayEntity(testString.getBytes()));
        return basicHttpResponse;
    }

    public static HttpResponse createTestResponseFromJSON(String jsonResourcePath) throws IOException, URISyntaxException {
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, ""));
        basicHttpResponse.setEntity(new ByteArrayEntity(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(LeagueHttpClientMock.class.getClassLoader()
                        .getResource(jsonResourcePath)).toURI()))));
        return basicHttpResponse;
    }
}
