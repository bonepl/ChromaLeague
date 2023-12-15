package net.booone.chromaleague.rest.http.client;


import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.util.Optional;

public final class NonBlockingLeagueHttpClient extends AbstractLeagueHttpClient {

    public <T> Optional<T> getResponse(final String url, HttpClientResponseHandler<Optional<T>> responseHandler) {
        try {
            return super.execute(url, responseHandler);
        } catch (IOException e) {
            //this http client can fail, but we allow it as it shouldn't block the application
        }
        return Optional.empty();
    }

    public CloseableHttpClient createLeagueHttpClient() {
        return commonHttpClientBuilder()
                .disableAutomaticRetries()
                .build();
    }
}
