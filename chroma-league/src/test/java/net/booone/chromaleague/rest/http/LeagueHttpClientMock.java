package net.booone.chromaleague.rest.http;

import net.booone.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import net.booone.chromaleague.rest.http.client.RetryingLeagueHttpClient;
import net.booone.chromaleague.rest.http.handlers.*;
import net.booone.chromaleague.tasks.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;

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

    public void mockReturnedResponseWithResource(String uri, String jsonResourcePath, HttpClientResponseHandler<?> responseHandler) {
        try {
            mockReturnedResponse(uri, responseHandler.handleResponse(createTestResponseFromJSON(jsonResourcePath)));
        } catch (IOException | URISyntaxException | HttpException e) {
            fail(e);
        }
    }

    private void mockReturnedResponse(String uri, Object returnedObject) {
        try {
            doReturn(returnedObject).when(mock).execute(argThat(uriRequest -> {
                try {
                    return uriRequest.getUri().toString().equals(uri);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }), any(HttpClientResponseHandler.class));
            doReturn(mock).when(retryingLeagueHttpClientMock).getHttpClient();
            doReturn(mock).when(nonBlockingLeagueHttpClientMock).getHttpClient();
            LeagueHttpClients.setRetryingLeagueHttpClient(retryingLeagueHttpClientMock);
            LeagueHttpClients.setNonBlockingLeagueHttpClient(nonBlockingLeagueHttpClientMock);
        } catch (IOException e) {
            fail(e);
        }
    }

    public static BasicClassicHttpResponse createTestResponse(int statusCode, String testString) {
        BasicClassicHttpResponse basicHttpResponse = new BasicClassicHttpResponse(statusCode);
        basicHttpResponse.setEntity(new ByteArrayEntity(testString.getBytes(), ContentType.TEXT_PLAIN));
        return basicHttpResponse;
    }

    public static BasicClassicHttpResponse createTestResponseFromJSON(String jsonResourcePath) throws IOException, URISyntaxException {
        BasicClassicHttpResponse basicHttpResponse = new BasicClassicHttpResponse(HttpStatus.SC_OK);
        basicHttpResponse.setEntity(new ByteArrayEntity(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(LeagueHttpClientMock.class.getClassLoader()
                        .getResource(jsonResourcePath)).toURI())), ContentType.TEXT_PLAIN));
        return basicHttpResponse;
    }
}
