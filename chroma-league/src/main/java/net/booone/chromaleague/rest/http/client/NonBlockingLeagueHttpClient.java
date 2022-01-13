package net.booone.chromaleague.rest.http.client;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Optional;

public final class NonBlockingLeagueHttpClient extends AbstractLeagueHttpClient {

    public <T> Optional<T> getResponse(final String url, ResponseHandler<Optional<T>> responseHandler) {
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
