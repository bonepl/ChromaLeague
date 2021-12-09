package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NonBlockingLeagueHttpClient extends AbstractLeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(NonBlockingLeagueHttpClient.class.getName());
    private final CloseableHttpClient httpClient = createNonBlockingLeagueHttpClient();

    public <T> Optional<T> getNonBlockingResponse(final String url, ResponseHandler<Optional<T>> responseHandler) {
        try {
            return httpClient.execute(jsonHttpGet(url), responseHandler);
        } catch (IOException e) {
            //IOException will be thrown when endpoints are down and threads are not shutdown yet
            LOGGER.log(Level.FINER, e, () -> "Error while fetching HTTP response - probably endpoint is down");
        }
        return Optional.empty();
    }

    private CloseableHttpClient createNonBlockingLeagueHttpClient() {
        return commonHttpClientBuilder()
                .disableAutomaticRetries()
                .build();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
