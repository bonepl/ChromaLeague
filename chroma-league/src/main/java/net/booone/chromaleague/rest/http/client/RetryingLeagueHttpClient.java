package net.booone.chromaleague.rest.http.client;

import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.util.TimeValue;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RetryingLeagueHttpClient extends AbstractLeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(RetryingLeagueHttpClient.class.getName());

    public <T> T getResponse(final String url, HttpClientResponseHandler<T> responseHandler) {
        try {
            return super.execute(url, responseHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "This shouldn't happen in retrying league http client", e);
            throw new IllegalStateException(e);
        }
    }

    public CloseableHttpClient createLeagueHttpClient() {
        return commonHttpClientBuilder()
                .addResponseInterceptorFirst(new RetryOnNon200StatusInterceptor())
                .addResponseInterceptorLast(new RetryOnContentTooSmallInterceptor())
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy(10, TimeValue.ofSeconds(1)))
                .build();
    }
}
