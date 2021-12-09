package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BlockingLeagueHttpClient extends AbstractLeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(BlockingLeagueHttpClient.class.getName());
    private final CloseableHttpClient httpClient = createBlockingLeagueHttpClient();

    public <T> T getBlockingResponse(final String url, ResponseHandler<T> responseHandler) {
        try {
            return httpClient.execute(jsonHttpGet(url), responseHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while fetching HTTP response - this shouldn't happen in BlockingLeagueHttpClient");
            throw new IllegalStateException(e);
        }
    }

    private CloseableHttpClient createBlockingLeagueHttpClient() {
        return commonHttpClientBuilder()
                .addInterceptorLast(new BlockingResponseInterceptor())
                .build();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
